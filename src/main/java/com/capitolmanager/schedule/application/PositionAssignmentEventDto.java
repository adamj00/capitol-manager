/*
 * Created on 16-05-2024 19:51 by ajarzabe
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

import java.util.List;


public class PositionAssignmentEventDto {

	private final Long id;
	private final String title;
	private final List<PositionDto> positions;
	private final String eventStartTime;

	public PositionAssignmentEventDto(Long id, String title, List<PositionDto> positions, String eventStartTime) {

		this.id = id;
		this.title = title;
		this.positions = positions;
		this.eventStartTime = eventStartTime;
	}

	public Long getId() {

		return id;
	}

	public String getTitle() {

		return title;
	}

	public List<PositionDto> getPositions() {

		return positions;
	}

	public String getEventStartTime() {

		return eventStartTime;
	}
}
