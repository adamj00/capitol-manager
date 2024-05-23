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

	private final long id;
	private final ShowEventDto show;
	private final LocalDateTime eventStartTime;

	public EventDto(long id, ShowEventDto show, LocalDateTime eventStartTime) {

		this.id = id;
		this.show = show;
		this.eventStartTime = eventStartTime;
	}

	public long getId() {

		return id;
	}

	public ShowEventDto getShow() {

		return show;
	}

	public LocalDateTime getEventStartTime() {

		return eventStartTime;
	}
}
