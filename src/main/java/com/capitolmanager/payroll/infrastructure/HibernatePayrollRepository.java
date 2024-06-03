/*
 * Created on 01-06-2024 21:18 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.payroll.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.hibernate.AbstractHibernateRepository;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.payroll.domain.Payroll;


@Service
public class HibernatePayrollRepository extends AbstractHibernateRepository<Payroll> implements Repository<Payroll> {

	protected HibernatePayrollRepository(SessionFactory sessionFactory) {

		super(sessionFactory);
	}
}