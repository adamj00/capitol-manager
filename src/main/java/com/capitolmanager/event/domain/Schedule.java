/*
 * Created on 11-05-2024 21:48 by ajarzabe
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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.capitolmanager.hibernate.AbstractEntity;


@Entity
@Table(name = "schedules")
public class Schedule extends AbstractEntity {

	@OneToOne
	@JoinColumn(name = "event_group_id")
	private EventGroup eventGroup;

	@OneToMany(mappedBy = "schedule")
	private Set<EventPositionAssignment> assignments;

	public Schedule(EventGroup eventGroup, Set<EventPositionAssignment> assignments) {

		this.eventGroup = eventGroup;
		this.assignments = assignments;
	}

	public Schedule() {

	}

	public EventGroup getEventGroup() {

		return eventGroup;
	}

	public void setEventGroup(EventGroup eventGroup) {

		this.eventGroup = eventGroup;
	}

	public Set<EventPositionAssignment> getAssignments() {

		return assignments;
	}

	public void setAssignments(Set<EventPositionAssignment> assignments) {

		this.assignments = assignments;
	}
}
