/*
 * Created on 19-05-2024 16:34 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.homepage.application;

public class EventListDto {

	private Long id;
	private String label;

	public EventListDto(Long id, String label) {

		this.id = id;
		this.label = label;
	}

	public Long getId() {

		return id;
	}

	public String getLabel() {

		return label;
	}
}
