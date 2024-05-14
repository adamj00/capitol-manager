/*
 * Created on 11-05-2024 21:59 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.event.application.EventGroupQueries;
import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.hibernate.AbstractHibernateQueries;


@Service
public class HibernateEventGroupQueries extends AbstractHibernateQueries<EventGroup> implements EventGroupQueries {

	protected HibernateEventGroupQueries(SessionFactory sessionFactory) {

		super(sessionFactory, EventGroup.class);
	}
}
