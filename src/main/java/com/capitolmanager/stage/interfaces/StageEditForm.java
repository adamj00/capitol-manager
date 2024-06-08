

package com.capitolmanager.stage.interfaces;

public class StageEditForm {

	public static final String F_NAME = "name";

	private Long id;
	private String name;
	private int numberOfSeats;
	private String address;

	public StageEditForm(Long id, String name, int numberOfSeats, String address) {

		this.id = id;
		this.name = name;
		this.numberOfSeats = numberOfSeats;
		this.address = address;
	}

	public StageEditForm() {

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

	public int getNumberOfSeats() {

		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {

		this.numberOfSeats = numberOfSeats;
	}

	public String getAddress() {

		return address;
	}

	public void setAddress(String address) {

		this.address = address;
	}

}
