/*
 * Created on 22-05-2024 19:35 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.schedule.application;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.event.domain.Event;
import com.capitolmanager.position.domain.Position;
import com.capitolmanager.schedule.domain.EventPositionAssignment;
import com.capitolmanager.schedule.domain.Schedule;
import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.utils.StringUtils;


@Service
public class ScheduleViewService {

	private final ScheduleQueries scheduleQueries;
	private final UserApplicationService userApplicationService;

	@Autowired
	ScheduleViewService(ScheduleQueries scheduleQueries, UserApplicationService userApplicationService) {

		Assert.notNull(scheduleQueries, "scheduleQueries must not be null");
		Assert.notNull(userApplicationService, "userApplicationService must not be null");

		this.scheduleQueries = scheduleQueries;
		this.userApplicationService = userApplicationService;
	}

	public List<List<DayWithEvents>> getEventsForScheduleViewByWeeks(Long scheduleId, boolean showOnlyUsers) {

		Schedule schedule = scheduleQueries.findById(scheduleId)
			.orElseThrow(EntityNotFoundException::new);

		Long userId = userApplicationService.getLoggedUserId();

		List<EventScheduleViewDto> events = schedule.getEventGroup().getEvents().stream()
			.filter(event -> !showOnlyUsers || isUserAssignedToEvent(schedule, event, userId))
			.map(event -> mapEventToDto(schedule, event, userId))
			.sorted(Comparator.comparing(EventScheduleViewDto::getEventStartTime))
			.toList();

		LocalDate firstOfMonth = events.get(0).getEventStartTime().toLocalDate();
		LocalDate lastOfMonth = events.get(events.size() - 1).getEventStartTime().toLocalDate();
		LocalDate current = firstOfMonth.with(ChronoField.DAY_OF_WEEK, 1);

		List<List<DayWithEvents>> weeks = new ArrayList<>();

		while (!current.isAfter(lastOfMonth)) {
			List<DayWithEvents> week = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				DayWithEvents dayWithEvents = new DayWithEvents();
				if (!current.isAfter(lastOfMonth)) {
					if (current.isBefore(firstOfMonth)) {
						dayWithEvents.setDate(null);
					}
					else {
						dayWithEvents.setDate(current);
						dayWithEvents.setEvents(getEventsForDay(events, current));
					}
				}
				else {
					dayWithEvents.setDate(null);
				}
				week.add(dayWithEvents);
				current = current.plusDays(1);
			}
			weeks.add(week);
		}

		return weeks;
	}

	public String getTitle(Long scheduleId) {

		return scheduleQueries.findById(scheduleId)
			.orElseThrow(EntityNotFoundException::new)
			.getEventGroup().getName();
	}

	private EventScheduleViewDto mapEventToDto(Schedule schedule, Event event, Long userId) {

		boolean isUserAssignedToEvent = isUserAssignedToEvent(schedule, event, userId);

		return new EventScheduleViewDto(event.getId(),
			event.getShow().getTitle(),
			event.getShow().getStage().getName(),
			"Dominika Piechanowska",
			event.getEventStartTime(),
			StringUtils.getDurationString(event.getShow().getDuration()),
			isUserAssignedToEvent,
			isUserAssignedToEvent ? getPositionForUser(schedule, event, userId) : null);
	}

	private boolean isUserAssignedToEvent(Schedule schedule, Event event, Long userId) {

		return schedule.getAssignments().stream()
			.filter(assignment -> assignment.getUser().getId().equals(userId))
			.anyMatch(assignment -> assignment.getEvent().equals(event));
	}

	private List<EventScheduleViewDto> getEventsForDay(List<EventScheduleViewDto> events, LocalDate date) {

		return events.stream()
			.filter(eventScheduleViewDto -> eventScheduleViewDto.getEventStartTime()
				.toLocalDate().isEqual(date))
			.toList();
	}

	private String getPositionForUser(Schedule schedule, Event event, Long userId) {

		Optional<Position> position = schedule.getAssignments().stream()
			.filter(assignment -> assignment.getUser().getId().equals(userId))
			.filter(assignment -> assignment.getEvent().equals(event))
			.map(EventPositionAssignment::getPosition)
			.filter(Objects::nonNull)
			.findFirst();

		return position.map(Position::getName)
			.orElse(null);
	}
}
