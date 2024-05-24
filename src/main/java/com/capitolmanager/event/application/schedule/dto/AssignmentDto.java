/*
 * Created on 16-05-2024 19:56 by ajarzabe
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

public class AssignmentDto {

	private Long id;
	private Long employeeId;
	private Long eventId;
	private Long positionId;

	public AssignmentDto(Long id, Long employeeId, Long eventId, Long positionId) {

		this.id = id;
		this.employeeId = employeeId;
		this.eventId = eventId;
		this.positionId = positionId;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Long getEmployeeId() {

		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {

		this.employeeId = employeeId;
	}

	public Long getEventId() {

		return eventId;
	}

	public void setEventId(Long eventId) {

		this.eventId = eventId;
	}

	public Long getPositionId() {

		return positionId;
	}

	public void setPositionId(Long positionId) {

		this.positionId = positionId;
	}


}
