/*
 * Created on 06-06-2024 21:18 by ajarzabe
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
