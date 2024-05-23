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

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitolmanager.availability.application.AvailabilityQueries;
import com.capitolmanager.event.application.EventGroupQueries;
import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.position.application.PositionQueries;
import com.capitolmanager.schedule.domain.EventPositionAssignment;
import com.capitolmanager.schedule.domain.Schedule;
import com.capitolmanager.user.application.UserApplicationService;
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
	@Autowired private PositionQueries positionQueries;
	@Autowired private UserApplicationService userApplicationService;

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

	public List<PositionAssignmentEventDto> getEventsForAssignment(Long eventGroupId) {

		var eventGroup = eventGroupQueries.findById(eventGroupId)
			.orElseThrow(EntityNotFoundException::new);

		var schedule = eventGroup.getSchedule();

		return eventGroup.getEvents().stream()
			.sorted(Comparator.comparing(Event::getEventStartTime))
			.map(event -> new PositionAssignmentEventDto(event.getId(),
				schedule.getId(),
				event.getShow().getTitle(),
				getPositionsForEvent(event),
				DateUtils.formatLocalDateTime(event.getEventStartTime())))
			.toList();
	}

	public List<PositionAssignmentEmployeeDto> getEmployeesForAssignment(Long eventGroupId) {

		var eventGroup = eventGroupQueries.findById(eventGroupId)
			.orElseThrow(EntityNotFoundException::new);

		var schedule = eventGroup.getSchedule();

		return userQueries.getAll().stream()
			.filter(user -> userIsAssignedToAnyEvent(user.getId(), schedule))
			.map(user -> new PositionAssignmentEmployeeDto(user.getId(),
				user.toString(),
				getAssignmentsForUser(user.getId(), schedule),
				getAssignedEventsForEmployee(user.getId(), schedule)))
			.sorted(Comparator.comparing(PositionAssignmentEmployeeDto::getName))
			.toList();

	}

	public void assignPosition(Long scheduleId, Long eventId, Long userId, Long positionId) {

		var schedule = scheduleQueries.findById(scheduleId)
			.orElseThrow(EntityNotFoundException::new);

		var event = eventQueries.findById(eventId)
			.orElseThrow(EntityNotFoundException::new);

		var position = positionQueries.findById(positionId)
			.orElseThrow(EntityNotFoundException::new);

		var user = userQueries.findById(userId)
			.orElseThrow(EntityNotFoundException::new);

		var assignmentOptional = schedule.getAssignments().stream()
			.filter(positionAssignment -> positionAssignment.getEvent().getId().equals(eventId))
			.filter(positionAssignment -> positionAssignment.getUser() != null
				&& positionAssignment.getUser().getId().equals(userId))
			.findAny();

		EventPositionAssignment assignment = assignmentOptional.orElse(
			new EventPositionAssignment(schedule, event, position, user)
		);

		assignment.setPosition(position);
		eventPositionAssignmentRepository.saveOrUpdate(assignment);

	}

	public List<AssignmentDto> getAssignments(Long eventGroupId) {

		var schedule = scheduleQueries.findByEventGroup(eventGroupId)
			.orElseThrow(EntityNotFoundException::new);

		return schedule.getAssignments().stream()
			.filter(assignment -> assignment.getPosition() != null)
			.map(assignment -> new AssignmentDto(assignment.getId(),
				assignment.getUser().getId(),
				assignment.getEvent().getId(),
				assignment.getPosition().getId()
			))
			.toList();
	}

	public List<ScheduleListDto> getScheduleList() {

		Long userId = userApplicationService.getLoggedUserId();

		return scheduleQueries.getAll().stream()
			.filter(Schedule::isActive)
			.map(schedule -> mapScheduleToListDto(schedule, userId))
			.toList();
	}

	public void changeScheduleActivity(Long eventGroupId, boolean value) {

		Schedule schedule = scheduleQueries.findByEventGroup(eventGroupId)
			.orElseThrow(EntityNotFoundException::new);

		schedule.setActive(value);

		scheduleRepository.saveOrUpdate(schedule);
	}


	private ScheduleListDto mapScheduleToListDto(Schedule schedule, Long userId) {

		return new ScheduleListDto(schedule.getId(),
			schedule.getEventGroup().getName(),
			getNumberOfEventsAssignedToUserForSchedule(schedule, userId));
	}

	private int getNumberOfEventsAssignedToUserForSchedule(Schedule schedule, Long userId) {

		return (int)schedule.getAssignments().stream()
			.filter(assignment -> assignment.getUser() != null)
			.filter(assignment -> assignment.getUser().getId().equals(userId))
			.count();
	}
	private List<PositionDto> getPositionsForEvent(Event event) {

		return event
			.getShow().getStage().getRequiredPositions().stream()
			.distinct()
			.map(stagePosition -> new PositionDto(stagePosition.getPosition().getId(),
				stagePosition.getPosition().getName(),
				stagePosition.getPosition().getPositionType(),
				stagePosition.getQuantity()))
			.toList();
	}

	private boolean userIsAssignedToAnyEvent(Long userId, Schedule schedule) {

		return schedule.getAssignments().stream()
			.anyMatch(assignment -> assignment.getUser().getId().equals(userId)
				&& assignment.getEvent() != null);

	}

	private List<AssignmentDto> getAssignmentsForUser(Long userId, Schedule schedule) {

		return schedule.getAssignments().stream()
			.filter(assignment -> assignment.getUser().getId().equals(userId))
			.map(assignment -> new AssignmentDto(assignment.getId(),
				userId,
				assignment.getEvent().getId(),
				assignment.getPosition() == null ? null : assignment.getPosition().getId()
				))
			.toList();
	}

	private Schedule getNewSchedule(Long eventGroupId) {

		var eventGroup = eventGroupQueries.findById(eventGroupId)
			.orElseThrow(EntityNotFoundException::new);

		Schedule schedule = new Schedule(eventGroup, null);

		schedule.setAssignments(new HashSet<>());

		return schedule;
	}

	private List<Long> getAssignedEventsForEmployee(Long userId, Schedule schedule) {

		return schedule.getAssignments().stream()
			.filter(assignment -> Objects.equals(assignment.getUser().getId(), userId))
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
