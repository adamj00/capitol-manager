
package com.capitolmanager.event.application.dto;

import java.time.LocalDateTime;

import com.capitolmanager.show.application.ShowEventDto;


public class EventDto {

	private final long id;
	private final ShowEventDto show;
	private final LocalDateTime eventStartTime;

	public EventDto(long id, ShowEventDto show, LocalDateTime eventStartTime) {

		this.id = id;
		this.show = show;
		this.eventStartTime = eventStartTime;
	}

	public long getId() {

		return id;
	}

	public ShowEventDto getShow() {

		return show;
	}

	public LocalDateTime getEventStartTime() {

		return eventStartTime;
	}
}
