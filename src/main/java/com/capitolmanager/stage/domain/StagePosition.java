/*
 * Created on 28-03-2024 16:14 by ajarzabe
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.capitolmanager.hibernate.AbstractEntity;
import com.capitolmanager.position.domain.Position;


@Entity(name="stage_positions")
public class StagePosition extends AbstractEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "stage_id")
	private Stage stage;

	@ManyToOne(optional = false)
	@JoinColumn(name = "position_id")
	private Position position;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	public StagePosition(Stage stage, Position position, Integer quantity) {

		this.stage = stage;
		this.position = position;
		this.quantity = quantity;
	}

	public StagePosition() {

	}

	public Stage getStage() {

		return stage;
	}

	public void setStage(Stage stage) {

		this.stage = stage;
	}

	public Position getPosition() {

		return position;
	}

	public void setPosition(Position position) {

		this.position = position;
	}

	public Integer getQuantity() {

		return quantity;
	}

	public void setQuantity(Integer quantity) {

		this.quantity = quantity;
	}
}
