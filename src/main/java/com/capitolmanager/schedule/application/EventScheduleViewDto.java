/*
 * Created on 22-05-2024 19:36 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.schedule.application;

import java.time.LocalDateTime;


public class EventScheduleViewDto {

	private final Long id;
	private final String title;
	private final String stage;
	private final String manager;
	private final LocalDateTime eventStartTime;
	private final String duration;
	private final boolean assigned;
	private final String position;

	public EventScheduleViewDto(Long id, String title, String stage, String manager, LocalDateTime eventStartTime, String duration, boolean assigned, String position) {

		this.id = id;
		this.title = title;
		this.stage = stage;
		this.manager = manager;
		this.eventStartTime = eventStartTime;
		this.duration = duration;
		this.assigned = assigned;
		this.position = position;
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

	public String getManager() {

		return manager;
	}

	public LocalDateTime getEventStartTime() {

		return eventStartTime;
	}

	public String getDuration() {

		return duration;
	}

	public boolean isAssigned() {

		return assigned;
	}

	public String getPosition() {

		return position;
	}
}
