/*
 * Created on 01-06-2024 21:15 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.payroll.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.payroll.domain.Payroll;


public interface PayrollQueries {

	List<Payroll> getAll();
	Optional<Payroll> findById(Long id);
	Payroll get(Long id);
}
