
package com.capitolmanager.event.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.event.domain.Event;
import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;


@Service
public class HibernateEventRepository extends AbstractHibernateRepository<Event> implements Repository<Event> {

	protected HibernateEventRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}