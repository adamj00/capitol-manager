/*
 * Created on 24-03-2024 20:01 by ajarzabe
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

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.AbstractHibernateQueries;
import com.capitolmanager.user.application.UserQueries;
import com.capitolmanager.user.domain.User;


@Service
public class HibernateUserQueries extends AbstractHibernateQueries<User> implements UserQueries {

	@Autowired
	HibernateUserQueries(SessionFactory sessionFactory) {

		super(sessionFactory, User.class);

		Assert.notNull(sessionFactory, "sessionFactory must not be null");
	}

	@Override
	public Optional<User> findByEmail(String email) {

		return getAll().stream()
			.filter(user -> user.getEmail().equals((email)))
			.findFirst();
	}

	@Override
	public User get(Long id) {

		return findById(id)
			.orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
	}
}
