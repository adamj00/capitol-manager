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

package com.capitolmanager.schedule.application;

public class EventScheduleDto {

	private Long id;
	private Long scheduleId;
	private String title;
	private int assignedEmployeesCount;
	private int requiredEmployeesCount;
	private String eventStartTime;

	public EventScheduleDto(Long id, Long scheduleId, String title, int assignedEmployeesCount, int requiredEmployeesCount, String eventStartTime) {

		this.id = id;
		this.scheduleId = scheduleId;
		this.title = title;
		this.assignedEmployeesCount = assignedEmployeesCount;
		this.requiredEmployeesCount = requiredEmployeesCount;
		this.eventStartTime = eventStartTime;
	}

	public EventScheduleDto() {

	}

	public Long getId() {

		return id;
	}



	public void setId(Long id) {

		this.id = id;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public int getAssignedEmployeesCount() {

		return assignedEmployeesCount;
	}

	public void setAssignedEmployeesCount(int assignedEmployeesCount) {

		this.assignedEmployeesCount = assignedEmployeesCount;
	}

	public String getEventStartTime() {

		return eventStartTime;
	}

	public void setEventStartTime(String eventStartTime) {

		this.eventStartTime = eventStartTime;
	}

	public int getRequiredEmployeesCount() {

		return requiredEmployeesCount;
	}

	public void setRequiredEmployeesCount(int requiredEmployeesCount) {

		this.requiredEmployeesCount = requiredEmployeesCount;
	}

	public Long getScheduleId() {

		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {

		this.scheduleId = scheduleId;
	}
}
