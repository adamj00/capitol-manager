
package com.capitolmanager.event.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.event.application.EventGroupQueries;
import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.hibernate.AbstractHibernateQueries;


@Service
public class HibernateEventGroupQueries extends AbstractHibernateQueries<EventGroup> implements EventGroupQueries {

	protected HibernateEventGroupQueries(SessionFactory sessionFactory) {

		super(sessionFactory, EventGroup.class);
	}
}
