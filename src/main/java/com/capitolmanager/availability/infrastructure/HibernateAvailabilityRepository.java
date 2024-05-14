/*
 * Created on 09-05-2024 22:39 by ajarzabe
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

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.availability.domain.Availability;
import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;


@Service
public class HibernateAvailabilityRepository extends AbstractHibernateRepository<Availability> implements Repository<Availability> {

	protected HibernateAvailabilityRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}
