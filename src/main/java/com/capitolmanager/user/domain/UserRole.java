
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

