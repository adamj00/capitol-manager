

package com.capitolmanager.position.application;

import com.capitolmanager.position.domain.PositionType;


public class PositionListDto {

	private final Long id;
	private final String name;
	private final PositionType positionType;
	private final int quantity;
	private final Long stageId;

	public PositionListDto(Long id, String name, PositionType positionType, int quantity, Long stageId) {

		this.id = id;
		this.name = name;
		this.positionType = positionType;
		this.quantity = quantity;
		this.stageId = stageId;
	}

	public Long getId() {

		return id;
	}

	public String getName() {

		return name;
	}

	public PositionType getPositionType() {

		return positionType;
	}

	public int getQuantity() {

		return quantity;
	}

	public Long getStageId() {

		return stageId;
	}
}
