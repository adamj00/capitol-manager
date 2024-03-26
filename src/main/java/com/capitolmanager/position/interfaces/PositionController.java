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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.position.application.PositionApplicationService;
import com.capitolmanager.position.application.PositionDto;
import com.capitolmanager.position.domain.PositionType;


@Controller
public class PositionController {

	private final PositionApplicationService positionApplicationService;


	@Autowired
	PositionController(PositionApplicationService positionApplicationService) {

		Assert.notNull(positionApplicationService, "positionApplicationService must not be null");

		this.positionApplicationService = positionApplicationService;
	}

	@ModelAttribute("allPositionTypes")
	List<PositionType> getAllPositionTypes() {

		return positionApplicationService.getAllPositionTypes();
	}

	@GetMapping("positions")
	String getView(Model model) {

		model.addAttribute("positions", positionApplicationService.getAllPositions());
		return "position-list-edit-view";
	}

	@GetMapping("positions-new")
	String getViewWithNew(Model model) {

		var positions = positionApplicationService.getAllPositions();
		positions.add(new PositionDto());
		model.addAttribute("positions", positions);
		return "position-list-edit-view";
	}

	@PostMapping("positions-save-all")
	ResponseEntity<String> saveALl(@RequestBody List<PositionDto> positions) {

		positionApplicationService.saveAll(positions);
		return ResponseEntity.ok("Positions saved.");
	}
}
