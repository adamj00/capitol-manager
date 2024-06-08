
package com.capitolmanager.availability.application;

public class AvailabilityListDto {

	private Long id;
	private String name;
	private long availabilityRate;
	private String availabilityFulfilment;
	private boolean availabilityActive;

	public AvailabilityListDto(Long id, String name, long availabilityRate, String availabilityFulfilment, boolean availabilityActive) {

		this.id = id;
		this.name = name;
		this.availabilityRate = availabilityRate;
		this.availabilityFulfilment = availabilityFulfilment;
		this.availabilityActive = availabilityActive;
	}

	public AvailabilityListDto() {

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

	public long getAvailabilityRate() {

		return availabilityRate;
	}

	public void setAvailabilityRate(long availabilityRate) {

		this.availabilityRate = availabilityRate;
	}

	public String getAvailabilityFulfilment() {

		return availabilityFulfilment;
	}

	public void setAvailabilityFulfilment(String availabilityFulfilment) {

		this.availabilityFulfilment = availabilityFulfilment;
	}

	public boolean isAvailabilityActive() {

		return availabilityActive;
	}

	public void setAvailabilityActive(boolean availabilityActive) {

		this.availabilityActive = availabilityActive;
	}
}
