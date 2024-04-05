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

import org.springframework.util.Assert;

import com.capitolmanager.hibernate.AbstractEntity;


@Entity(name = "positions")
public class Position extends AbstractEntity {

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PositionType positionType;

	public Position(String name, PositionType positionType) {

		Assert.notNull(name, "name must not be null");
		Assert.notNull(positionType, "positionType must not be null");

		this.name = name;
		this.positionType = positionType;
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
}
