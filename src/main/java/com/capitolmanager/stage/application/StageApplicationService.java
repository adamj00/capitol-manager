
package com.capitolmanager.stage.application;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.stage.domain.Stage;
import com.capitolmanager.stage.interfaces.StageEditForm;


@Service
public class StageApplicationService {

	private final StageQueries stageQueries;
	private final Repository<Stage> stageRepository;

	@Autowired
	public StageApplicationService(StageQueries stageQueries, Repository<Stage> stageRepository) {

		Assert.notNull(stageQueries, "stageQueries must not be null");
		Assert.notNull(stageRepository, "stageRepository must not be null");

		this.stageQueries = stageQueries;
		this.stageRepository = stageRepository;
	}

	public List<StageListDto> findAllStages() {

		return stageQueries.getAll().stream()
			.map(stage -> new StageListDto(stage.getId(),
				stage.getName(),
				stage.getNumberOfSeats(),
				stage.getAddress()))
			.toList();
	}

	public Long saveStage(StageEditForm stageEditForm) {

		Stage stage = new Stage(stageEditForm.getName(),
			stageEditForm.getNumberOfSeats(),
			stageEditForm.getAddress(),
			new LinkedList<>());

		stageRepository.saveOrUpdate(stage);

		return stage.getId();
	}

	public void updateStage(StageEditForm stageEditForm) {

		Stage stage = stageQueries.findById(stageEditForm.getId())
			.orElseThrow(EntityNotFoundException::new);

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

	public void deleteStage(Long id) {

		Stage stage = stageQueries.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Stage not found"));

		stageRepository.delete(stage);
	}

	public boolean existStageWithName(Long id, String name) {

		return stageQueries.getAll().stream()
			.anyMatch(stage -> stage.getName().equals(name) && !stage.getId().equals(id));
	}

	public String getStageName(Long stageId) {

		return stageQueries.get(stageId).getName();
	}
}
