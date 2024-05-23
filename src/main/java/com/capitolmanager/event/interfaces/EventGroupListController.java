/*
 * Created on 11-05-2024 23:02 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capitolmanager.event.application.EventApplicationService;
import com.capitolmanager.schedule.application.ScheduleApplicationService;


@Controller
@RequestMapping("/eventGroups")
public class EventGroupListController {

	@Autowired private EventApplicationService eventApplicationService;
	@Autowired private ScheduleApplicationService scheduleApplicationService;

	@GetMapping
	String getView(Model model) {

		model.addAttribute("eventGroups", eventApplicationService.getEventGroups());

		return "eventGroup-list-view";
	}

	@PostMapping("/new")
	String saveNewEventGroup(@RequestParam String name) {

		eventApplicationService.saveNewGroup(name);

		return "redirect:/eventGroups";
	}

	@PostMapping("/changeName")
	String changeEventGroupName(@RequestParam Long id, @RequestParam String name) {

		eventApplicationService.changeEventGroupName(name, id);

		return "redirect:/eventGroups";
	}

	@GetMapping("/delete")
	String deleteEventGroup(@RequestParam Long id) {

		eventApplicationService.deleteEventGroup(id);

		return "redirect:/eventGroups";
	}

	@PostMapping("/changeAvailabilityActive")
	@ResponseBody
	public ResponseEntity<?> changeAvailabilityActive(
		@RequestParam("id") Long eventGroupId,
		@RequestParam("value") boolean value) {

		eventApplicationService.changeAvailabilityActive(eventGroupId, value);

		return ResponseEntity.ok("");
	}

	@PostMapping("/changeScheduleActive")
	@ResponseBody
	public ResponseEntity<?> changeScheduleActive(
		@RequestParam("id") Long eventGroupId,
		@RequestParam("value") boolean value) {

		scheduleApplicationService.changeScheduleActivity(eventGroupId, value);

		return ResponseEntity.ok("");
	}
}
