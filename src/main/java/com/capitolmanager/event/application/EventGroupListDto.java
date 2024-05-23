/*
 * Created on 11-05-2024 22:58 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.application;

public class EventGroupListDto {

	private final Long id;
	private final String name;
	private final boolean availabilityActive;
	private final boolean scheduleActive;

	public EventGroupListDto(Long id, String name, boolean availabilityActive, boolean scheduleActive) {

		this.id = id;
		this.name = name;
		this.availabilityActive = availabilityActive;
		this.scheduleActive = scheduleActive;
	}

	public Long getId() {

		return id;
	}

	public String getName() {

		return name;
	}

	public boolean isAvailabilityActive() {

		return availabilityActive;
	}

	public boolean isScheduleActive() {

		return scheduleActive;
	}
}
