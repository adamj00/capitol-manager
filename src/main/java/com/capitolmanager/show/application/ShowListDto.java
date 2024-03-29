/*
 * Created on 29-03-2024 20:00 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.show.application;

import com.capitolmanager.stage.domain.Stage;


public class ShowListDto {

	private Long id;
	private String title;
	private int duration;
	private Stage stage;
	private String additionalInformation;


	public ShowListDto(Long id, String title, int duration, Stage stage, String additionalInformation) {

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

	public Stage getStage() {

		return stage;
	}

	public void setStage(Stage stage) {

		this.stage = stage;
	}

	public String getAdditionalInformation() {

		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {

		this.additionalInformation = additionalInformation;
	}
}
