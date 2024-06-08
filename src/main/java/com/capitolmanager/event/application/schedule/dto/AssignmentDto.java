
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
