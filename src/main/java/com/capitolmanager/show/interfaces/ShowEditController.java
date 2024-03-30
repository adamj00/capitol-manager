/*
 * Created on 30-03-2024 21:47 by ajarzabe
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

import static com.capitolmanager.show.interfaces.ShowEditController.SHOW_EDIT_PATH;
import static com.capitolmanager.show.interfaces.ShowListController.SHOW_LIST_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.show.application.ShowApplicationService;
import com.capitolmanager.stage.application.StageApplicationService;
import com.capitolmanager.stage.application.StageSelectionDto;


@Controller
@RequestMapping(SHOW_EDIT_PATH)
public class ShowEditController {

	static final String	SHOW_EDIT_PATH = "show-edit";
	private static final String VIEW_NAME = "show-edit-view";
	private static final String M_STAGES = "stages";
	private static final String M_SHOW = "show";

	private final ShowApplicationService showApplicationService;
	private final StageApplicationService stageApplicationService;
	private final ShowEditFormFactory showEditFormFactory;


	@Autowired
	ShowEditController(ShowApplicationService showApplicationService, StageApplicationService stageApplicationService, ShowEditFormFactory showEditFormFactory) {

		Assert.notNull(showApplicationService, "showApplicationService must not be null");
		Assert.notNull(stageApplicationService, "stageApplicationService must not be null");
		Assert.notNull(showEditFormFactory, "showEditFormFactory must not be null");

		this.showApplicationService = showApplicationService;
		this.stageApplicationService = stageApplicationService;
		this.showEditFormFactory = showEditFormFactory;
	}

	@ModelAttribute(M_STAGES)
	List<StageSelectionDto> getAllStages() {

		return stageApplicationService.getAllStagesSelectionDto();
	}

	@GetMapping
	String getEditView(@RequestParam(required = false) Long id, Model model) {

		if (id == null) {
			model.addAttribute(M_SHOW, ShowEditFormFactory.emptyForm());
		}

		else {
			model.addAttribute(M_SHOW, showEditFormFactory.getFormById(id));
		}

		return VIEW_NAME;
	}

	@PostMapping
	String saveShow(@ModelAttribute ShowEditForm show, Model model) {

		if (show.getId() == null) {

			showApplicationService.saveShow(show);
		}

		else {

			showApplicationService.updateShow(show);
		}

		return "redirect:" + SHOW_LIST_PATH;
	}
}
