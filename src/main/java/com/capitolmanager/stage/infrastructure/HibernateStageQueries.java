

package com.capitolmanager.stage.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.AbstractHibernateQueries;
import com.capitolmanager.stage.application.StageQueries;
import com.capitolmanager.stage.domain.Stage;


@Service
public class HibernateStageQueries extends AbstractHibernateQueries<Stage> implements StageQueries {

	protected HibernateStageQueries(SessionFactory sessionFactory) {

		super(sessionFactory, Stage.class);

		Assert.notNull(sessionFactory, "sessionFactory must not be null");
	}
}
