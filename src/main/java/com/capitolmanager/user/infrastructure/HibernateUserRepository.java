
package com.capitolmanager.user.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.user.domain.User;


@Service
public class HibernateUserRepository extends AbstractHibernateRepository<User> implements Repository<User> {

	@Autowired
	HibernateUserRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}
