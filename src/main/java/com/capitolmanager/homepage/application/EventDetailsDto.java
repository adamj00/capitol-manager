

package com.capitolmanager.homepage.application;

public class EventDetailsDto {

	private Long id;
	private String showTitle;
	private String eventStartTime;
	private String duration;
	private String position;
	private String stage;
	private String label;
	private String additionalInfo;

	public EventDetailsDto(Long id, String showTitle, String eventStartTime, String duration, String position, String stage, String label, String additionalInfo) {

		this.id = id;
		this.showTitle = showTitle;
		this.eventStartTime = eventStartTime;
		this.duration = duration;
		this.position = position;
		this.stage = stage;
		this.label = label;
		this.additionalInfo = additionalInfo;
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

	public void setId(Long id) {

		this.id = id;
	}

	public void setShowTitle(String showTitle) {

		this.showTitle = showTitle;
	}

	public void setEventStartTime(String eventStartTime) {

		this.eventStartTime = eventStartTime;
	}

	public void setDuration(String duration) {

		this.duration = duration;
	}

	public void setPosition(String position) {

		this.position = position;
	}

	public void setStage(String stage) {

		this.stage = stage;
	}

	public void setLabel(String label) {

		this.label = label;
	}

	public String getAdditionalInfo() {

		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {

		this.additionalInfo = additionalInfo;
	}
}