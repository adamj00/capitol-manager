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

	TICKETS("Bilety", 2),
	AUDITORIUM("Widownia", 1),
	CLOAKROOM("Szatnia", 2),
	OTHER("Inne", 3);

	private String label;
	private int priority;

	PositionType(String label, int priority) {

		this.label = label;
		this.priority = priority;
	}

	public String getLabel() {
		return label;
	}
	public int getPriority() {
		return priority;
	}
}
