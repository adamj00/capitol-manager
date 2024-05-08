/*
 * Created on 13-04-2024 13:18 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.capitolmanager.hibernate.AbstractEntity;
import com.capitolmanager.show.domain.Show;


@Entity
@Table(name = "events")
public class Event extends AbstractEntity {

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "show_id", nullable = false)
	private Show show;

	@Column(name = "event_start_time", nullable = false)
	private LocalDateTime eventStartTime;

	@Column(name = "shift_start_time", nullable = false)
	private LocalDateTime shiftStartTime;

	@Column(name = "notes")
	private String notes;

	public Event() {

	}

	public Event(Show show, LocalDateTime eventStartTime, LocalDateTime shiftStartTime, String notes) {

		this.show = show;
		this.eventStartTime = eventStartTime;
		this.shiftStartTime = shiftStartTime;
		this.notes = notes;
	}

	public void update(Show show, LocalDateTime eventStartTime, LocalDateTime shiftStartTime, String notes) {

		this.show = show;
		this.eventStartTime = eventStartTime;
		this.shiftStartTime = shiftStartTime;
		this.notes = notes;
	}

	public Show getShow() {

		return show;
	}

	public void setShow(Show show) {

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
}