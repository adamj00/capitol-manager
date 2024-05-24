/*
 * Created on 16-05-2024 21:43 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

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
