

package com.capitolmanager.event.application.schedule.dto;

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
