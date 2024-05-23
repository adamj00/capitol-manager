/*
 * Created on 16-05-2024 19:55 by ajarzabe
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


public class PositionAssignmentEmployeeDto {

	private Long id;
	private String name;
	private List<AssignmentDto> assignedPositions;
	private List<Long> assignedEvents;

	public PositionAssignmentEmployeeDto(Long id, String name, List<AssignmentDto> assignedPositions, List<Long> assignedEvents) {

		this.id = id;
		this.name = name;
		this.assignedPositions = assignedPositions;
		this.assignedEvents = assignedEvents;
	}

	public PositionAssignmentEmployeeDto() {

	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public List<AssignmentDto> getAssignedPositions() {

		return assignedPositions;
	}

	public void setAssignedPositions(List<AssignmentDto> assignedPositions) {

		this.assignedPositions = assignedPositions;
	}

	public List<Long> getAssignedEvents() {

		return assignedEvents;
	}

	public void setAssignedEvents(List<Long> assignedEvents) {

		this.assignedEvents = assignedEvents;
	}
}
