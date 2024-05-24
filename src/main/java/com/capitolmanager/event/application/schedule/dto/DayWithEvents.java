/*
 * Created on 22-05-2024 20:17 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.application.schedule.dto;

import java.time.LocalDate;
import java.util.List;


public class DayWithEvents {

	private LocalDate date;
	private List<EventScheduleViewDto> events;

	public DayWithEvents(LocalDate day, List<EventScheduleViewDto> events) {

		this.date = day;
		this.events = events;
	}

	public DayWithEvents() {

	}

	public LocalDate getDate() {

		return date;
	}

	public void setDate(LocalDate date) {

		this.date = date;
	}

	public List<EventScheduleViewDto> getEvents() {

		return events;
	}

	public void setEvents(List<EventScheduleViewDto> events) {

		this.events = events;
	}
}
