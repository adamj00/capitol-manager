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

	private Long id;
	private Long scheduleId;
	private String title;
	private List<PositionDto> positions;
	private String eventStartTime;

	public PositionAssignmentEventDto(Long id, Long scheduleId, String title, List<PositionDto> positions, String eventStartTime) {

		this.id = id;
		this.scheduleId = scheduleId;
		this.title = title;
		this.positions = positions;
		this.eventStartTime = eventStartTime;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Long getScheduleId() {

		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {

		this.scheduleId = scheduleId;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public List<PositionDto> getPositions() {

		return positions;
	}

	public void setPositions(List<PositionDto> positions) {

		this.positions = positions;
	}

	public String getEventStartTime() {

		return eventStartTime;
	}

	public void setEventStartTime(String eventStartTime) {

		this.eventStartTime = eventStartTime;
	}
}
