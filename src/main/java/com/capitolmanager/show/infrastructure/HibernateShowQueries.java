

package com.capitolmanager.show.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.hibernate.AbstractHibernateQueries;
import com.capitolmanager.show.application.ShowQueries;
import com.capitolmanager.show.domain.Show;


@Service
public class HibernateShowQueries extends AbstractHibernateQueries<Show> implements ShowQueries {

	protected HibernateShowQueries(SessionFactory sessionFactory) {

		super(sessionFactory, Show.class);
	}
}
