
package com.capitolmanager.availability.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitolmanager.availability.domain.Availability;
import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.user.domain.User;


@Service
public class AvailabilityInitializer {

	@Autowired private EventQueries eventQueries;
	@Autowired private Repository<Availability> availabilityRepository;

	public void initializeAvailabilityForUser(User user) {

		var events = eventQueries.getAll();

		for (Event event : events) {

			Availability availability = new Availability(event, user, null);
			availabilityRepository.saveOrUpdate(availability);
		}
	}
}
