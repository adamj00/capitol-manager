/*
 * Created on 23-04-2024 21:18 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.application;

import java.time.LocalDateTime;

import com.capitolmanager.show.application.ShowEventDto;


public class EventDto {

	private long id;
	private ShowEventDto show;
	private LocalDateTime eventStartTime;
	private LocalDateTime shiftStartTime;
	private String notes;

	public EventDto () {}

	public EventDto(long id, ShowEventDto show, LocalDateTime eventStartTime, LocalDateTime shiftStartTime, String notes) {

		this.id = id;
		this.show = show;
		this.eventStartTime = eventStartTime;
		this.shiftStartTime = shiftStartTime;
		this.notes = notes;
	}

	public ShowEventDto getShow() {

		return show;
	}

	public void setShow(ShowEventDto show) {

		this.show = show;
	}

	public LocalDateTime getEventStartTime() {

		return eventStartTime;
	}

	public void setEventStartTime(LocalDateTime eventStartTime) {

		this.eventStartTime = eventStartTime;
	}

	public LocalDateTime getShiftStartTime() {

		return shiftStartTime;
	}

	public void setShiftStartTime(LocalDateTime shiftStartTime) {

		this.shiftStartTime = shiftStartTime;
	}

	public String getNotes() {

		return notes;
	}

	public void setNotes(String notes) {

		this.notes = notes;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}
}
