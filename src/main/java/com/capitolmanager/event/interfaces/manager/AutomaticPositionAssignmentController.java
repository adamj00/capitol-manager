/*
 * Created on 25-05-2024 15:15 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.interfaces.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.event.application.schedule.PositionsAssignmentService;


@Controller
@RequestMapping("/autoPositionAssignment")
public class AutomaticPositionAssignmentController {

	private final PositionsAssignmentService positionsAssignmentService;


	@Autowired
	AutomaticPositionAssignmentController(PositionsAssignmentService positionsAssignmentService) {

		Assert.notNull(positionsAssignmentService, "positionsAssignmentService must not be null");

		this.positionsAssignmentService = positionsAssignmentService;
	}

	@GetMapping
	String assignAndRedirect(@RequestParam Long eventGroupId) {

		positionsAssignmentService.assignPositionsAutomatically(eventGroupId);

		return "redirect:/positionAssigning?eventGroup=" + eventGroupId;
	}
}
