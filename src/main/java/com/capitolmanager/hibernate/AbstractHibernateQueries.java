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

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service
public abstract class AbstractHibernateQueries <ENTITY extends AbstractEntity> {

	protected final SessionFactory sessionFactory;
	private final Class<ENTITY> entityClass;

	protected AbstractHibernateQueries(SessionFactory sessionFactory, Class<ENTITY> entityClass) {

		Assert.notNull(sessionFactory, "sessionFactory must not be null");
		Assert.notNull(entityClass, "entityClass must not be null");

		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	public List<ENTITY> getAll() {

		try (Session session = sessionFactory.openSession()) {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ENTITY> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			Root<ENTITY> root = criteriaQuery.from(entityClass);
			criteriaQuery.select(root)
				.orderBy(criteriaBuilder.asc(root.get(D_ID)));

			Query<ENTITY> query = session.createQuery(criteriaQuery);

			return query.getResultList();
		}
	}

	public Optional<ENTITY> findById(Long id) {

		Assert.notNull(id, "id must not be null");

		try (Session session = sessionFactory.openSession()) {

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ENTITY> criteriaQuery = criteriaBuilder.createQuery(entityClass);
			Root<ENTITY> root = criteriaQuery.from(entityClass);

			criteriaQuery.select(root)
				.where(criteriaBuilder.equal(root.get(D_ID), id));

			Query<ENTITY> query = session.createQuery(criteriaQuery);

			ENTITY entity = query.uniqueResult();

			return Optional.ofNullable(entity);
		}
	}

	public ENTITY get(Long id) {

		return findById(id)
			.orElseThrow(EntityNotFoundException::new);
	}
}
