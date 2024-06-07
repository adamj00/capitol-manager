/*
 * Created on 06-06-2024 21:14 by ajarzabe
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.position.application.PositionApplicationService;
import com.capitolmanager.position.domain.PositionType;
import com.capitolmanager.stage.application.StageApplicationService;


@Controller
@RequestMapping("/position-edit")
public class PositionEditController {

	private final PositionApplicationService positionApplicationService;
	private final StageApplicationService stageApplicationService;


	@Autowired
	PositionEditController(PositionApplicationService positionApplicationService, StageApplicationService stageApplicationService) {

		Assert.notNull(positionApplicationService, "positionApplicationService must not be null");
		Assert.notNull(stageApplicationService, "stageApplicationService must not be null");

		this.positionApplicationService = positionApplicationService;
		this.stageApplicationService = stageApplicationService;
	}

	@GetMapping
	String getView(Model model, @RequestParam Long stageId) {

		model.addAttribute("positions", positionApplicationService.getPositionsForStage(stageId));
		model.addAttribute("stageName", stageApplicationService.getStageName(stageId));
		model.addAttribute("allPositionTypes", PositionType.values());
		model.addAttribute("stageId", stageId);

		return "position-edit-view";
	}

	@GetMapping("/position")
	ResponseEntity<PositionEditForm> getPositionForm(@RequestParam(required = false, defaultValue = "null") Long id) {

		return ResponseEntity.ok(positionApplicationService.getPositionFormById(id));
	}

	@PostMapping
	public String savePosition(@ModelAttribute(name = "editPositionForm") PositionEditForm editForm, Model model) {


		if (editForm.getId() == null) {

			positionApplicationService.savePosition(editForm);
		}

		else {

			positionApplicationService.updatePosition(editForm);
		}

		return "redirect:position-edit?stageId=" + editForm.getStageId();
	}

	@GetMapping("/delete")
	String deletePosition(@RequestParam Long id, @RequestParam Long stageId) {

		positionApplicationService.deletePosition(id);

		return "redirect:?stageId=" + stageId;
	}
}
