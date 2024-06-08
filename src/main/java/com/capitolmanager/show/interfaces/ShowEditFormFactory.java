
package com.capitolmanager.show.interfaces;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.show.application.ShowQueries;
import com.capitolmanager.stage.application.StageSelectionDto;


@Service
public class ShowEditFormFactory {


	private final ShowQueries showQueries;


	@Autowired
	ShowEditFormFactory(ShowQueries showQueries) {

		Assert.notNull(showQueries, "showQueries must not be null");

		this.showQueries = showQueries;
	}


	public ShowEditForm getFormById(Long id) {

		return showQueries.findById(id)
			.map(show -> new ShowEditForm(show.getId(),
				show.getTitle(),
				show.getDuration(),
				new StageSelectionDto(show.getStage().getId(),
					show.getStage().getName()),
				show.getAdditionalInformation()))
			.orElseThrow(EntityExistsException::new);
	}

	public static ShowEditForm emptyForm() {
		return new ShowEditForm();
	}
}
