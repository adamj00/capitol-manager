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

package com.capitolmanager.event.interfaces.manager;

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
import com.capitolmanager.event.application.schedule.ScheduleApplicationService;


@Controller
@RequestMapping("/eventGroups")
public class EventGroupListController {

	private static final String EVENT_GROUPS_URL = "/eventGroups";
	private static final String REDIRECT = "redirect:";
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

		return REDIRECT + EVENT_GROUPS_URL;
	}

	@PostMapping("/changeName")
	String changeEventGroupName(@RequestParam Long id, @RequestParam String name) {

		eventApplicationService.changeEventGroupName(name, id);

		return REDIRECT + EVENT_GROUPS_URL;
	}

	@GetMapping("/delete")
	String deleteEventGroup(@RequestParam Long id) {

		eventApplicationService.deleteEventGroup(id);

		return REDIRECT + EVENT_GROUPS_URL;
	}

	@PostMapping("/changeAvailabilityActive")
	@ResponseBody
	public ResponseEntity<String> changeAvailabilityActive(
		@RequestParam("id") Long eventGroupId,
		@RequestParam("value") boolean value) {

		eventApplicationService.changeAvailabilityActive(eventGroupId, value);

		return ResponseEntity.ok("");
	}

	@PostMapping("/changeScheduleActive")
	@ResponseBody
	public ResponseEntity<String> changeScheduleActive(
		@RequestParam("id") Long eventGroupId,
		@RequestParam("value") boolean value) {

		scheduleApplicationService.changeScheduleActivity(eventGroupId, value);

		return ResponseEntity.ok("");
	}
}
