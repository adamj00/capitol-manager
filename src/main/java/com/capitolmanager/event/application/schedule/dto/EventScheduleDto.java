/*
 * Created on 14-05-2024 11:42 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.application.schedule.dto;

public class EventScheduleDto {

	private final Long id;
	private final String title;
	private final int assignedEmployeesCount;
	private final int requiredEmployeesCount;
	private final String eventStartTime;

	public EventScheduleDto(Long id, String title, int assignedEmployeesCount, int requiredEmployeesCount, String eventStartTime) {

		this.id = id;
		this.title = title;
		this.assignedEmployeesCount = assignedEmployeesCount;
		this.requiredEmployeesCount = requiredEmployeesCount;
		this.eventStartTime = eventStartTime;
	}

	public Long getId() {

		return id;
	}

	public String getTitle() {

		return title;
	}

	public int getAssignedEmployeesCount() {

		return assignedEmployeesCount;
	}

	public int getRequiredEmployeesCount() {

		return requiredEmployeesCount;
	}

	public String getEventStartTime() {

		return eventStartTime;
	}
}
