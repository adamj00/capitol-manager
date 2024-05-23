/*
 * Created on 14-05-2024 19:13 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.show.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.event.domain.EventPositionAssignment;


@Service
public class HibernateEventPositionAssignmentRepository extends AbstractHibernateRepository<EventPositionAssignment> implements Repository<EventPositionAssignment> {

	protected HibernateEventPositionAssignmentRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}
