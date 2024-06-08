
package com.capitolmanager.payroll.infrastructure;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.capitolmanager.hibernate.AbstractHibernateQueries;
import com.capitolmanager.payroll.application.PayrollQueries;
import com.capitolmanager.payroll.domain.Payroll;


@Service
public class HibernatePayrollQueries extends AbstractHibernateQueries<Payroll> implements PayrollQueries {

	protected HibernatePayrollQueries(SessionFactory sessionFactory) {

		super(sessionFactory, Payroll.class);
	}
}
