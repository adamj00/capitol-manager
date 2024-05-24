/*
 * Created on 23-04-2024 21:45 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.interfaces.manager;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;


public class EventForm {

	private Long id;
	private Long show;
	private Long eventGroupId;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime eventStartTime;

	public EventForm () {}

	public EventForm(Long id, Long show, Long eventGroupId, LocalDateTime eventStartTime) {

		this.id = id;
		this.show = show;
		this.eventGroupId = eventGroupId;
		this.eventStartTime = eventStartTime;
	}

	public Long getShow() {

		return show;
	}

	public void setShow(Long show) {

		this.show = show;
	}

	public LocalDateTime getEventStartTime() {

		return eventStartTime;
	}

	public void setEventStartTime(LocalDateTime eventStartTime) {

		this.eventStartTime = eventStartTime;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Long getEventGroupId() {

		return eventGroupId;
	}

	public void setEventGroupId(Long eventGroupId) {

		this.eventGroupId = eventGroupId;
	}
}
