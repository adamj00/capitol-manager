/*
 * Created on 28-03-2024 13:24 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.stage.interfaces;

import static com.capitolmanager.stage.interfaces.StageEditController.STAGE_EDIT_PATH;
import static com.capitolmanager.stage.interfaces.StageListController.STAGE_LIST_PATH;

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

import com.capitolmanager.position.application.PositionStageEditDto;
import com.capitolmanager.show.application.ShowApplicationService;
import com.capitolmanager.stage.application.StageApplicationService;


@Controller
@RequestMapping(STAGE_EDIT_PATH)
public class StageEditController {

	static final String STAGE_EDIT_PATH = "/stage-edit";
	static final String STAGE_EDIT_VIEW = "stage-edit-view";
	private static final String P_ID = "id";
	private static final String M_STAGE_FORM = "stage";
	private static final String M_CAN_DELETE = "canDelete";
	private static final String REDIRECT = "redirect:";

	private final StageApplicationService stageApplicationService;
	private final StageFormFactory stageFormFactory;
	private final ShowApplicationService showApplicationService;


	@Autowired
	StageEditController(StageApplicationService stageApplicationService, StageFormFactory stageFormFactory, ShowApplicationService showApplicationService) {

		Assert.notNull(stageApplicationService, "stageApplicationService must not be null");
		Assert.notNull(stageFormFactory, "stageFormFactory must not be null");
		Assert.notNull(showApplicationService, "showApplicationService must not be null");

		this.stageApplicationService = stageApplicationService;
		this.stageFormFactory = stageFormFactory;
		this.showApplicationService = showApplicationService;
	}

	@GetMapping
	String getEditView(Model model, @RequestParam(required = false, name = P_ID) Long id) {

		if (id == null) {
			model.addAttribute(M_STAGE_FORM, StageFormFactory.emptyForm());
		}

		else {
			model.addAttribute(M_STAGE_FORM, stageFormFactory.getFormById(id));
		}

		return STAGE_EDIT_VIEW;
	}

	@PostMapping
	String saveOrUpdateUser(@ModelAttribute(M_STAGE_FORM) StageEditForm stageForm, Model model) {

		if (stageForm.getId() == null) {
			stageApplicationService.saveStage(stageForm);
		}
		else {
			stageApplicationService.updateStage(stageForm);
		}

		return REDIRECT + STAGE_LIST_PATH;
	}

	@ModelAttribute(name="allPositions")
	List<PositionStageEditDto> getPositions() {

		return stageApplicationService.getAllPositionsDto();
	}

	@ModelAttribute(name=M_CAN_DELETE)
	boolean canDelete(@RequestParam(required = false) Long id) {

		return id != null && !showApplicationService.existShowWithStage(id);
	}
}
