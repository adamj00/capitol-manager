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
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.interfaces.EventForm;
import com.capitolmanager.hibernate.Repository;
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

	private final ShowQueries showQueries;
	private final Repository<Event> eventRepository;
	private final EventQueries eventQueries;


	@Autowired
	EventApplicationService(ShowQueries showQueries, Repository<Event> eventRepository, EventQueries eventQueries) {

		Assert.notNull(showQueries, "showQueries must not be null");
		Assert.notNull(eventRepository, "eventRepository must not be null");
		Assert.notNull(eventQueries, "eventQueries must not be null");

		this.showQueries = showQueries;
		this.eventRepository = eventRepository;
		this.eventQueries = eventQueries;
	}

	public List<WeekDayWithEvents> getEventsByWeek(LocalDate weekStart) {

		var events = new ArrayList<WeekDayWithEvents>();

		for (int i=0; i<7; i++) {

			LocalDate day = weekStart.plusDays(i);

			var weekDayWithEvents = new WeekDayWithEvents(WEEK_DAYS.get(i) + " " + DateUtils.formatLocalDateToDDMM(day),
				weekStart.plusDays(i),
				getEventsForDay(day));

			events.add(weekDayWithEvents);
		}

		return events;
	}

	private List<EventDto> getEventsForDay(LocalDate day) {

		return eventQueries.getAll().stream()
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
			eventForm.getEventStartTime(),
			eventForm.getShiftStartTime(),
			eventForm.getNotes());

		eventRepository.saveOrUpdate(event);
	}

	public void updateEvent(EventForm eventForm) {

		Assert.notNull(eventForm, "eventForm must not be null");

		Event event = eventQueries.findById(eventForm.getId())
			.orElseThrow(EntityNotFoundException::new);

		event.update(showQueries.findById(eventForm.getShow())
				.orElseThrow(EntityNotFoundException::new),
			eventForm.getEventStartTime(),
			eventForm.getShiftStartTime(),
			eventForm.getNotes());

		eventRepository.saveOrUpdate(event);
	}

	public void deleteEvent(Long id) {

		Assert.notNull(id, "id must not be null");

		Event event = eventQueries.findById(id)
			.orElseThrow(EntityNotFoundException::new);

		eventRepository.delete(event);
	}
}
