
package com.capitolmanager.event.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.capitolmanager.hibernate.AbstractEntity;


@Entity
@Table(name = "event_groups")
public class EventGroup extends AbstractEntity {

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "eventGroup", fetch = FetchType.EAGER)
	private Set<Event> events;

	@Column(name = "availability_active")
	private boolean availabilityActive;

	@Column(name = "active")
	private boolean active;

	public EventGroup(String name, Set<Event> events, boolean availabilityActive, boolean active) {

		this.name = name;
		this.events = events;
		this.availabilityActive = availabilityActive;
		this.active = active;
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

	public boolean isAvailabilityActive() {

		return availabilityActive;
	}

	public void setAvailabilityActive(boolean availabilityActive) {

		this.availabilityActive = availabilityActive;
	}

	public boolean isActive() {

		return active;
	}

	public void setActive(boolean active) {

		this.active = active;
	}
}
