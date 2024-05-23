/*
 * Created on 16-05-2024 19:46 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.schedule.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capitolmanager.schedule.application.ScheduleApplicationService;


@Service
@RequestMapping("/positionAssigning")
public class PositionAssigningController {

	private static final String VIEW_NAME = "position-assignment-view";
	@Autowired private ScheduleApplicationService scheduleApplicationService;

	@GetMapping
	String getView(Model model, @RequestParam("eventGroup") Long eventGroupId) {

		if (eventGroupId == null) {

			return "redirect:/eventGroups";
		}

		model.addAttribute("employees", scheduleApplicationService.getEmployeesForAssignment(eventGroupId));
		model.addAttribute("events", scheduleApplicationService.getEventsForAssignment(eventGroupId));
		model.addAttribute("assignedPositions", scheduleApplicationService.getAssignments(eventGroupId));
		model.addAttribute("title", scheduleApplicationService.getEventGroupName(eventGroupId));

		return VIEW_NAME;
	}

	@PostMapping("/assignPosition")
	@ResponseBody
	ResponseEntity<String> assignPosition(@RequestParam Long eventId,
		@RequestParam Long userId,
		@RequestParam Long positionId) {

		scheduleApplicationService.assignPosition(eventId, userId, positionId);

		return ResponseEntity.ok("");
	}
}
