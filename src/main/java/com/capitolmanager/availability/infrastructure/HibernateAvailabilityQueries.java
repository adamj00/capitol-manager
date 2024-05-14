/*
 * Created on 09-05-2024 21:47 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.availability.infrastructure;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import com.capitolmanager.availability.application.AvailabilityQueries;
import com.capitolmanager.availability.domain.Availability;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.hibernate.AbstractHibernateQueries;


@Service
public class HibernateAvailabilityQueries extends AbstractHibernateQueries<Availability> implements AvailabilityQueries {

	protected HibernateAvailabilityQueries(SessionFactory sessionFactory) {

		super(sessionFactory, Availability.class);
	}

	@Override
	public List<Availability> getByUserIdAndEventGroup(Long userId, Long eventGroupId) {
		try (Session session = sessionFactory.openSession()) {
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Availability> criteriaQuery = criteriaBuilder.createQuery(Availability.class);
			Root<Availability> root = criteriaQuery.from(Availability.class);

			// Tworzenie połączenia (join) z encją Event
			Join<Availability, Event> eventJoin = root.join("event");

			// Dodanie warunków where do zapytania
			Predicate userPredicate = criteriaBuilder.equal(root.get("user"), userId);
			Predicate eventGroupPredicate = criteriaBuilder.equal(eventJoin.get("eventGroup").get("id"), eventGroupId);
			criteriaQuery.select(root).where(criteriaBuilder.and(userPredicate, eventGroupPredicate));

			Query<Availability> query = session.createQuery(criteriaQuery);

			return query.getResultList();
		}
	}
}
