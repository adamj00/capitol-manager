
package com.capitolmanager.event.application.dto;

public class EventGroupListDto {

	private final Long id;
	private final String name;
	private final boolean availabilityActive;
	private final boolean scheduleActive;

	public EventGroupListDto(Long id, String name, boolean availabilityActive, boolean scheduleActive) {

		this.id = id;
		this.name = name;
		this.availabilityActive = availabilityActive;
		this.scheduleActive = scheduleActive;
	}

	public Long getId() {

		return id;
	}

	public String getName() {

		return name;
	}

	public boolean isAvailabilityActive() {

		return availabilityActive;
	}

	public boolean isScheduleActive() {

		return scheduleActive;
	}
}
