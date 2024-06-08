

package com.capitolmanager.event.application.schedule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.availability.application.AvailabilityQueries;
import com.capitolmanager.event.application.EventGroupQueries;
import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.application.schedule.dto.AssignmentDto;
import com.capitolmanager.event.application.schedule.dto.EmployeeScheduleDto;
import com.capitolmanager.event.application.schedule.dto.EventScheduleDto;
import com.capitolmanager.event.application.schedule.dto.PositionAssignmentEmployeeDto;
import com.capitolmanager.event.application.schedule.dto.PositionAssignmentEventDto;
import com.capitolmanager.event.application.schedule.dto.PositionDto;
import com.capitolmanager.event.application.schedule.dto.ScheduleListDto;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.event.domain.EventPositionAssignment;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.position.application.PositionQueries;
import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.user.application.UserQueries;
import com.capitolmanager.user.domain.User;
import com.capitolmanager.user.domain.UserRole;
import com.capitolmanager.utils.DateUtils;


@Service
public class ScheduleApplicationService {

	private final EventGroupQueries eventGroupQueries;
	private final UserQueries userQueries;
	private final AvailabilityQueries availabilityQueries;
	private final EventQueries eventQueries;
	private final Repository<EventPositionAssignment> eventPositionAssignmentRepository;
	private final PositionQueries positionQueries;
	private final UserApplicationService userApplicationService;
	private final Repository<Event> eventRepository;
	private final Repository<EventGroup> eventGroupRepository;


	@Autowired
	ScheduleApplicationService(EventGroupQueries eventGroupQueries,
		UserQueries userQueries,
		AvailabilityQueries availabilityQueries,
		EventQueries eventQueries,
		Repository<EventPositionAssignment> eventPositionAssignmentRepository,
		PositionQueries positionQueries,
		UserApplicationService userApplicationService,
		Repository<Event> eventRepository,
		Repository<EventGroup> eventGroupRepository) {

		Assert.notNull(eventGroupQueries, "eventGroupQueries must not be null");
		Assert.notNull(userQueries, "userQueries must not be null");
		Assert.notNull(availabilityQueries, "availabilityQueries must not be null");
		Assert.notNull(eventQueries, "eventQueries must not be null");
		Assert.notNull(eventPositionAssignmentRepository, "eventPositionAssignmentRepository must not be null");
		Assert.notNull(positionQueries, "positionQueries must not be null");
		Assert.notNull(userApplicationService, "userApplicationService must not be null");
		Assert.notNull(eventRepository, "eventRepository must not be null");
		Assert.notNull(eventGroupRepository, "eventGroupRepository must not be null");

		this.eventGroupQueries = eventGroupQueries;
		this.userQueries = userQueries;
		this.availabilityQueries = availabilityQueries;
		this.eventQueries = eventQueries;
		this.eventPositionAssignmentRepository = eventPositionAssignmentRepository;
		this.positionQueries = positionQueries;
		this.userApplicationService = userApplicationService;
		this.eventRepository = eventRepository;
		this.eventGroupRepository = eventGroupRepository;
	}

	public List<EmployeeScheduleDto> getEmployees(Long eventGroupId) {

		EventGroup eventGroup = eventGroupQueries.get(eventGroupId);

		return userQueries.getAll().stream()
			.filter(user -> user.getRole() == UserRole.EMPLOYEE)
			.map(user -> new EmployeeScheduleDto(user.getId(), user.getFirstName() + " " + user.getLastName(),
				getAssignedEventsForEmployee(user.getId(), eventGroup),
				getAvailability(eventGroupId, user.getId())
			))
			.sorted(Comparator.comparing(EmployeeScheduleDto::getName))
			.toList();
	}

	public List<EventScheduleDto> getEvents(Long eventGroupId) {

		EventGroup eventGroup = eventGroupQueries.get(eventGroupId);

		return eventGroup.getEvents().stream()
			.sorted(Comparator.comparing(Event::getEventStartTime))
			.map(event -> new EventScheduleDto(event.getId(), event.getShow().getTitle(),
				getAssignedEmployeesCount(event),
				getRequiredEmployeesCount(event),
				DateUtils.formatLocalDateTime(event.getEventStartTime())))
			.toList();
	}

	public void updateSchedule(Long userId, Long eventId, boolean value) {

		Event event = eventQueries.get(eventId);

		Optional<EventPositionAssignment> userAssignment = event.getAssignments().stream()
			.filter(assignment -> assignment.getUser().getId().equals(userId))
			.findAny();

		if (value && userAssignment.isEmpty()) {

			User user = userQueries.findById(userId)
				.orElseThrow(EntityNotFoundException::new);

			var eventPositionAssignment = new EventPositionAssignment(event, null, user);
			event.getAssignments().add(eventPositionAssignment);

			eventPositionAssignmentRepository.saveOrUpdate(eventPositionAssignment);
			eventRepository.saveOrUpdate(event);
		}

		else if (!value && userAssignment.isPresent()) {

			var eventPositionAssignment = userAssignment.get();

			event.getAssignments().remove(userAssignment.get());

			eventRepository.saveOrUpdate(event);
			eventPositionAssignmentRepository.delete(eventPositionAssignment);
		}
	}

	public String getEventGroupName(Long eventGroupId) {

		return eventGroupQueries.findById(eventGroupId)
			.orElseThrow(EntityNotFoundException::new)
			.getName();
	}

	public List<PositionAssignmentEventDto> getEventsForAssignment(Long eventGroupId) {

		EventGroup eventGroup = eventGroupQueries.get(eventGroupId);

		return eventGroup.getEvents().stream()
			.sorted(Comparator.comparing(Event::getEventStartTime))
			.map(event -> new PositionAssignmentEventDto(event.getId(),
				event.getShow().getTitle(),
				getPositionsForEvent(event),
				DateUtils.formatLocalDateTime(event.getEventStartTime())))
			.toList();
	}

	public List<PositionAssignmentEmployeeDto> getEmployeesForAssignment(Long eventGroupId) {

		EventGroup eventGroup = eventGroupQueries.get(eventGroupId);

		return userQueries.getAll().stream()
			.filter(user -> userIsAssignedToAnyEvent(user.getId(), eventGroup))
			.map(user -> new PositionAssignmentEmployeeDto(user.getId(),
				user.toString(),
				getAssignmentsForUser(user.getId(), eventGroup),
				getAssignedEventsForEmployee(user.getId(), eventGroup)))
			.sorted(Comparator.comparing(PositionAssignmentEmployeeDto::getName))
			.toList();

	}

	public void assignPosition(Long eventId, Long userId, Long positionId) {


		Event event = eventQueries.get(eventId);

		var position = positionQueries.findById(positionId)
			.orElseThrow(EntityNotFoundException::new);

		User user = userQueries.get(userId);

		var assignmentOptional = event.getAssignments().stream()
			.filter(positionAssignment -> positionAssignment.getUser() != null
				&& positionAssignment.getUser().getId().equals(userId))
			.findAny();

		EventPositionAssignment assignment = assignmentOptional.orElse(
			new EventPositionAssignment(event, position, user)
		);

		assignment.setPosition(position);
		eventPositionAssignmentRepository.saveOrUpdate(assignment);

	}

	public List<AssignmentDto> getAssignments(Long eventGroupId) {

		EventGroup eventGroup = eventGroupQueries.get(eventGroupId);

		return eventGroup.getEvents().stream()
			.flatMap(event -> event.getAssignments().stream()
				.filter(assignment -> assignment.getPosition() != null)
				.map(assignment -> new AssignmentDto(
					assignment.getId(),
					assignment.getUser().getId(),
					assignment.getEvent().getId(),
					assignment.getPosition().getId()
				)))
			.toList();
	}

	public List<ScheduleListDto> getScheduleList() {

		Long userId = userApplicationService.getLoggedUserId();

		return eventGroupQueries.getAll().stream()
			.filter(EventGroup::isActive)
			.map(eventGroup -> mapEventGroupToListDto(eventGroup, userId))
			.toList();
	}

	public void changeScheduleActivity(Long eventGroupId, boolean value) {

		EventGroup eventGroup = eventGroupQueries.get(eventGroupId);

		eventGroup.setActive(value);

		eventGroupRepository.saveOrUpdate(eventGroup);
	}


	private ScheduleListDto mapEventGroupToListDto(EventGroup eventGroup, Long userId) {

		return new ScheduleListDto(eventGroup.getId(),
			eventGroup.getName(),
			getNumberOfEventsAssignedToUserForEventGroup(eventGroup, userId));
	}

	private int getNumberOfEventsAssignedToUserForEventGroup(EventGroup eventGroup, Long userId) {

		return (int)eventGroup.getEvents().stream()
			.filter(event -> event.getAssignments().stream()
				.anyMatch(assignment -> assignment.getUser() != null &&
					assignment.getUser().getId().equals(userId)))
			.count();
	}
	private List<PositionDto> getPositionsForEvent(Event event) {

		return event
			.getShow().getStage().getRequiredPositions().stream()
			.distinct()
			.map(position -> new PositionDto(position.getId(),
				position.getName(),
				position.getPositionType(),
				position.getQuantity()))
			.toList();
	}

	private boolean userIsAssignedToAnyEvent(Long userId, EventGroup eventGroup) {

		return eventGroup.getEvents().stream()
			.anyMatch(event -> event.getAssignments().stream()
				.anyMatch(assignment -> assignment.getUser().getId().equals(userId)));

	}

	private List<AssignmentDto> getAssignmentsForUser(Long userId, EventGroup eventGroup) {
		return eventGroup.getEvents().stream()
			.flatMap(event -> event.getAssignments().stream()
				.filter(assignment -> assignment.getUser().getId().equals(userId))
				.map(assignment -> new AssignmentDto(
					assignment.getId(),
					userId,
					assignment.getEvent().getId(),
					assignment.getPosition() == null ? null : assignment.getPosition().getId()
				)))
			.toList();
	}

	private List<Long> getAssignedEventsForEmployee(Long userId, EventGroup eventGroup) {

		Set<Event> events = eventGroup.getEvents();

		List<Long> assignedEvents = new ArrayList<>();

		for (Event event : events) {

			if (event.getAssignments().stream()
				.anyMatch(assignment -> assignment.getUser().getId().equals(userId))) {

				assignedEvents.add(event.getId());
			}
		}

		return assignedEvents;
	}

	private int getAssignedEmployeesCount(Event event) {

		return (int) event.getAssignments().stream()
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
