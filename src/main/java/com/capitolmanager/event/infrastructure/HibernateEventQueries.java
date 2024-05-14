/*
 * Created on 23-04-2024 21:23 by ajarzabe
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

import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.hibernate.AbstractHibernateQueries;


@Service
public class HibernateEventQueries extends AbstractHibernateQueries<Event> implements EventQueries {

	protected HibernateEventQueries(SessionFactory sessionFactory) {

		super(sessionFactory, Event.class);
	}
}
