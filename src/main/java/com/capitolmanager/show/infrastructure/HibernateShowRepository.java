

package com.capitolmanager.show.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.show.domain.Show;


@Service
public class HibernateShowRepository extends AbstractHibernateRepository<Show> implements Repository<Show> {

	protected HibernateShowRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}
