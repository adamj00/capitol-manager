/*
 * Created on 30-03-2024 21:47 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.show.interfaces;

import com.capitolmanager.stage.application.StageSelectionDto;


public class ShowEditForm {

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
