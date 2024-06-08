
package com.capitolmanager.position.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.AbstractHibernateQueries;
import com.capitolmanager.position.application.PositionQueries;
import com.capitolmanager.position.domain.Position;


@Service
public class HibernatePositionQueries extends AbstractHibernateQueries<Position> implements PositionQueries {

	protected HibernatePositionQueries(SessionFactory sessionFactory) {

		super(sessionFactory, Position.class);

		Assert.notNull(sessionFactory, "sessionFactory must not be null");
	}
}
