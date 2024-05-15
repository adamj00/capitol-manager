/*
 * Created on 11-05-2024 22:07 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.schedule.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.capitolmanager.event.domain.Event;
import com.capitolmanager.hibernate.AbstractEntity;
import com.capitolmanager.stage.domain.StagePosition;
import com.capitolmanager.user.domain.User;


@Entity
@Table(name = "event_position_assignments")
public class EventPositionAssignment extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne
	@JoinColumn(name = "position_id")
	private StagePosition position;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public EventPositionAssignment(Schedule schedule, Event event, StagePosition position, User user) {

		this.schedule = schedule;
		this.event = event;
		this.position = position;
		this.user = user;
	}

	public EventPositionAssignment() {

	}

	public Schedule getSchedule() {

		return schedule;
	}

	public void setSchedule(Schedule schedule) {

		this.schedule = schedule;
	}

	public Event getEvent() {

		return event;
	}

	public void setEvent(Event event) {

		this.event = event;
	}

	public StagePosition getPosition() {

		return position;
	}

	public void setPosition(StagePosition position) {

		this.position = position;
	}

	public User getUser() {

		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}
}
