/*
 * Created on 14-05-2024 11:46 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.schedule.infrastructure;

import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.hibernate.AbstractHibernateQueries;
import com.capitolmanager.schedule.application.ScheduleQueries;
import com.capitolmanager.schedule.domain.Schedule;


@Service
public class HibernateScheduleQueries extends AbstractHibernateQueries<Schedule> implements ScheduleQueries {

	protected HibernateScheduleQueries(SessionFactory sessionFactory) {

		super(sessionFactory, Schedule.class);
	}

	@Override
	public Optional<Schedule> findByEventGroup(Long eventGroupId) {
		//		try (Session session = sessionFactory.openSession()) {
		//			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		//			CriteriaQuery<Schedule> criteriaQuery = criteriaBuilder.createQuery(Schedule.class);
		//			Root<Schedule> root = criteriaQuery.from(Schedule.class);
		//
		//			// Odwołanie do identyfikatora encji EventGroup
		//			criteriaQuery.select(root).where(criteriaBuilder.equal(root.join("eventGroup").get("id"), eventGroupId));
		//
		//			Query<Schedule> query = session.createQuery(criteriaQuery);
		//
		//			return query.uniqueResultOptional();
		//		}

		return getAll().stream()
			.filter(schedule -> schedule.getEventGroup().getId().equals(eventGroupId))
			.findAny();
	}
}
