/*
 * Created on 30-03-2024 19:53 by ajarzabe
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

import com.capitolmanager.hibernate.AbstractHibernateQueries;
import com.capitolmanager.show.application.ShowQueries;
import com.capitolmanager.show.domain.Show;


@Service
public class HibernateShowQueries extends AbstractHibernateQueries<Show> implements ShowQueries {

	protected HibernateShowQueries(SessionFactory sessionFactory) {

		super(sessionFactory, Show.class);
	}
}
