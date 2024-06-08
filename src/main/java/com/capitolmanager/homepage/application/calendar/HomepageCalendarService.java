

package com.capitolmanager.homepage.application.calendar;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.utils.StringUtils;


@Service
public class HomepageCalendarService {
	
	private final UserApplicationService userApplicationService;
	private final EventQueries eventQueries;


	@Autowired
	HomepageCalendarService(UserApplicationService userApplicationService, EventQueries eventQueries) {

		Assert.notNull(userApplicationService, "userApplicationService must not be null");
		Assert.notNull(eventQueries, "eventQueries must not be null");

		this.userApplicationService = userApplicationService;
		this.eventQueries = eventQueries;
	}

	public List<List<CalendarDay>> getUserEventsForMonthByWeeks(int year, int month) {
		
		Long userId = userApplicationService.getLoggedUserId();

		List<EventCalendarDto> events = eventQueries.getAll().stream()
			.filter(event -> event.getEventGroup().isActive())
			.filter(event -> isUserAssignedToEvent(event, userId))
			.map(this::mapEventToDto)
			.sorted(Comparator.comparing(EventCalendarDto::getEventStartTime))
			.toList();

		LocalDate firstOfMonth = LocalDate.of(year, month, 1);
		LocalDate lastOfMonth = LocalDate.of(year, month, 1)
			.withDayOfMonth(LocalDate.of(year, month, 1).lengthOfMonth());
		LocalDate current = firstOfMonth.with(ChronoField.DAY_OF_WEEK, 1);

		List<List<CalendarDay>> weeks = new ArrayList<>();

		while (!current.isAfter(lastOfMonth)) {
			List<CalendarDay> week = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				CalendarDay calendarDay = new CalendarDay();
				if (!current.isAfter(lastOfMonth)) {
					if (current.isBefore(firstOfMonth)) {
						calendarDay.setDate(null);
					}
					else {
						calendarDay.setDate(current);
						calendarDay.setEvents(getEventsForDay(events, current));
					}
				}
				else {
					calendarDay.setDate(null);
				}
				week.add(calendarDay);
				current = current.plusDays(1);
			}
			weeks.add(week);
		}

		return weeks;
	}

	private EventCalendarDto mapEventToDto(Event event) {

		return new EventCalendarDto(event.getId(),
			event.getShow().getTitle(),
			event.getShow().getStage().getName(),
			event.getEventStartTime(),
			StringUtils.getDurationString(event.getShow().getDuration()));
	}

	private boolean isUserAssignedToEvent(Event event, Long userId) {

		if (userApplicationService.isUserManager(userId)) {
			return true;
		}

		return event.getAssignments().stream()
			.anyMatch(assignment -> assignment.getUser().getId().equals(userId));
	}

	List<EventCalendarDto> getEventsForDay(List<EventCalendarDto> events, LocalDate date) {

		return events.stream()
			.filter(eventCalendarDto -> eventCalendarDto.getEventStartTime()
				.toLocalDate().isEqual(date))
			.toList();
	}
}
