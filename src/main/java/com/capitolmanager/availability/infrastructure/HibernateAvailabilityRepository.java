

package com.capitolmanager.availability.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.availability.domain.Availability;
import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;


@Service
public class HibernateAvailabilityRepository extends AbstractHibernateRepository<Availability> implements Repository<Availability> {

	protected HibernateAvailabilityRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}
