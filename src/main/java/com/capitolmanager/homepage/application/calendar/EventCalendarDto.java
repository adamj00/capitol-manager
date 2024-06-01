/*
 * Created on 24-05-2024 21:29 by ajarzabe
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

import java.time.LocalDateTime;


public class EventCalendarDto {

	private final Long id;
	private final String title;
	private final String stage;
	private final LocalDateTime eventStartTime;
	private final String duration;

	public EventCalendarDto(Long id, String title, String stage, LocalDateTime eventStartTime, String duration) {

		this.id = id;
		this.title = title;
		this.stage = stage;
		this.eventStartTime = eventStartTime;
		this.duration = duration;
	}

	public Long getId() {

		return id;
	}

	public String getTitle() {

		return title;
	}

	public String getStage() {

		return stage;
	}

	public LocalDateTime getEventStartTime() {

		return eventStartTime;
	}

	public String getDuration() {

		return duration;
	}
}
