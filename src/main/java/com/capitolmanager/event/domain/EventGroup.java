/*
 * Created on 11-05-2024 21:46 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.capitolmanager.hibernate.AbstractEntity;


@Entity
@Table(name = "event_groups")
public class EventGroup extends AbstractEntity {

	@Column
	private String name;

	@OneToMany(mappedBy = "eventGroup", fetch = FetchType.EAGER)
	private Set<Event> events;

	@OneToOne(mappedBy = "eventGroup")
	private Schedule schedule;

	@Column(name = "availability_active")
	private boolean availabilityActive;

	public EventGroup(String name, Set<Event> events, Schedule schedule, boolean availabilityActive) {

		this.name = name;
		this.events = events;
		this.schedule = schedule;
		this.availabilityActive = availabilityActive;
	}

	public EventGroup() {

	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Set<Event> getEvents() {

		return events;
	}

	public void setEvents(Set<Event> events) {

		this.events = events;
	}

	public Schedule getSchedule() {

		return schedule;
	}

	public void setSchedule(Schedule schedule) {

		this.schedule = schedule;
	}

	public boolean isAvailabilityActive() {

		return availabilityActive;
	}

	public void setAvailabilityActive(boolean availabilityActive) {

		this.availabilityActive = availabilityActive;
	}
}
