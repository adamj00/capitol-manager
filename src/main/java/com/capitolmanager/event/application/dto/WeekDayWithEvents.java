/*
 * Created on 01-05-2024 14:35 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.application.dto;

import java.time.LocalDate;
import java.util.List;


public class WeekDayWithEvents {

	private String dayOfWeek;
	private LocalDate date;
	private List<EventDto> events;

	public WeekDayWithEvents(String dayOfWeek, LocalDate date, List<EventDto> events) {

		this.dayOfWeek = dayOfWeek;
		this.date = date;
		this.events = events;
	}

	public WeekDayWithEvents() {

	}

	public String getDayOfWeek() {

		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {

		this.dayOfWeek = dayOfWeek;
	}

	public List<EventDto> getEvents() {

		return events;
	}

	public void setEvents(List<EventDto> events) {

		this.events = events;
	}

	public LocalDate getDate() {

		return date;
	}

	public void setDate(LocalDate date) {

		this.date = date;
	}
}
