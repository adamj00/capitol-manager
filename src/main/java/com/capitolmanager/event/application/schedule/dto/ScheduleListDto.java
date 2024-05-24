/*
 * Created on 21-05-2024 22:35 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.application.schedule.dto;

public class ScheduleListDto {

	private final Long id;
	private final String name;
	private final int assignedEventsQuantity;

	public ScheduleListDto(Long id, String name, int assignedEventsQuantity) {

		this.id = id;
		this.name = name;
		this.assignedEventsQuantity = assignedEventsQuantity;
	}

	public Long getId() {

		return id;
	}

	public String getName() {

		return name;
	}

	public int getAssignedEventsQuantity() {

		return assignedEventsQuantity;
	}
}
