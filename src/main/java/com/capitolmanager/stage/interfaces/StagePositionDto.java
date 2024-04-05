/*
 * Created on 28-03-2024 16:27 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.stage.interfaces;

public class StagePositionDto {

	private Long positionId;
	private String positionName;
	private int quantity;

	public StagePositionDto(Long positionId, String positionName, int quantity) {

		this.positionId = positionId;
		this.positionName = positionName;
		this.quantity = quantity;
	}

	public StagePositionDto() {

	}

	public Long getPositionId() {

		return positionId;
	}

	public void setPositionId(Long positionId) {

		this.positionId = positionId;
	}

	public String getPositionName() {

		return positionName;
	}

	public void setPositionName(String positionName) {

		this.positionName = positionName;
	}

	public int getQuantity() {

		return quantity;
	}

	public void setQuantity(int quantity) {

		this.quantity = quantity;
	}
}
