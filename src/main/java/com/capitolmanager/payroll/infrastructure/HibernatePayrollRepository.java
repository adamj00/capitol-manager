
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
