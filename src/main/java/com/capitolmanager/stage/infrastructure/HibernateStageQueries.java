/*
 * Created on 28-03-2024 13:03 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

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
