
package com.capitolmanager.event.application.schedule.dto;

import com.capitolmanager.position.domain.PositionType;


public class PositionDto {

	Long id;
	String name;
	PositionType type;
	int quantity;

	public PositionDto(Long id, String name, PositionType type, int quantity) {

		this.id = id;
		this.name = name;
		this.type = type;
		this.quantity = quantity;
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

	public PositionType getType() {

		return type;
	}

	public void setType(PositionType type) {

		this.type = type;
	}

	public int getQuantity() {

		return quantity;
	}

	public void setQuantity(int quantity) {

		this.quantity = quantity;
	}
}
