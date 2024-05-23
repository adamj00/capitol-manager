
package com.capitolmanager.schedule.application;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.event.application.EventGroupQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.event.domain.EventPositionAssignment;
import com.capitolmanager.position.domain.Position;
import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.utils.StringUtils;


@Service
public class ScheduleViewService {

	private final UserApplicationService userApplicationService;
	private final EventGroupQueries eventGroupQueries;

	@Autowired
	ScheduleViewService(UserApplicationService userApplicationService, EventGroupQueries eventGroupQueries) {

		Assert.notNull(userApplicationService, "userApplicationService must not be null");
		Assert.notNull(eventGroupQueries, "eventGroupQueries must not be null");

		this.userApplicationService = userApplicationService;
		this.eventGroupQueries = eventGroupQueries;
	}

	public List<List<DayWithEvents>> getEventsForScheduleViewByWeeks(Long eventGroupId, boolean showOnlyUsers) {

		EventGroup eventGroup = eventGroupQueries.get(eventGroupId);
		Long userId = userApplicationService.getLoggedUserId();

		List<EventScheduleViewDto> events = eventGroup.getEvents().stream()
			.filter(event -> !showOnlyUsers || isUserAssignedToEvent(event, userId))
			.map(event -> mapEventToDto(event, userId))
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
						dayWithEvents.setDate(current);
					}
					else {
						dayWithEvents.setDate(current);
						dayWithEvents.setEvents(getEventsForDay(events, current));
					}
				}
				else {
					dayWithEvents.setDate(current);
				}
				week.add(dayWithEvents);
				current = current.plusDays(1);
			}
			weeks.add(week);
		}

		return weeks;
	}

	public String getTitle(Long eventGroupId) {

		return eventGroupQueries.get(eventGroupId)
			.getName();
	}

	private EventScheduleViewDto mapEventToDto(Event event, Long userId) {

		boolean isUserAssignedToEvent = isUserAssignedToEvent(event, userId);

		return new EventScheduleViewDto(event.getId(),
			event.getShow().getTitle(),
			event.getShow().getStage().getName(),
			"Dominika Piechanowska",
			event.getEventStartTime(),
			StringUtils.getDurationString(event.getShow().getDuration()),
			isUserAssignedToEvent,
			isUserAssignedToEvent ? getPositionForUser(event, userId) : null);
	}

	private boolean isUserAssignedToEvent(Event event, Long userId) {

		return event.getAssignments().stream()
			.anyMatch(assignment -> assignment.getUser().getId().equals(userId));
	}

	private List<EventScheduleViewDto> getEventsForDay(List<EventScheduleViewDto> events, LocalDate date) {

		return events.stream()
			.filter(eventScheduleViewDto -> eventScheduleViewDto.getEventStartTime()
				.toLocalDate().isEqual(date))
			.toList();
	}

	private String getPositionForUser(Event event, Long userId) {

		Optional<Position> position = event.getAssignments().stream()
			.filter(assignment -> assignment.getUser().getId().equals(userId))
			.map(EventPositionAssignment::getPosition)
			.filter(Objects::nonNull)
			.findFirst();

		return position.map(Position::getName)
			.orElse(null);
	}
}
