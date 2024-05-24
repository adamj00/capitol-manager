package com.capitolmanager.homepage.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.event.application.EventGroupQueries;
import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.event.domain.EventPositionAssignment;
import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.utils.DateUtils;
import com.capitolmanager.utils.StringUtils;


@Service
public class HomepageService {

	private final UserApplicationService userApplicationService;
	private final EventQueries eventQueries;
	private final EventGroupQueries eventGroupQueries;


	@Autowired
	HomepageService(UserApplicationService userApplicationService, EventQueries eventQueries, EventGroupQueries eventGroupQueries) {

		Assert.notNull(userApplicationService, "userApplicationService must not be null");
		Assert.notNull(eventQueries, "eventQueries must not be null");
		Assert.notNull(eventGroupQueries, "eventGroupQueries must not be null");

		this.userApplicationService = userApplicationService;
		this.eventQueries = eventQueries;
		this.eventGroupQueries = eventGroupQueries;
	}

	public List<EventListDto> getCloseEventsForLoggedUser() {

		Long userId = userApplicationService.getLoggedUserId();

		return eventGroupQueries.getAll().stream()
			.filter(EventGroup::isActive)
			.flatMap(eventGroup -> eventGroup.getEvents().stream())
			.flatMap(event -> event.getAssignments().stream())
			.filter(assignment -> assignment.getUser().getId().equals(userId))
			.map(EventPositionAssignment::getEvent)
			.filter(event -> isFromCurrentWeek(event.getEventStartTime().toLocalDate()))
			.distinct()
			.sorted(Comparator.comparing(Event::getEventStartTime))
			.map(event -> new EventListDto(event.getId(), event.toString()))
			.toList();
	}

	public Optional<EventDetailsDto> getEventDetails(Long id) {

		Long userId = userApplicationService.getLoggedUserId();

		return eventQueries.findById(id)
			.map(event -> mapToDto(event, userId));
	}

	public String getLoggedUserName() {

		return userApplicationService.getLoggedUserName();
	}

	private EventDetailsDto mapToDto(Event event, Long userId) {

		return new EventDetailsDto(event.getId(),
			event.getShow().getTitle(),
			DateUtils.formatLocalDateTimeWithGodzina(event.getEventStartTime()),
			StringUtils.getDurationString(event.getShow().getDuration()),
			getPositionNameForUserAndEvent(userId, event),
			event.getShow().getStage().getName(),
			event.toString()
		);
	}

	private boolean isFromCurrentWeek(LocalDate localDate) {

		LocalDate today = LocalDate.now();
		LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

		return (localDate.isAfter(today) || localDate.isEqual(today)) && !localDate.isBefore(startOfWeek) && !localDate.isAfter(endOfWeek);
	}

	private String getPositionNameForUserAndEvent(Long userId, Event event) {

		return event.getAssignments().stream()
			.filter(assignment -> assignment.getUser().getId().equals(userId))
			.filter(assignment -> assignment.getPosition() != null)
			.map(assignment -> assignment.getPosition().getName())
			.findFirst()
			.orElse(null);
	}
}
