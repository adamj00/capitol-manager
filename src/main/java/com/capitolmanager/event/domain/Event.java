

package com.capitolmanager.event.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.capitolmanager.hibernate.AbstractEntity;
import com.capitolmanager.show.domain.Show;
import com.capitolmanager.utils.DateUtils;


@Entity
@Table(name = "events")
public class Event extends AbstractEntity {

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "show_id", nullable = false)
	private Show show;

	@ManyToOne
	@JoinColumn(name = "event_group_id")
	private EventGroup eventGroup;

	@Column(name = "event_start_time", nullable = false)
	private LocalDateTime eventStartTime;

	@OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
	private Set<EventPositionAssignment> assignments;

	public Event() {

	}

	public Event(Show show, EventGroup eventGroup, LocalDateTime eventStartTime, Set<EventPositionAssignment> assignments) {

		this.show = show;
		this.eventGroup = eventGroup;
		this.eventStartTime = eventStartTime;
		this.assignments = assignments;
	}

	public void update(Show show, EventGroup eventGroup, LocalDateTime eventStartTime) {

		this.show = show;
		this.eventGroup = eventGroup;
		this.eventStartTime = eventStartTime;
	}

	public Show getShow() {

		return show;
	}

	public void setShow(Show show) {

		this.show = show;
	}

	public LocalDateTime getEventStartTime() {

		return eventStartTime;
	}

	public void setEventStartTime(LocalDateTime eventStartTime) {

		this.eventStartTime = eventStartTime;
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

	@Override
	public String toString() {

		return DateUtils.formatLocalDateTimeWithGodzina(eventStartTime) + " - " + show.getTitle();
	}
}