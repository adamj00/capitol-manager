/*
 * Created on 29-03-2024 20:07 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.show.interfaces;

import static com.capitolmanager.show.interfaces.ShowListController.SHOW_LIST_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capitolmanager.show.application.ShowApplicationService;
import com.capitolmanager.show.application.ShowListDto;


@Controller
@RequestMapping(SHOW_LIST_PATH)
public class ShowListController {
	
	static final String SHOW_LIST_PATH = "/show-list";
	private static final String SHOW_LIST_VIEW = "show-list-view";
	private static final String M_SHOWS = "shows";

	private final ShowApplicationService showApplicationService;

	@Autowired
	ShowListController(ShowApplicationService showApplicationService) {

		Assert.notNull(showApplicationService, "showApplicationService must not be null");

		this.showApplicationService = showApplicationService;
	}

	@GetMapping
	String getShowsView() {

		return SHOW_LIST_VIEW;
	}

	@ModelAttribute(M_SHOWS)
	private List<ShowListDto> getShows() {

		return showApplicationService.getAllShows();
	}
}
