/*
 * Created on 06-06-2024 22:03 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.position.interfaces;

import com.capitolmanager.position.domain.PositionType;


public class PositionEditForm {

	private Long id;
	private String name;
	private PositionType positionType;
	private int quantity;
	private Long stageId;

	public PositionEditForm(Long id, String name, PositionType positionType, int quantity, Long stageId) {

		this.id = id;
		this.name = name;
		this.positionType = positionType;
		this.quantity = quantity;
		this.stageId = stageId;
	}

	public PositionEditForm() {

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

	public PositionType getPositionType() {

		return positionType;
	}

	public void setPositionType(PositionType positionType) {

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
