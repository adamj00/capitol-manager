

package com.capitolmanager.show.application;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.show.domain.Show;
import com.capitolmanager.show.interfaces.ShowEditForm;
import com.capitolmanager.stage.application.StageQueries;


@Service
public class ShowApplicationService {

	private final ShowQueries showQueries;
	private final Repository<Show> showRepository;
	private final StageQueries stageQueries;


	@Autowired
	ShowApplicationService(ShowQueries showQueries, Repository<Show> showRepository, StageQueries stageQueries) {

		Assert.notNull(showQueries, "showQueries must not be null");
		Assert.notNull(showRepository, "showRepository must not be null");
		Assert.notNull(stageQueries, "stageQueries must not be null");

		this.showQueries = showQueries;
		this.showRepository = showRepository;
		this.stageQueries = stageQueries;
	}

	public List<ShowListDto> getAllShows() {

		return showQueries.getAll().stream()
			.map(show -> new ShowListDto(show.getId(),
				show.getTitle(),
				show.getDuration(),
				show.getStage().getName(),
				show.getAdditionalInformation()))
			.toList();
	}

	public List<ShowEventDto> getAllShowsEventDto() {

		return showQueries.getAll().stream()
			.map(show -> new ShowEventDto(show.getId(),
				show.getTitle()
				))
			.toList();
	}

	public void saveShow(ShowEditForm form) {

		Assert.notNull(form, "form must not be null");

		Show show = new Show(form.getTitle(),
			form.getDuration(),
			stageQueries.findById(form.getStageSelectionDto().getId())
				.orElseThrow(EntityExistsException::new),
			form.getAdditionalInformation());

		showRepository.saveOrUpdate(show);
	}

	public void updateShow(ShowEditForm form) {

		Assert.notNull(form, "form must not be null");

		Show show = showQueries.findById(form.getId())
			.orElseThrow(EntityExistsException::new);

		 show.update(form.getTitle(),
			form.getDuration(),
			stageQueries.findById(form.getStageSelectionDto().getId())
				.orElseThrow(EntityExistsException::new),
			form.getAdditionalInformation());

		showRepository.saveOrUpdate(show);
	}

	public void deleteShow(Long id) {

		Show show = showQueries.findById(id)
				.orElseThrow(EntityExistsException::new);

		showRepository.delete(show);
	}

	public boolean existShowWithStage(Long stageId) {

		return showQueries.getAll().stream()
			.anyMatch(show -> show.getStage().getId().equals(stageId));
	}

	public boolean existShowWithTitle(Long id, String title) {

		return getAllShows().stream()
			.anyMatch(showListDto -> showListDto.getTitle().equals(title)
			&& !showListDto.getId().equals(id));
	}

}
