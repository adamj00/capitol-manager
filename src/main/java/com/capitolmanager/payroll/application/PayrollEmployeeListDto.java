/*
 * Created on 01-06-2024 21:39 by ajarzabe
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

public class PayrollEmployeeListDto {

	private final Long id;
	private final String date;
	private final String start;
	private final String end;
	private final double hours;

	public PayrollEmployeeListDto(Long id, String date, String start, String end, double hours) {

		this.id = id;
		this.date = date;
		this.start = start;
		this.end = end;
		this.hours = hours;
	}

	public Long getId() {

		return id;
	}

	public String getDate() {

		return date;
	}

	public String getStart() {

		return start;
	}

	public String getEnd() {

		return end;
	}

	public double getHours() {

		return hours;
	}
}
