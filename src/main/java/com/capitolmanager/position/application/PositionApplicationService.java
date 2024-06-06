
package com.capitolmanager.position.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.position.domain.Position;
import com.capitolmanager.position.domain.PositionType;
import com.capitolmanager.position.interfaces.PositionsForm;
import com.capitolmanager.stage.application.StageQueries;


@Service
public class PositionApplicationService {

	private final PositionQueries positionQueries;
	private final Repository<Position> positionRepository;
	private final StageQueries stageQueries;

	@Autowired
	PositionApplicationService(PositionQueries positionQueries, Repository<Position> positionRepository, StageQueries stageQueries) {

		Assert.notNull(positionQueries, "positionQueries must not be null");
		Assert.notNull(positionRepository, "positionRepository must not be null");
		Assert.notNull(stageQueries, "stageQueries must not be null");

		this.positionQueries = positionQueries;
		this.positionRepository = positionRepository;
		this.stageQueries = stageQueries;
	}

	public List<PositionDto> getAllPositions() {

		return positionQueries.getAll().stream()
			.map(position -> new PositionDto(position.getId(),
				position.getName(),
				position.getPositionType().name(),
				position.getQuantity(),
				position.getStage().getId()))
			.toList();
	}

	public PositionsForm getAllPositionsForm() {

		return new PositionsForm(getAllPositions());
	}

	public List<PositionType> getAllPositionTypes() {

		return List.of(PositionType.values());
	}

	public void saveAll(List<PositionDto> positions) {

		for (PositionDto position : positions) {

			if (position.getId() == null) {
				Position newPosition = new Position(position.getName(),
					PositionType.valueOf(position.getPositionType()),
					position.getQuantity(),
					stageQueries.get(position.getStageId()));

				positionRepository.saveOrUpdate(newPosition);
			}
			else {
				Position updatedPosition = positionQueries.findById(position.getId())
					.orElseThrow(IllegalStateException::new);

				updatedPosition.setName(position.getName());
				updatedPosition.setPositionType(PositionType.valueOf(position.getPositionType()));

				positionRepository.saveOrUpdate(updatedPosition);
			}
		}
	}

	public void deletePosition(Long id) {

		var position = positionQueries.findById(id)
			.orElseThrow(IllegalStateException::new);

		positionRepository.delete(position);
	}
}
