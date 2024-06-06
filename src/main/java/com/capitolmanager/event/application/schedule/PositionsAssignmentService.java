/*
 * Created on 24-05-2024 23:55 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.application.schedule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.event.application.EventGroupQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.event.domain.EventPositionAssignment;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.position.domain.Position;
import com.capitolmanager.position.domain.PositionType;
import com.capitolmanager.user.domain.User;


@Service
public class PositionsAssignmentService {

	private final EventGroupQueries eventGroupQueries;
	private final Repository<EventPositionAssignment> eventPositionAssignmentRepository;

	@Autowired
	PositionsAssignmentService(EventGroupQueries eventGroupQueries, Repository<EventPositionAssignment> eventPositionAssignmentRepository) {

		Assert.notNull(eventGroupQueries, "eventGroupQueries must not be null");
		Assert.notNull(eventPositionAssignmentRepository, "eventPositionAssignmentRepository must not be null");

		this.eventGroupQueries = eventGroupQueries;
		this.eventPositionAssignmentRepository = eventPositionAssignmentRepository;
	}

	public void assignPositionsAutomatically(Long eventGroupId) {

		EventGroup eventGroup = eventGroupQueries.get(eventGroupId);

		clearPositionAssignments(eventGroup);

		List<Event> events = eventGroup.getEvents().stream()
			.sorted(Comparator.comparing(Event::getEventStartTime))
			.toList();

		for (Event event : events) {

			assignPositionsForEvent(event);
		}
	}

	private List<EmployeeWithPositionsHistory> getEmployeesWithPositionsHistory(Event event) {

		List<User> employeesAssignedToEvent = getUsersAssignedToEvent(event);

		return employeesAssignedToEvent.stream()
			.map(user -> new EmployeeWithPositionsHistory(user, getPositionHistoryForUser(event.getEventGroup(), user)))
			.toList();
	}

	private void clearPositionAssignments(EventGroup eventGroup) {

		for (Event event : eventGroup.getEvents()) {
			for (EventPositionAssignment assignment : event.getAssignments()) {
				assignment.setPosition(null);
				eventPositionAssignmentRepository.saveOrUpdate(assignment);
			}
		}
	}

	private List<User> getUsersAssignedToEvent(Event event) {

		return event.getAssignments().stream()
			.map(EventPositionAssignment::getUser)
			.filter(Objects::nonNull)
			.distinct()
			.toList();
	}

	private List<Position> getPositionHistoryForUser(EventGroup eventGroup, User user) {

		return eventGroup.getEvents().stream()
			.sorted(Comparator.comparing(Event::getEventStartTime))
			.flatMap(event -> event.getAssignments().stream())
			.filter(assignment -> user.equals(assignment.getUser()))
			.map(EventPositionAssignment::getPosition)
			.filter(Objects::nonNull)
			.toList();
	}

	private void assignPositionsForEvent(Event event) {

		List<Position> requiredPositions = getRequiredPositonsSortedByPriority(event);
		List<EmployeeWithPositionsHistory> availableEmployees = getEmployeesWithPositionsHistory(event);

		Set<EventPositionAssignment> assignments = event.getAssignments();

		for (Position position : requiredPositions) {
			User bestChoiceUser = determineBestEmployeeChoice(position, availableEmployees);

			if (bestChoiceUser == null) {
				return;
			}

			availableEmployees = availableEmployees.stream()
				.filter(userWithPositionsHistory -> !userWithPositionsHistory.getUser().equals(bestChoiceUser))
				.toList();

			for (EventPositionAssignment assignment : assignments) {
				if (assignment.getUser().equals(bestChoiceUser)) {
					assignment.setPosition(position);

					eventPositionAssignmentRepository.saveOrUpdate(assignment);
					break;
				}
			}
		}
		event.setAssignments(assignments);
	}

	private List<Position> getRequiredPositonsSortedByPriority(Event event) {

		List<Position> stagePositions = event.getShow().getStage().getRequiredPositions();

		List<Position> requiredPositions = new ArrayList<>();

		for (var stagePosition : stagePositions) {

			for (int i = 0; i < stagePosition.getQuantity(); i++) {
				requiredPositions.add(stagePosition);
			}
		}

		return requiredPositions.stream()
			.sorted(Comparator.comparing(position -> position.getPositionType().getPriority()))
			.toList();
	}

	private User determineBestEmployeeChoice(Position position, List<EmployeeWithPositionsHistory> availableEmployees) {

		return availableEmployees.stream()
			.map(employeeWithPositionsHistory -> new EmployeeWithStats(employeeWithPositionsHistory.getUser(),
				calculateQuantityOfPositionTypeForUser(employeeWithPositionsHistory, position.getPositionType()),
				calculateDistance(employeeWithPositionsHistory, position.getPositionType()))).min(Comparator.comparing(EmployeeWithStats::getQuantity)
				.thenComparing(EmployeeWithStats::getDistance))
			.map(EmployeeWithStats::getUser).orElse(null);
	}

	private int calculateQuantityOfPositionTypeForUser(EmployeeWithPositionsHistory employeeWithPositionsHistory, PositionType positionType) {

		return (int) employeeWithPositionsHistory.positionHistory.stream()
			.filter(position -> position.getPositionType() == positionType)
			.count();
	}

	private int calculateDistance(EmployeeWithPositionsHistory employeeWithPositionsHistory, PositionType positionType) {

		var history = employeeWithPositionsHistory.getPositionHistory();
		for (int i = 0; i < history.size(); i++) {
			if (history.get(i).getPositionType() == positionType) {

				return history.size() - i;
			}
		}

		return -1;
	}

	static class EmployeeWithStats {

		private final User user;
		private final int quantity;
		private final int distance;

		public EmployeeWithStats(User user, int quantity, int distance) {

			this.user = user;
			this.quantity = quantity;
			this.distance = distance;
		}

		public User getUser() {

			return user;
		}

		public int getQuantity() {

			return quantity;
		}

		public int getDistance() {

			return distance;
		}
	}

	static class EmployeeWithPositionsHistory {

		private final User user;
		private final List<Position> positionHistory;

		public EmployeeWithPositionsHistory(User user, List<Position> positionHistory) {

			this.user = user;
			this.positionHistory = positionHistory;
		}

		public User getUser() {

			return user;
		}

		public List<Position> getPositionHistory() {

			return positionHistory;
		}
	}
}
