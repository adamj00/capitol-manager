/*
 * Created on 28-03-2024 13:26 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.stage.interfaces;

import java.util.List;

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

		List<StagePositionDto> requiredPositions = stage.getRequiredPositions().stream()
			.map(stagePosition -> new StagePositionDto(stagePosition.getPosition().getId(),
				stagePosition.getPosition().getName(),
				stagePosition.getQuantity()))
			.toList();

		return new StageEditForm(stage.getId(),
			stage.getName(),
			stage.getNumberOfSeats(),
			stage.getAddress(),
			requiredPositions);
	}

	public static StageEditForm emptyForm() {

		return new StageEditForm();
	}
}

