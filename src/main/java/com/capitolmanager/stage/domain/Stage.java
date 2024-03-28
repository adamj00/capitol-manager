/*
 * Created on 28-03-2024 12:34 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.stage.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;

import com.capitolmanager.position.domain.Position;


@Entity(name = "stages")
public class Stage {

	@Column
	private String name;

	@Column
	private int numberOfSeats;

	@Column
	private String address;

	@ElementCollection
	@CollectionTable(name = "required_positions", joinColumns = @JoinColumn(name = "stage_id"))
	@MapKeyJoinColumn(name = "position_id")
	@Column(name = "quantity")
	private Map<Position, Integer> requiredPositions = new HashMap<>();

	public Stage(String name, int numberOfSeats, String address, Map<Position, Integer> requiredPositions) {

		this.name = name;
		this.numberOfSeats = numberOfSeats;
		this.address = address;
		this.requiredPositions = requiredPositions;
	}

	public Stage() {

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

	public Map<Position, Integer> getRequiredPositions() {

		return requiredPositions;
	}

	public void setRequiredPositions(Map<Position, Integer> requiredPositions) {

		this.requiredPositions = requiredPositions;
	}
}
