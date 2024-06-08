
package com.capitolmanager.user.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.availability.application.AvailabilityQueries;
import com.capitolmanager.availability.domain.Availability;
import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.domain.EventPositionAssignment;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.payroll.application.PayrollApplicationService;
import com.capitolmanager.user.domain.User;


@Service
public class UserDeleter {

	private final Repository<User> userRepository;
	private final UserQueries userQueries;
	private final AvailabilityQueries availabilityQueries;
	private final Repository<Availability> availabilityRepository;
	private final EventQueries eventQueries;
	private final Repository<EventPositionAssignment> eventPositionAssignmentRepository;
	private final PayrollApplicationService payrollApplicationService;

	@Autowired
	UserDeleter(Repository<User> userRepository,
		UserQueries userQueries,
		AvailabilityQueries availabilityQueries,
		Repository<Availability> availabilityRepository,
		EventQueries eventQueries,
		Repository<EventPositionAssignment> eventPositionAssignmentRepository,
		PayrollApplicationService payrollApplicationService) {

		Assert.notNull(userRepository, "userRepository must not be null");
		Assert.notNull(userQueries, "userQueries must not be null");
		Assert.notNull(availabilityQueries, "availabilityQueries must not be null");
		Assert.notNull(availabilityRepository, "availabilityRepository must not be null");
		Assert.notNull(eventQueries, "eventQueries must not be null");
		Assert.notNull(eventPositionAssignmentRepository, "eventPositionAssignmentRepository must not be null");
		Assert.notNull(payrollApplicationService, "payrollApplicationService must not be null");

		this.userRepository = userRepository;
		this.userQueries = userQueries;
		this.availabilityQueries = availabilityQueries;
		this.availabilityRepository = availabilityRepository;
		this.eventQueries = eventQueries;
		this.eventPositionAssignmentRepository = eventPositionAssignmentRepository;
		this.payrollApplicationService = payrollApplicationService;
	}

	public void deleteUser(Long id) {

		deleteAvailabilities(id);
		deleteAssignments(id);
		deletePayrolls(id);

		User user = userQueries.get(id);
		userRepository.delete(user);
	}

	private void deleteAvailabilities(Long userId) {

		var availabilities = availabilityQueries.getAll();

		for (var availability : availabilities) {

			if (availability.getUser().getId().equals(userId)) {
				availabilityRepository.delete(availability);
			}
		}
	}

	private void deleteAssignments(Long userId) {

		for (Event event : eventQueries.getAll()) {

			for (EventPositionAssignment assignment : event.getAssignments()) {

				if (assignment.getUser().getId().equals(userId)) {

					eventPositionAssignmentRepository.delete(assignment);
				}
			}
		}
	}

	private void deletePayrolls(Long userId) {

		payrollApplicationService.deletePayrollsForUser(userId);
	}
}
