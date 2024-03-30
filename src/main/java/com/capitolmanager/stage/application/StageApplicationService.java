/*
 * Created on 28-03-2024 12:43 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.stage.application;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.position.application.PositionQueries;
import com.capitolmanager.position.application.PositionStageEditDto;
import com.capitolmanager.position.domain.Position;
import com.capitolmanager.stage.domain.Stage;
import com.capitolmanager.stage.domain.StagePosition;
import com.capitolmanager.stage.interfaces.StageEditForm;
import com.capitolmanager.stage.interfaces.StagePositionDto;


@Service
public class StageApplicationService {

	private final StageQueries stageQueries;
	private final Repository<Stage> stageRepository;
	private final PositionQueries positionQueries;


	@Autowired
	StageApplicationService(StageQueries stageQueries, Repository<Stage> stageRepository, PositionQueries positionQueries) {

		Assert.notNull(stageQueries, "stageQueries must not be null");
		Assert.notNull(stageRepository, "stageRepository must not be null");
		Assert.notNull(positionQueries, "positionQueries must not be null");

		this.stageQueries = stageQueries;
		this.stageRepository = stageRepository;
		this.positionQueries = positionQueries;
	}

	public List<StageListDto> findAllStages() {

		return stageQueries.getAll().stream()
			.map(stage -> new StageListDto(stage.getId(),
				stage.getName(),
				stage.getNumberOfSeats(),
				stage.getAddress()))
			.toList();
	}

	public void saveStage(StageEditForm stageEditForm) {

		Stage stage = new Stage(stageEditForm.getName(),
			stageEditForm.getNumberOfSeats(),
			stageEditForm.getAddress(),
			new LinkedList<>());

		for (var stagePositionDto : stageEditForm.getRequiredPositions()) {

			Position position = positionQueries.findById(stagePositionDto.getPositionId())
				.orElseThrow(() -> new EntityNotFoundException("Position not found"));

			StagePosition stagePosition = new StagePosition();
			stagePosition.setStage(stage);
			stagePosition.setPosition(position);
			stagePosition.setQuantity(stagePositionDto.getQuantity());

			stage.getRequiredPositions().add(stagePosition);
		}

		stageRepository.saveOrUpdate(stage);
	}

	public void updateStage(StageEditForm stageEditForm) {

		Stage stage = stageQueries.findById(stageEditForm.getId())
			.orElseThrow(EntityNotFoundException::new);

		updateStagePositions(stage, stageEditForm.getRequiredPositions());

		stage.updateStage(stageEditForm.getName(),
			stageEditForm.getNumberOfSeats(),
			stageEditForm.getAddress());

		stageRepository.saveOrUpdate(stage);
	}

	public List<PositionStageEditDto> getAllPositionsDto() {

		return positionQueries.getAll().stream()
			.map(position -> new PositionStageEditDto(position.getId(),
				position.getName()))
			.toList();
	}

	public List<StageSelectionDto> getAllStagesSelectionDto() {

		return stageQueries.getAll().stream()
			.map(stage -> new StageSelectionDto(stage.getId(), stage.getName()))
			.toList();
	}

	private void updateStagePositions(Stage stage, List<StagePositionDto> updatedPositionQuantities) {

		stage.getRequiredPositions().removeIf(stagePosition ->
			updatedPositionQuantities.stream()
				.noneMatch(stagePositionDto -> Objects.equals(stagePosition.getPosition().getId(), stagePositionDto.getPositionId())));

		for (var stagePositionDto : updatedPositionQuantities) {
			Long positionId = stagePositionDto.getPositionId();
			Integer quantity = stagePositionDto.getQuantity();

			StagePosition stagePosition = stage.getRequiredPositions().stream()
				.filter(sp -> sp.getPosition().getId().equals(positionId))
				.findFirst()
				.orElse(null);

			if (stagePosition == null) {

				stagePosition = new StagePosition();
				stagePosition.setStage(stage);
				Position position = positionQueries.findById(positionId)
					.orElseThrow(() -> new EntityNotFoundException("Position not found"));
				stagePosition.setPosition(position);
				stage.getRequiredPositions().add(stagePosition);
			}

			stagePosition.setQuantity(quantity);
		}
	}

	public void deleteStage(Long id) {

		Stage stage = stageQueries.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Stage not found"));

		stageRepository.delete(stage);
	}
}
