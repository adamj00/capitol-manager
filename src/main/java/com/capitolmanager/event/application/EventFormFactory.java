

package com.capitolmanager.event.application;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.interfaces.manager.EventForm;


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
			event.getEventStartTime());
	}
}
