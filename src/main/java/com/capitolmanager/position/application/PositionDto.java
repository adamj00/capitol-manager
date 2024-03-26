/*
 * Created on 26-03-2024 21:26 by ajarzabe
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

import org.springframework.util.Assert;

import com.capitolmanager.position.domain.PositionType;


public class PositionDto {

	private Long id;
	private String name;
	private PositionType positionType;

	public PositionDto(Long id, String name, PositionType positionType) {

		Assert.notNull(id, "id must not be null");
		Assert.notNull(name, "name must not be null");
		Assert.notNull(positionType, "positionType must not be null");

		this.id = id;
		this.name = name;
		this.positionType = positionType;
	}

	public PositionDto() {

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
}
