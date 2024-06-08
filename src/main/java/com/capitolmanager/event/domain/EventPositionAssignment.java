

package com.capitolmanager.event.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.capitolmanager.hibernate.AbstractEntity;
import com.capitolmanager.position.domain.Position;
import com.capitolmanager.user.domain.User;


@Entity
@Table(name = "event_position_assignments")
public class EventPositionAssignment extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne
	@JoinColumn(name = "position_id")
	private Position position;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public EventPositionAssignment(Event event, Position position, User user) {

		this.event = event;
		this.position = position;
		this.user = user;
	}

	public EventPositionAssignment() {

	}

	public Event getEvent() {

		return event;
	}

	public void setEvent(Event event) {

		this.event = event;
	}

	public Position getPosition() {

		return position;
	}

	public void setPosition(Position position) {

		this.position = position;
	}

	public User getUser() {

		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}
}
