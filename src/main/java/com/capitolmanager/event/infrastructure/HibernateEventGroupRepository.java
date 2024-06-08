

package com.capitolmanager.event.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;


@Service
public class HibernateEventGroupRepository extends AbstractHibernateRepository<EventGroup> implements Repository<EventGroup> {

	protected HibernateEventGroupRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}
