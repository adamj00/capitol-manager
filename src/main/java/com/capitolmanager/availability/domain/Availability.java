

package com.capitolmanager.availability.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.capitolmanager.event.domain.Event;
import com.capitolmanager.hibernate.AbstractEntity;
import com.capitolmanager.user.domain.User;

@Entity
@Table(name = "availability")
public class Availability extends AbstractEntity {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "available")
	private Boolean available;

	public Availability() {

	}

	public Availability(Event event, User user, Boolean available) {

		this.event = event;
		this.user = user;
		this.available = available;
	}

	public Event getEvent() {

		return event;
	}

	public void setEvent(Event event) {

		this.event = event;
	}

	public User getUser() {

		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}

	public Boolean getAvailable() {

		return available;
	}

	public void setAvailable(Boolean available) {

		this.available = available;
	}
}
