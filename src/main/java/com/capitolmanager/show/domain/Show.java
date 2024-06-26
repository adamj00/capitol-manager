
package com.capitolmanager.show.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.capitolmanager.hibernate.AbstractEntity;
import com.capitolmanager.stage.domain.Stage;


@Entity(name="shows")
public class Show extends AbstractEntity {

	@Column
	private String title;

	@Column
	private int duration;

	@ManyToOne
	@JoinColumn(name = "stage_id")
	private Stage stage;

	@Column
	private String additionalInformation;


	public Show(String title, int duration, Stage stage, String additionalInformation) {

		this.title = title;
		this.duration = duration;
		this.stage = stage;
		this.additionalInformation = additionalInformation;
	}

	public Show() {

	}

	public void update(String title, int duration, Stage stage, String additionalInformation) {

		this.title = title;
		this.duration = duration;
		this.stage = stage;
		this.additionalInformation = additionalInformation;
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
