/*
 * Created on 01-06-2024 21:01 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.payroll.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import com.capitolmanager.user.domain.User;


public class Payroll {

	private User user;
	private LocalDate date;
	private LocalTime start;
	private LocalTime end;
	private double hours;


}
