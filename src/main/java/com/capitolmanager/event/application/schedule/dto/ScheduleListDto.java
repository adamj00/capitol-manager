

package com.capitolmanager.event.application.schedule.dto;

public class ScheduleListDto {

	private final Long id;
	private final String name;
	private final int assignedEventsQuantity;

	public ScheduleListDto(Long id, String name, int assignedEventsQuantity) {

		this.id = id;
		this.name = name;
		this.assignedEventsQuantity = assignedEventsQuantity;
	}

	public Long getId() {

		return id;
	}

	public String getName() {

		return name;
	}

	public int getAssignedEventsQuantity() {

		return assignedEventsQuantity;
	}
}
