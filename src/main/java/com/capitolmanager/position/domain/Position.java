/*
 * Created on 26-03-2024 20:43 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.position.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.util.Assert;

import com.capitolmanager.hibernate.AbstractEntity;
import com.capitolmanager.stage.domain.Stage;


@Entity(name = "positions")
public class Position extends AbstractEntity {

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PositionType positionType;

	@Column(nullable = false)
	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stage_id")
	private Stage stage;

	public Position(String name, PositionType positionType, int quantity, Stage stage) {

		this.name = name;
		this.positionType = positionType;
		this.quantity = quantity;
		this.stage = stage;
	}

	public void update(String name, PositionType positionType, int quantity, Stage stage) {

		this.name = name;
		this.positionType = positionType;
		this.quantity = quantity;
		this.stage = stage;
	}

	public Position() {

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

	public Stage getStage() {

		return stage;
	}

	public void setStage(Stage stage) {

		this.stage = stage;
	}
}
