/*
 * Created on 14-05-2024 11:44 by ajarzabe
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

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitolmanager.availability.application.AvailabilityQueries;
import com.capitolmanager.event.application.EventGroupQueries;
import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.schedule.domain.EventPositionAssignment;
import com.capitolmanager.schedule.domain.Schedule;
import com.capitolmanager.user.application.UserQueries;
import com.capitolmanager.user.domain.User;
import com.capitolmanager.user.domain.UserRole;
import com.capitolmanager.utils.DateUtils;


@Service
public class ScheduleApplicationService {

	@Autowired private Repository<Schedule> scheduleRepository;
	@Autowired private ScheduleQueries scheduleQueries;
	@Autowired private EventGroupQueries eventGroupQueries;
	@Autowired private UserQueries userQueries;
	@Autowired private AvailabilityQueries availabilityQueries;
	@Autowired private EventQueries eventQueries;
	@Autowired private Repository<EventPositionAssignment> eventPositionAssignmentRepository;

	public List<EmployeeScheduleDto> getEmployees(Long eventGroupId) {

		Schedule schedule = scheduleQueries.findByEventGroup(eventGroupId)
			.orElse(getNewSchedule(eventGroupId));

		if (schedule.getId() == null) {
			scheduleRepository.saveOrUpdate(schedule);
		}

		return userQueries.getAll().stream()
			.filter(user -> user.getRole() == UserRole.EMPLOYEE)
			.map(user -> new EmployeeScheduleDto(user.getId(), user.getFirstName() + " " + user.getLastName(),
				getAssignedEventsForEmployee(user.getId(), schedule),
				getAvailability(eventGroupId, user.getId())
			))
			.sorted(Comparator.comparing(EmployeeScheduleDto::getName))
			.toList();
	}

	public List<EventScheduleDto> getEvents(Long eventGroupId) {

		Schedule schedule = scheduleQueries.findByEventGroup(eventGroupId)
			.orElseThrow(EntityNotFoundException::new);

		return eventGroupQueries.findById(schedule.getEventGroup().getId())
			.orElseThrow(EntityNotFoundException::new)
			.getEvents().stream()
			.sorted(Comparator.comparing(Event::getEventStartTime))
			.map(event -> new EventScheduleDto(event.getId(), schedule.getId(), event.getShow().getTitle(),
				getAssignedEmployeesCount(schedule, event.getId()),
				getRequiredEmployeesCount(event),
				DateUtils.formatLocalDateTime(event.getEventStartTime())))
			.toList();
	}

	public void updateSchedule(Long userId, Long eventId, Long scheduleId, boolean value) {

		Schedule schedule = scheduleQueries.findById(scheduleId)
			.orElseThrow(EntityNotFoundException::new);

		Optional<EventPositionAssignment> userAssignment = schedule.getAssignments().stream()
			.filter(assignment -> assignment.getUser().getId().equals(userId))
			.filter(assignment -> assignment.getEvent().getId().equals(eventId))
			.findAny();

		if (value && userAssignment.isEmpty()) {

			Event event = eventQueries.findById(eventId)
				.orElseThrow(EntityNotFoundException::new);

			User user = userQueries.findById(userId)
				.orElseThrow(EntityNotFoundException::new);

			var eventPositionAssignment = new EventPositionAssignment(schedule, event, null, user);
			schedule.getAssignments().add(eventPositionAssignment);

			eventPositionAssignmentRepository.saveOrUpdate(eventPositionAssignment);

			scheduleRepository.saveOrUpdate(schedule);
		}

		else if (!value && userAssignment.isPresent()) {

			var eventPositionAssignment = userAssignment.get();

			schedule.getAssignments().remove(userAssignment.get());

			scheduleRepository.saveOrUpdate(schedule);

			eventPositionAssignmentRepository.delete(eventPositionAssignment);
		}
	}

	public String getEventGroupName(Long eventGroupId) {

		return eventGroupQueries.findById(eventGroupId)
			.orElseThrow(EntityNotFoundException::new)
			.getName();
	}

	private Schedule getNewSchedule(Long eventGroupId) {

		var eventGroup = eventGroupQueries.findById(eventGroupId)
			.orElseThrow(EntityNotFoundException::new);

		Schedule schedule = new Schedule(eventGroup, null);

		schedule.setAssignments(new HashSet<>());

		return schedule;
	}

	private Set<EventPositionAssignment> initializeEmptyAssignment(Schedule schedule, EventGroup eventGroup) {

		Set<EventPositionAssignment> assignment = eventGroup.getEvents().stream()
			.flatMap(event -> event.getShow().getStage().getRequiredPositions().stream()
				.map(position -> new EventPositionAssignment(schedule, event, position, null))
			)
			.collect(Collectors.toSet());

		return assignment;
	}

	private List<Long> getAssignedEventsForEmployee(Long userId, Schedule schedule) {

		return schedule.getAssignments().stream()
			.filter(assignment -> assignment.getUser().getId() == userId)
			.map(assignment -> assignment.getEvent().getId())
			.toList();
	}

	private int getAssignedEmployeesCount(Schedule schedule, Long eventId) {

		return (int) schedule.getAssignments().stream()
			.filter(assignment -> Objects.equals(assignment.getEvent().getId(), eventId))
			.filter(assignment -> assignment.getUser() != null)
			.map(assignment -> assignment.getUser().getId())
			.distinct()
			.count();
	}

	private List<Long> getAvailability(Long eventGroupId, Long userId) {

		var availabilities = availabilityQueries.getByUserIdAndEventGroup(userId, eventGroupId);

		return availabilities.stream()
			.filter(availability -> availability.getAvailable() != null && availability.getAvailable())
			.map(availability -> availability.getEvent().getId())
			.toList();
	}

	private int getRequiredEmployeesCount(Event event) {

		var requiredPositions = event.getShow().getStage().getRequiredPositions().stream().distinct().toList();

		int count = 0;

		for (var position : requiredPositions) {
			count += position.getQuantity();
		}

		return count;
	}
}
