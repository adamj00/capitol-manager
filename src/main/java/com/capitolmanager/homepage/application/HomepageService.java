package com.capitolmanager.homepage.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.schedule.application.ScheduleQueries;
import com.capitolmanager.schedule.domain.EventPositionAssignment;
import com.capitolmanager.schedule.domain.Schedule;
import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.utils.DateUtils;
import com.capitolmanager.utils.StringUtils;


@Service
public class HomepageService {

	private final ScheduleQueries scheduleQueries;
	private final UserApplicationService userApplicationService;
	private final EventQueries eventQueries;


	@Autowired
	HomepageService(ScheduleQueries scheduleQueries, UserApplicationService userApplicationService, EventQueries eventQueries) {

		Assert.notNull(scheduleQueries, "scheduleQueries must not be null");
		Assert.notNull(userApplicationService, "userApplicationService must not be null");
		Assert.notNull(eventQueries, "eventQueries must not be null");

		this.scheduleQueries = scheduleQueries;
		this.userApplicationService = userApplicationService;
		this.eventQueries = eventQueries;
	}

	public List<EventListDto> getCloseEventsForLoggedUser() {

		Long userId = userApplicationService.getLoggedUserId();

		return scheduleQueries.getAll().stream()
			.filter(Schedule::isActive)
			.flatMap(schedule -> schedule.getAssignments().stream())
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
			getPositionNameForUserAndEvent(userId, event.getId()),
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

	private String getPositionNameForUserAndEvent(Long userId, Long eventId) {

		Schedule schedule = eventQueries.findById(eventId)
			.orElseThrow(EntityNotFoundException::new)
			.getEventGroup().getSchedule();

		return schedule.getAssignments().stream()
			.filter(assignment -> assignment.getUser().getId().equals(userId))
			.filter(assignment -> assignment.getEvent().getId().equals(eventId))
			.filter(assignment -> assignment.getPosition() != null)
			.map(assignment -> assignment.getPosition().getName())
			.findFirst()
			.orElse(null);
	}
}
