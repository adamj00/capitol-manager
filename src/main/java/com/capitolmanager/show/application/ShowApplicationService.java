/*
 * Created on 29-03-2024 20:04 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.show.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.show.domain.Show;
import com.capitolmanager.stage.application.StageListDto;


@Service
public class ShowApplicationService {

	private final ShowQueries showQueries;
	private final Repository<Show> showRepository;

	@Autowired
	ShowApplicationService(ShowQueries showQueries, Repository<Show> showRepository) {

		Assert.notNull(showQueries, "showQueries must not be null");
		Assert.notNull(showRepository, "showRepository must not be null");

		this.showQueries = showQueries;
		this.showRepository = showRepository;
	}


	public List<ShowListDto> getAllShows() {

		return showQueries.getAll().stream()
			.map(show -> new ShowListDto(show.getId(),
				show.getTitle(),
				show.getDuration(),
				show.getStage(),
				show.getAdditionalInformation()))
			.toList();
	}
}
