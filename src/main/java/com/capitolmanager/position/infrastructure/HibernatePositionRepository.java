

package com.capitolmanager.position.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.position.domain.Position;


@Service
public class HibernatePositionRepository extends AbstractHibernateRepository<Position> implements Repository<Position> {

	@Autowired
	HibernatePositionRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}
