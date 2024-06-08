

package com.capitolmanager.homepage.application;

public class EventDetailsDto {

	private Long id;
	private String showTitle;
	private String eventStartTime;
	private String duration;
	private String position;
	private String stage;
	private String label;

	public EventDetailsDto(Long id, String showTitle, String eventStartTime, String duration, String position, String stage, String label) {

		this.id = id;
		this.showTitle = showTitle;
		this.eventStartTime = eventStartTime;
		this.duration = duration;
		this.position = position;
		this.stage = stage;
		this.label = label;
	}

	public Long getId() {

		return id;
	}

	public String getShowTitle() {

		return showTitle;
	}

	public String getEventStartTime() {

		return eventStartTime;
	}

	public String getDuration() {

		return duration;
	}

	public String getPosition() {

		return position;
	}

	public String getStage() {

		return stage;
	}

	public String getLabel() {

		return label;
	}
}