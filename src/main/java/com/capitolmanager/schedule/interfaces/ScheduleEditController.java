/*
 * Created on 14-05-2024 11:02 by ajarzabe
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capitolmanager.schedule.application.ScheduleApplicationService;


@Controller
@RequestMapping("/scheduleEdit")
public class ScheduleEditController {


	@Autowired private ScheduleApplicationService scheduleApplicationService;

	@GetMapping
	String getView(Model model, @RequestParam(required = false) Long eventGroup) {

		if (eventGroup == null) {

			return "redirect:/eventGroups";
		}

		model.addAttribute("employees", scheduleApplicationService.getEmployees(eventGroup));
		model.addAttribute("events", scheduleApplicationService.getEvents(eventGroup));
		model.addAttribute("title", scheduleApplicationService.getEventGroupName(eventGroup));

		return "schedule-edit-view";
	}

	@PostMapping("/change")
	@ResponseBody
	public ResponseEntity<?> changeSchedule(
		@RequestParam("user") Long userId,
		@RequestParam("event") Long eventId,
		@RequestParam("schedule") Long scheduleId,
		@RequestParam("value") boolean value) {

		scheduleApplicationService.updateSchedule(userId, eventId, scheduleId, value);

		return ResponseEntity.ok("");
	}
}
