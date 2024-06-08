

package com.capitolmanager.show.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.event.domain.EventPositionAssignment;
import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;


@Service
public class HibernateEventPositionAssignmentRepository extends AbstractHibernateRepository<EventPositionAssignment> implements Repository<EventPositionAssignment> {

	protected HibernateEventPositionAssignmentRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}
