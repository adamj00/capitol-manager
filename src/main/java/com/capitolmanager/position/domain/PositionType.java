
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
