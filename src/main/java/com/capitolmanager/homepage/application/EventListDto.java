

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
