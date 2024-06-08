

package com.capitolmanager.stage.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.stage.domain.Stage;


@Service
public class HibernateStageRepository extends AbstractHibernateRepository<Stage> implements Repository<Stage> {

	@Autowired
	HibernateStageRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}
