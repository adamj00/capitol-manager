/*
 * Created on 06-06-2024 21:17 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.position.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.domain.EventPositionAssignment;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.position.domain.Position;
import com.capitolmanager.position.interfaces.PositionEditForm;
import com.capitolmanager.stage.application.StageQueries;


@Service
public class PositionApplicationService {

	private final PositionQueries positionQueries;
	private final StageQueries stageQueries;
	private final Repository<Position> positionRepository;
	private final Repository<EventPositionAssignment> eventPositionAssignmentRepository;
	private final EventQueries eventQueries;

	@Autowired
	PositionApplicationService(PositionQueries positionQueries,

		StageQueries stageQueries,
		Repository<Position> positionRepository,
		Repository<EventPositionAssignment> eventPositionAssignmentRepository,
		EventQueries eventQueries) {

		Assert.notNull(positionQueries, "positionQueries must not be null");
		Assert.notNull(stageQueries, "stageQueries must not be null");
		Assert.notNull(positionRepository, "positionRepository must not be null");
		Assert.notNull(eventPositionAssignmentRepository, "eventPositionAssignmentRepository must not be null");
		Assert.notNull(eventQueries, "eventQueries must not be null");

		this.positionQueries = positionQueries;
		this.stageQueries = stageQueries;
		this.positionRepository = positionRepository;
		this.eventPositionAssignmentRepository = eventPositionAssignmentRepository;
		this.eventQueries = eventQueries;
	}

	public List<PositionListDto> getPositionsForStage(Long stageId) {

		return positionQueries.getAll().stream()
			.filter(position -> position.getStage().getId().equals(stageId))
			.map(position -> new PositionListDto(position.getId(),
				position.getName(),
				position.getPositionType(),
				position.getQuantity(),
				stageId))
			.toList();
	}

	public PositionEditForm getPositionFormById(Long id) {

		if (id == null) {
			return new PositionEditForm();
		}

		Position position = positionQueries.get(id);

		return new PositionEditForm(position.getId(),
			position.getName(),
			position.getPositionType(),
			position.getQuantity(),
			position.getStage().getId());
	}

	public void savePosition(PositionEditForm editForm) {

		Position position = new Position(editForm.getName(),
			editForm.getPositionType(),
			editForm.getQuantity(),
			stageQueries.get(editForm.getStageId()));

		positionRepository.saveOrUpdate(position);
	}

	public void updatePosition(PositionEditForm editForm) {

		Position position = positionQueries.get(editForm.getId());

		position.update(editForm.getName(),
			editForm.getPositionType(),
			editForm.getQuantity(),
			stageQueries.get(editForm.getStageId()));

		positionRepository.saveOrUpdate(position);
	}

	public void deletePosition(Long id) {

		Position position = positionQueries.get(id);

		positionRepository.delete(position);
	}
}
