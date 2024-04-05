/*
 * Created on 26-03-2024 23:39 by ajarzabe
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.position.domain.Position;


@Service
public class HibernatePositionRepository extends AbstractHibernateRepository<Position> implements Repository<Position> {

	@Autowired
	HibernatePositionRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}
