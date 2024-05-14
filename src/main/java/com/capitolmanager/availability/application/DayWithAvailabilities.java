/*
 * Created on 09-05-2024 20:25 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

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
