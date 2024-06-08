

package com.capitolmanager.event.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.hibernate.AbstractHibernateQueries;


@Service
public class HibernateEventQueries extends AbstractHibernateQueries<Event> implements EventQueries {

	protected HibernateEventQueries(SessionFactory sessionFactory) {

		super(sessionFactory, Event.class);
	}
}
