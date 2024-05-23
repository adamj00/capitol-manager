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
import com.capitolmanager.event.interfaces.EventForm;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.schedule.application.ScheduleQueries;
import com.capitolmanager.schedule.domain.EventPositionAssignment;
import com.capitolmanager.schedule.domain.Schedule;
import com.capitolmanager.show.application.ShowEventDto;
import com.capitolmanager.show.application.ShowQueries;
import com.capitolmanager.utils.DateUtils;


@Service
public class EventApplicationService {

	public static final List<String> WEEK_DAYS = Arrays.asList(
		"pon",
		"wt",
		"śr",
		"czw",
		"pt",
		"sob",
		"ndz"
	);

	@Autowired private ShowQueries showQueries;
	@Autowired private Repository<Event> eventRepository;
	@Autowired private EventQueries eventQueries;
	@Autowired private AvailabilityApplicationService availabilityApplicationService;
	@Autowired private EventGroupQueries eventGroupQueries;
	@Autowired private Repository<EventGroup> eventGroupRepository;
	@Autowired private Repository<Schedule> scheduleRepository;
	@Autowired private Repository<EventPositionAssignment> eventPositionAssignmentRepository;

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
				event.getEventStartTime(),
				event.getShiftStartTime(),
				event.getNotes()
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
			eventForm.getShiftStartTime(),
			eventForm.getNotes());

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
			eventForm.getEventStartTime(),
			eventForm.getShiftStartTime(),
			eventForm.getNotes());

		eventRepository.saveOrUpdate(event);
	}

	public void deleteEvent(Long id) {

		Assert.notNull(id, "id must not be null");

		availabilityApplicationService.deleteAvailabilitiesForEvent(id);

		Event event = eventQueries.findById(id)
			.orElseThrow(EntityNotFoundException::new);

		eventRepository.delete(event);
	}

	public List<EventGroupListDto> getEventGroups() {

		return eventGroupQueries.getAll().stream()
			.map(eventGroup -> new EventGroupListDto(eventGroup.getId(),
				eventGroup.getName(),
				eventGroup.isAvailabilityActive(),
				eventGroup.getSchedule().isActive()))
			.toList();
	}

	public void saveNewGroup(String name) {

		EventGroup eventGroup = new EventGroup(name, Collections.emptySet(), null, false);
		eventGroupRepository.saveOrUpdate(eventGroup);

		Schedule newSchedule = new Schedule(eventGroup, new HashSet<>());
		scheduleRepository.saveOrUpdate(newSchedule);

		eventGroup.setSchedule(newSchedule);
		eventGroupRepository.saveOrUpdate(eventGroup);
	}

	public void changeEventGroupName(String name, Long id) {

		EventGroup eventGroup = eventGroupQueries.findById(id)
			.orElseThrow(EntityNotFoundException::new);

		eventGroup.setName(name);

		eventGroupRepository.saveOrUpdate(eventGroup);
	}

	public void deleteEventGroup(Long id) {

		EventGroup eventGroup = eventGroupQueries.findById(id)
			.orElseThrow(EntityNotFoundException::new);

		deleteAssignmentsForEventGroup(eventGroup);

		scheduleRepository.delete(eventGroup.getSchedule());

//		for (Event event : eventGroup.getEvents()) {
//
//			availabilityApplicationService.deleteAvailabilitiesForEvent(event.getId());
//
//			eventRepository.delete(event);
//		}
//
//		eventGroupRepository.delete(eventGroup);
	}

	public void changeAvailabilityActive(Long eventGroupId, boolean active) {

		EventGroup eventGroup = eventGroupQueries.findById(eventGroupId)
			.orElseThrow(EntityNotFoundException::new);

		eventGroup.setAvailabilityActive(active);

		eventGroupRepository.saveOrUpdate(eventGroup);
	}

	private void deleteAssignmentsForEventGroup(EventGroup eventGroup) {

		for (var assignment : eventGroup.getSchedule().getAssignments()) {
			eventPositionAssignmentRepository.delete(assignment);
		}
	}
}
