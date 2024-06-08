

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
