/*
 * Created on 12-04-2024 19:19 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.user.domain;

public enum UserRole {
	MANAGER("Koordynator", 1), EMPLOYEE("Bileter", 2);

	private final String label;
	private final int orderNumber;

	UserRole(String label, int orderNumber) {

		this.label = label;
		this.orderNumber = orderNumber;
	}

	public String getLabel() {

		return label;
	}

	public int getOrderNumber() {

		return orderNumber;
	}
}

