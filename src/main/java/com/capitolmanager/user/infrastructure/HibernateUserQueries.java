

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
