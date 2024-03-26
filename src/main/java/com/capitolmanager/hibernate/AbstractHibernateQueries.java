/*
 * Created on 24-03-2024 20:03 by ajarzabe
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

import static com.capitolmanager.hibernate.AbstractEntity.D_ID;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service
public abstract class AbstractHibernateQueries <Entity extends AbstractEntity> {

	private final SessionFactory sessionFactory;
	private final Class<Entity> entityClass;

	protected AbstractHibernateQueries(SessionFactory sessionFactory, Class<Entity> entityClass) {

		Assert.notNull(sessionFactory, "sessionFactory must not be null");
		Assert.notNull(entityClass, "entityClass must not be null");

		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	public List<Entity> getAll() {

		try (Session session = sessionFactory.openSession()) {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			Root<Entity> root = criteriaQuery.from(entityClass);
			criteriaQuery.select(root)
				.orderBy(criteriaBuilder.asc(root.get(D_ID)));

			Query<Entity> query = session.createQuery(criteriaQuery);

			return query.getResultList();
		}
	}

	public Optional<Entity> findById(Long id) {

		Assert.notNull(id, "id must not be null");

		try (Session session = sessionFactory.openSession()) {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			Root<Entity> root = criteriaQuery.from(entityClass);

			criteriaQuery.select(root)
				.where(criteriaBuilder.equal(root.get(D_ID), id));

			Query<Entity> query = session.createQuery(criteriaQuery);

			Entity entity = query.uniqueResult();

			return Optional.ofNullable(entity);
		}
	}
}
