/*
 * Created on 23-04-2024 21:02 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.availability.application.AvailabilityApplicationService;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.event.domain.EventPositionAssignment;
import com.capitolmanager.event.interfaces.EventForm;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.show.application.ShowEventDto;
import com.capitolmanager.show.application.ShowQueries;
import com.capitolmanager.utils.DateUtils;


@Service
public class EventApplicationService {

	protected static final List<String> WEEK_DAYS = Arrays.asList(
		"pon",
		"wt",
		"śr",
		"czw",
		"pt",
		"sob",
		"ndz"
	);

	private final ShowQueries showQueries;
	private final Repository<Event> eventRepository;
	private final EventQueries eventQueries;
	private final AvailabilityApplicationService availabilityApplicationService;
	private final EventGroupQueries eventGroupQueries;
	private final Repository<EventGroup> eventGroupRepository;
	private final Repository<EventPositionAssignment> eventPositionAssignmentRepository;


	@Autowired
	EventApplicationService(ShowQueries showQueries,
		Repository<Event> eventRepository,
		EventQueries eventQueries,
		AvailabilityApplicationService availabilityApplicationService,
		EventGroupQueries eventGroupQueries,
		Repository<EventGroup> eventGroupRepository,
		Repository<EventPositionAssignment> eventPositionAssignmentRepository) {

		Assert.notNull(showQueries, "showQueries must not be null");
		Assert.notNull(eventRepository, "eventRepository must not be null");
		Assert.notNull(eventQueries, "eventQueries must not be null");
		Assert.notNull(availabilityApplicationService, "availabilityApplicationService must not be null");
		Assert.notNull(eventGroupQueries, "eventGroupQueries must not be null");
		Assert.notNull(eventGroupRepository, "eventGroupRepository must not be null");
		Assert.notNull(eventPositionAssignmentRepository, "eventPositionAssignmentRepository must not be null");

		this.showQueries = showQueries;
		this.eventRepository = eventRepository;
		this.eventQueries = eventQueries;
		this.availabilityApplicationService = availabilityApplicationService;
		this.eventGroupQueries = eventGroupQueries;
		this.eventGroupRepository = eventGroupRepository;
		this.eventPositionAssignmentRepository = eventPositionAssignmentRepository;
	}

	public List<WeekDayWithEvents> getEventsByWeek(LocalDate weekStart, Long eventGroupId) {

		var events = new ArrayList<WeekDayWithEvents>();

		for (int i = 0; i < 7; i++) {

			LocalDate day = weekStart.plusDays(i);

			var weekDayWithEvents = new WeekDayWithEvents(WEEK_DAYS.get(i) + " " + DateUtils.formatLocalDateToDDMM(day),
				weekStart.plusDays(i),
				getEventsForDay(day, eventGroupId));

			events.add(weekDayWithEvents);
		}

		return events;
	}

	private List<EventDto> getEventsForDay(LocalDate day, Long eventGroupId) {

		return eventGroupQueries.findById(eventGroupId)
			.orElseThrow(EntityNotFoundException::new)
			.getEvents().stream()
			.filter(event -> event.getEventStartTime().isEqual(day.atTime(event.getEventStartTime().toLocalTime())))
			.map(event -> new EventDto(
				event.getId(),
				new ShowEventDto(event.getShow().getId(), event.getShow().getTitle()),
				event.getEventStartTime()
			))
			.toList();
	}

	public void saveEvent(EventForm eventForm) {

		Assert.notNull(eventForm, "eventForm must not be null");

		Event event = new Event(showQueries.findById(eventForm.getShow())
			.orElseThrow(EntityNotFoundException::new),
			eventGroupQueries.findById(eventForm.getEventGroupId())
				.orElseThrow(EntityNotFoundException::new),
			eventForm.getEventStartTime(),
			new HashSet<>());

		eventRepository.saveOrUpdate(event);

		availabilityApplicationService.initializeAvailabilities(event);
	}

	public void updateEvent(EventForm eventForm) {

		Assert.notNull(eventForm, "eventForm must not be null");

		Event event = eventQueries.findById(eventForm.getId())
			.orElseThrow(EntityNotFoundException::new);

		event.update(showQueries.findById(eventForm.getShow())
				.orElseThrow(EntityNotFoundException::new),
			eventGroupQueries.findById(eventForm.getEventGroupId())
				.orElseThrow(EntityNotFoundException::new),
			eventForm.getEventStartTime());

		eventRepository.saveOrUpdate(event);
	}

	public void deleteEvent(Long id) {

		Assert.notNull(id, "id must not be null");

		availabilityApplicationService.deleteAvailabilitiesForEvent(id);

		Event event = eventQueries.get(id);
		for (EventPositionAssignment eventPositionAssignment : event.getAssignments()) {
			eventPositionAssignmentRepository.delete(eventPositionAssignment);
		}

		event = eventQueries.get(id);
		eventRepository.delete(event);
	}

	public List<EventGroupListDto> getEventGroups() {

		return eventGroupQueries.getAll().stream()
			.map(eventGroup -> new EventGroupListDto(eventGroup.getId(),
				eventGroup.getName(),
				eventGroup.isAvailabilityActive(),
				eventGroup.isActive()))
			.toList();
	}

	public void saveNewGroup(String name) {

		EventGroup eventGroup = new EventGroup(name, Collections.emptySet(), false, false);
		eventGroupRepository.saveOrUpdate(eventGroup);
	}

	public void changeEventGroupName(String name, Long id) {

		EventGroup eventGroup = eventGroupQueries.findById(id)
			.orElseThrow(EntityNotFoundException::new);

		eventGroup.setName(name);

		eventGroupRepository.saveOrUpdate(eventGroup);
	}

	public void deleteEventGroup(Long id) {

		EventGroup eventGroup = eventGroupQueries.get(id);

		for (Event event : eventGroup.getEvents()) {

			deleteEvent(event.getId());
		}

		eventGroup = eventGroupQueries.get(id);

		eventGroupRepository.delete(eventGroup);
	}

	public void changeAvailabilityActive(Long eventGroupId, boolean active) {

		EventGroup eventGroup = eventGroupQueries.findById(eventGroupId)
			.orElseThrow(EntityNotFoundException::new);

		eventGroup.setAvailabilityActive(active);

		eventGroupRepository.saveOrUpdate(eventGroup);
	}
}
