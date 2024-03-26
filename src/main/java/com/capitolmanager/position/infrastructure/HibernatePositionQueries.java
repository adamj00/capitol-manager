/*
 * Created on 26-03-2024 21:32 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.position.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.AbstractHibernateQueries;
import com.capitolmanager.position.application.PositionQueries;
import com.capitolmanager.position.domain.Position;


@Service
public class HibernatePositionQueries extends AbstractHibernateQueries<Position> implements PositionQueries {

	protected HibernatePositionQueries(SessionFactory sessionFactory) {

		super(sessionFactory, Position.class);

		Assert.notNull(sessionFactory, "sessionFactory must not be null");
	}
}
