/*
 * Created on 24-05-2024 21:42 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.homepage.application.calendar;

import java.time.LocalDate;
import java.util.List;


public class CalendarDay {

	private LocalDate date;
	private List<EventCalendarDto> events;

	public CalendarDay(LocalDate date, List<EventCalendarDto> events) {

		this.date = date;
		this.events = events;
	}

	public CalendarDay() {

	}

	public LocalDate getDate() {

		return date;
	}

	public void setDate(LocalDate date) {

		this.date = date;
	}

	public List<EventCalendarDto> getEvents() {

		return events;
	}

	public void setEvents(List<EventCalendarDto> events) {

		this.events = events;
	}
}
