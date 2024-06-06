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

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.position.application.PositionDto;
import com.capitolmanager.position.application.PositionQueries;

import com.capitolmanager.position.domain.Position;
import com.capitolmanager.position.domain.PositionType;
import com.capitolmanager.stage.domain.Stage;
import com.capitolmanager.stage.interfaces.StageEditForm;


@Service
public class StageApplicationService {

	private final StageQueries stageQueries;
	private final Repository<Stage> stageRepository;
	private final PositionQueries positionQueries;
	private final Repository<Position> positionRepository;

	@Autowired
	public StageApplicationService(StageQueries stageQueries, Repository<Stage> stageRepository, PositionQueries positionQueries, Repository<Position> positionRepository) {

		Assert.notNull(stageQueries, "stageQueries must not be null");
		Assert.notNull(stageRepository, "stageRepository must not be null");
		Assert.notNull(positionQueries, "positionQueries must not be null");
		Assert.notNull(positionRepository, "positionRepository must not be null");

		this.stageQueries = stageQueries;
		this.stageRepository = stageRepository;
		this.positionQueries = positionQueries;
		this.positionRepository = positionRepository;
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

		stageRepository.saveOrUpdate(stage);

		stage = stageQueries.getAll().stream()
			.filter(s -> s.getName().equals(stageEditForm.getName()))
			.findFirst()
			.orElseThrow(EntityNotFoundException::new);

		for (var positionDto : stageEditForm.getRequiredPositions()) {

			Position position = new Position(positionDto.getName(),
				PositionType.valueOf(positionDto.getPositionType()),
				positionDto.getQuantity(),
				stage);

			stage.getRequiredPositions().add(position);

			positionRepository.saveOrUpdate(position);
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

	public List<StageSelectionDto> getAllStagesSelectionDto() {

		return stageQueries.getAll().stream()
			.map(stage -> new StageSelectionDto(stage.getId(), stage.getName()))
			.toList();
	}

	private void updateStagePositions(Stage stage, List<PositionDto> positions) {


		for (var position : stage.getRequiredPositions()) {

			if (positions.stream().noneMatch(p -> p.getId().equals(position.getId()))) {

				stage.getRequiredPositions().remove(position);

				positionRepository.delete(position);
			}
		}

		for (var positionDto : positions) {

			Position position;
			if (positionDto.getId() == null) {
				position = new Position(positionDto.getName(),
					PositionType.valueOf(positionDto.getPositionType()),
					positionDto.getQuantity(),
					stage);

				stage.getRequiredPositions().add(position);

			}
			else {
				position = positionQueries.findById(positionDto.getId())
					.orElseThrow(EntityNotFoundException::new);

				position.setName(positionDto.getName());
				position.setPositionType(PositionType.valueOf(positionDto.getPositionType()));
				position.setQuantity(positionDto.getQuantity());

			}
			positionRepository.saveOrUpdate(position);

			stageRepository.saveOrUpdate(stage);
		}
	}

	public void deleteStage(Long id) {

		Stage stage = stageQueries.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Stage not found"));

		stageRepository.delete(stage);
	}

	public boolean existStageWithName(Long id, String name) {

		return stageQueries.getAll().stream()
			.anyMatch(stage -> stage.getName().equals(name) && !stage.getId().equals(id));
	}
}
