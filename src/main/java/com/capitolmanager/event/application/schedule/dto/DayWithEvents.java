

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
