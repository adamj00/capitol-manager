

package com.capitolmanager.stage.application;

public class StageSelectionDto {

	private Long id;
	private String name;

	public StageSelectionDto(Long id, String name) {

		this.id = id;
		this.name = name;
	}

	public StageSelectionDto() {

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
}

