
package com.capitolmanager.event.application.schedule.dto;

import java.util.List;


public class EmployeeScheduleDto {

	private Long id;
	private String name;
	private List<Long> assignedEvents;
	private List<Long> availableForEvents;

	public EmployeeScheduleDto(Long id, String name, List<Long> assignedEvents, List<Long> availableForEvents) {

		this.id = id;
		this.name = name;
		this.assignedEvents = assignedEvents;
		this.availableForEvents = availableForEvents;
	}

	public EmployeeScheduleDto() {

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

	public List<Long> getAssignedEvents() {

		return assignedEvents;
	}

	public void setAssignedEvents(List<Long> assignedEvents) {

		this.assignedEvents = assignedEvents;
	}

	public List<Long> getAvailableForEvents() {

		return availableForEvents;
	}

	public void setAvailableForEvents(List<Long> availableForEvents) {

		this.availableForEvents = availableForEvents;
	}
}
