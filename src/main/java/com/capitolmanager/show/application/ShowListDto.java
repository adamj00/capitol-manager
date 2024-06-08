
package com.capitolmanager.show.application;

public class ShowListDto {

	private Long id;
	private String title;
	private int duration;
	private String stage;
	private String additionalInformation;

	public ShowListDto(Long id, String title, int duration, String stage, String additionalInformation) {

		this.id = id;
		this.title = title;
		this.duration = duration;
		this.stage = stage;
		this.additionalInformation = additionalInformation;
	}

	public ShowListDto() {

	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public int getDuration() {

		return duration;
	}

	public void setDuration(int duration) {

		this.duration = duration;
	}

	public String getStage() {

		return stage;
	}

	public void setStage(String stage) {

		this.stage = stage;
	}

	public String getAdditionalInformation() {

		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {

		this.additionalInformation = additionalInformation;
	}
}
