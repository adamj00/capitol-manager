
package com.capitolmanager.stage.interfaces;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.stage.application.StageQueries;
import com.capitolmanager.stage.domain.Stage;


@Service
public class StageFormFactory {

	private final StageQueries stageQueries;

	@Autowired
	StageFormFactory(StageQueries stageQueries) {

		Assert.notNull(stageQueries, "stageQueries must not be null");

		this.stageQueries = stageQueries;
	}

	public StageEditForm getFormById(Long id) {

		Stage stage = stageQueries.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("No stage " + id));

		return new StageEditForm(stage.getId(),
			stage.getName(),
			stage.getNumberOfSeats(),
			stage.getAddress());
	}

	public static StageEditForm emptyForm() {

		return new StageEditForm();
	}
}

