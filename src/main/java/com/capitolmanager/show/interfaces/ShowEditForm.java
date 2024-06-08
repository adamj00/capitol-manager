

package com.capitolmanager.show.interfaces;

import com.capitolmanager.stage.application.StageSelectionDto;


public class ShowEditForm {

	public static final String F_TITLE = "title";
	public static final String F_DURATION = "duration";
	public static final String F_STAGE = "stageSelectionDto";

	private Long id;
	private String title;
	private int duration;
	private StageSelectionDto stageSelectionDto = new StageSelectionDto();
	private String additionalInformation;

	public ShowEditForm(Long id, String title, int duration, StageSelectionDto stageSelectionDto, String additionalInformation) {

		this.id = id;
		this.title = title;
		this.duration = duration;
		this.stageSelectionDto = stageSelectionDto;
		this.additionalInformation = additionalInformation;
	}

	public ShowEditForm() {

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

	public StageSelectionDto getStageSelectionDto() {

		return stageSelectionDto;
	}

	public void setStageSelectionDto(StageSelectionDto stageSelectionDto) {

		this.stageSelectionDto = stageSelectionDto;
	}

	public String getAdditionalInformation() {

		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {

		this.additionalInformation = additionalInformation;
	}
}
