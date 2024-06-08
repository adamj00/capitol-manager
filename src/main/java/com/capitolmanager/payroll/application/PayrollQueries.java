

package com.capitolmanager.payroll.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.payroll.domain.Payroll;


public interface PayrollQueries {

	List<Payroll> getAll();
	Optional<Payroll> findById(Long id);
	Payroll get(Long id);
}
