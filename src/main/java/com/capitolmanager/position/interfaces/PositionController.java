/*
 * Created on 26-03-2024 21:23 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.position.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.position.application.PositionApplicationService;
import com.capitolmanager.position.application.PositionDto;
import com.capitolmanager.position.domain.PositionType;


@Controller
public class PositionController {

	public static final String POSITION_LIST_EDIT_VIEW = "position-list-edit-view";
	private final PositionApplicationService positionApplicationService;
	private final PositionsValidator positionsValidator;


	@Autowired
	PositionController(PositionApplicationService positionApplicationService, PositionsValidator positionsValidator) {

		Assert.notNull(positionApplicationService, "positionApplicationService must not be null");
		Assert.notNull(positionsValidator, "positionsValidator must not be null");

		this.positionApplicationService = positionApplicationService;
		this.positionsValidator = positionsValidator;
	}

	@InitBinder
	private void customizeBinding(WebDataBinder binder) {

		binder.setValidator(positionsValidator);
	}

	@ModelAttribute("allPositionTypes")
	List<PositionType> getAllPositionTypes() {

		return positionApplicationService.getAllPositionTypes();
	}

	@GetMapping("positions")
	String getView(Model model, @RequestParam(required = false, defaultValue = "false", name = "createNew") Boolean createNew) {

		var positionsForm = positionApplicationService.getAllPositionsForm();
		if (createNew) {
			var positions = positionsForm.getPositions();
			positions.add(new PositionDto());
			positionsForm.setPositions(positions);
		}
		model.addAttribute("positionsForm", positionsForm);
		return POSITION_LIST_EDIT_VIEW;
	}

	@PostMapping("savePositions")
	String saveAll(@Validated PositionsForm positionsForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			return POSITION_LIST_EDIT_VIEW;
		}

		positionApplicationService.saveAll(positionsForm.getPositions());
		return "redirect:" + "positions";
	}

	@GetMapping("deletePosition")
	String deletePosition(@RequestParam Long id) {

		positionApplicationService.deletePosition(id);

		return "redirect:" + "positions";
	}
}
