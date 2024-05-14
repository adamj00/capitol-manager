/*
 * Created on 09-05-2024 21:57 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.availability.application;

import java.time.LocalDateTime;


public class AvailabilityDto {

	private Long id;
	private Long eventId;
	private String showTitle;
	private LocalDateTime showStartTime;
	private String showDurationString;
	private String stage;
	private Boolean available;

	public AvailabilityDto(Long id, Long eventId, String showTitle, LocalDateTime showStartTime, String showDurationString, String stage, Boolean available) {

		this.id = id;
		this.eventId = eventId;
		this.showTitle = showTitle;
		this.showStartTime = showStartTime;
		this.showDurationString = showDurationString;
		this.stage = stage;
		this.available = available;
	}

	public AvailabilityDto() {

	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Long getEventId() {

		return eventId;
	}

	public void setEventId(Long eventId) {

		this.eventId = eventId;
	}

	public String getShowTitle() {

		return showTitle;
	}

	public void setShowTitle(String showTitle) {

		this.showTitle = showTitle;
	}

	public LocalDateTime getShowStartTime() {

		return showStartTime;
	}

	public void setShowStartTime(LocalDateTime showStartTime) {

		this.showStartTime = showStartTime;
	}

	public String getShowDurationString() {

		return showDurationString;
	}

	public void setShowDurationString(String showDurationString) {

		this.showDurationString = showDurationString;
	}

	public Boolean getAvailable() {

		return available;
	}

	public void setAvailable(Boolean available) {

		this.available = available;
	}

	public String getStage() {

		return stage;
	}

	public void setStage(String stage) {

		this.stage = stage;
	}
}
