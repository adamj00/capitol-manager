
package com.capitolmanager.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.Assert;


public abstract class AbstractHibernateRepository<ENTITY extends AbstractEntity> {

	private final SessionFactory sessionFactory;

	protected AbstractHibernateRepository(SessionFactory sessionFactory) {

		Assert.notNull(sessionFactory, "sessionFactory must not be null");

		this.sessionFactory = sessionFactory;
	}

	public void saveOrUpdate(ENTITY entity) {

		Session session = sessionFactory.openSession();

		session.beginTransaction();

		session.saveOrUpdate(entity);

		session.getTransaction().commit();

		session.close();
	}

	public void delete(ENTITY entity) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		entity = (ENTITY) session.merge(entity);
		session.delete(entity);
		session.getTransaction().commit();
		session.close();
	}
}
