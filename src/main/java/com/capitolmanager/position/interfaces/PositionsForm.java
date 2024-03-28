/*
 * Created on 27-03-2024 20:26 by ajarzabe
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

import java.util.List;

import org.springframework.util.Assert;

import com.capitolmanager.position.application.PositionDto;


public class PositionsForm {

	private List<PositionDto> positions;

	public PositionsForm(List<PositionDto> positions) {

		Assert.notNull(positions, "positions must not be null");

		this.positions = positions;
	}

	public PositionsForm() {

	}

	public List<PositionDto> getPositions() {

		return positions;
	}

	public void setPositions(List<PositionDto> positions) {

		this.positions = positions;
	}
}
