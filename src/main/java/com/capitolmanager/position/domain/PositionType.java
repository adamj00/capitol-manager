/*
 * Created on 26-03-2024 20:45 by ajarzabe
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

public enum PositionType {

	TICKETS("Bilety"),
	AUDITORIUM("Widownia"),
	CLOAKROOM("Szatnia"),
	OTHER("Inne");

	private String label;

	PositionType(String label) {

		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
