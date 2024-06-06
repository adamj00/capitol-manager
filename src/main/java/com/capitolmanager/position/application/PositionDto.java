/*
 * Created on 26-03-2024 21:26 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.position.application;

import com.capitolmanager.stage.interfaces.PositionTypeDto;


public class PositionDto {

	private Long id;
	private String name;
	private String positionType;
	private int quantity;
	private Long stageId;

	public PositionDto(Long id, String name, String positionType, int quantity, Long stageId) {

		this.id = id;
		this.name = name;
		this.positionType = positionType;
		this.quantity = quantity;
		this.stageId = stageId;
	}

	public PositionDto() {

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

	public String getPositionType() {

		return positionType;
	}

	public void setPositionType(String positionType) {

		this.positionType = positionType;
	}

	public int getQuantity() {

		return quantity;
	}

	public void setQuantity(int quantity) {

		this.quantity = quantity;
	}

	public Long getStageId() {

		return stageId;
	}

	public void setStageId(Long stageId) {

		this.stageId = stageId;
	}
}
