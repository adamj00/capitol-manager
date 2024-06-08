

package com.capitolmanager.availability.application;

import java.time.LocalDate;
import java.util.List;


public class DayWithAvailabilities {

	private LocalDate date;
	private List<AvailabilityDto> availabilities;

	public DayWithAvailabilities(LocalDate date, List<AvailabilityDto> availabilities) {

		this.date = date;
		this.availabilities = availabilities;
	}

	public DayWithAvailabilities() {

	}

	public LocalDate getDate() {

		return date;
	}

	public void setDate(LocalDate date) {

		this.date = date;
	}

	public List<AvailabilityDto> getAvailabilities() {

		return availabilities;
	}

	public void setAvailabilities(List<AvailabilityDto> availabilities) {

		this.availabilities = availabilities;
	}
}
