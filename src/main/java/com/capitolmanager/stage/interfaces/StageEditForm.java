/*
 * Created on 28-03-2024 13:28 by ajarzabe
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

import java.util.ArrayList;
import java.util.List;

import com.capitolmanager.position.application.PositionDto;


public class StageEditForm {

	public static final String F_NAME = "name";
	public static final String F_REQUIRED_POSITIONS = "requiredPositions";

	private Long id;
	private String name;
	private int numberOfSeats;
	private String address;
	private List<PositionDto> requiredPositions = new ArrayList<>();

	public StageEditForm(Long id, String name, int numberOfSeats, String address, List<PositionDto> requiredPositions) {

		this.id = id;
		this.name = name;
		this.numberOfSeats = numberOfSeats;
		this.address = address;
		this.requiredPositions = requiredPositions;
	}

	public StageEditForm() {

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

	public int getNumberOfSeats() {

		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {

		this.numberOfSeats = numberOfSeats;
	}

	public String getAddress() {

		return address;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	public List<PositionDto> getRequiredPositions() {

		return requiredPositions;
	}

	public void setRequiredPositions(List<PositionDto> requiredPositions) {

		this.requiredPositions = requiredPositions;
	}
}
