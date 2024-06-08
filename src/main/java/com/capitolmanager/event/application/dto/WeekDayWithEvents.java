
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
