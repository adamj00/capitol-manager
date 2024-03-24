/*
 * Created on 24-03-2024 20:21 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.Assert;


public abstract class AbstractHibernateRepository<Entity extends AbstractEntity> {

	private final SessionFactory sessionFactory;

	protected AbstractHibernateRepository(SessionFactory sessionFactory) {

		Assert.notNull(sessionFactory, "sessionFactory must not be null");

		this.sessionFactory = sessionFactory;
	}

	public void saveOrUpdate(Entity entity) {

		Session session = sessionFactory.openSession();

		session.beginTransaction();

		session.saveOrUpdate(entity);

		session.getTransaction().commit();

		session.close();
	}
}
