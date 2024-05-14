/*
 * Created on 23-04-2024 21:46 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.application;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.interfaces.EventForm;


@Service
public class EventFormFactory {

	private final EventQueries eventQueries;

	@Autowired
	EventFormFactory(EventQueries eventQueries) {

		Assert.notNull(eventQueries, "eventQueries must not be null");

		this.eventQueries = eventQueries;
	}

	public static EventForm getEmptyForm() {

		return new EventForm();
	}

	public EventForm getForm(Long id) {

		Event event = eventQueries.findById(id)
			.orElseThrow(EntityNotFoundException::new);

		return new EventForm(event.getId(),
			event.getShow().getId(),
			event.getEventGroup().getId(),
			event.getEventStartTime(),
			event.getShiftStartTime(),
			event.getNotes());
	}
}
