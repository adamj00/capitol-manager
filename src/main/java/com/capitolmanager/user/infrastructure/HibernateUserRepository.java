/*
 * Created on 24-03-2024 20:26 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.user.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.user.domain.User;


public class HibernateUserRepository extends AbstractHibernateRepository<User> implements Repository<User> {

	@Autowired
	HibernateUserRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}
