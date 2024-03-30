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

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.capitolmanager.hibernate.AbstractEntity;


@Entity(name = "stages")
public class Stage extends AbstractEntity {

	@Column
	private String name;

	@Column
	private int numberOfSeats;

	@Column
	private String address;

	@OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<StagePosition> requiredPositions = new LinkedList<>();

	public Stage(String name, int numberOfSeats, String address, List<StagePosition> requiredPositions) {

		this.name = name;
		this.numberOfSeats = numberOfSeats;
		this.address = address;
		this.requiredPositions = requiredPositions;
	}

	public Stage() {

	}

	public void updateStage(String name, int numberOfSeats, String address) {

		this.name = name;
		this.numberOfSeats = numberOfSeats;
		this.address = address;
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

	public List<StagePosition> getRequiredPositions() {

		return requiredPositions;
	}

	public void setRequiredPositions(List<StagePosition> requiredPositions) {

		this.requiredPositions = requiredPositions;
	}
}
