

package com.capitolmanager.stage.interfaces;

public class PositionTypeDto {

	private String value;
	private String label;

	public PositionTypeDto(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public PositionTypeDto() {

	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
