/*
 * Created on 09-05-2024 18:11 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.availability.interfaces;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capitolmanager.availability.application.AvailabilityApplicationService;


@Controller
@RequestMapping("/availability")
public class AvailabilityController {

	@Autowired private AvailabilityApplicationService availabilityApplicationService;

	@GetMapping
	String getView(@RequestParam(required = false) Long eventGroup, Model model) {

		if (eventGroup == null || !availabilityApplicationService.isAvailabilityActive(eventGroup)) {

			return "redirect:/availabilityList";
		}

		model.addAttribute("weeks", availabilityApplicationService.getMonthWeeksWithAvailabilities(eventGroup));
		model.addAttribute("formatter", DateTimeFormatter.ofPattern("HH:mm"));
		model.addAttribute("title", availabilityApplicationService.getAvailabilityPageTitle(eventGroup));

		return "availability";
	}
	@PostMapping("/change")
	@ResponseBody
	public ResponseEntity<String> changeAvailability(
		@RequestParam("id") Long availabilityId,
		@RequestParam("value") boolean value) {

		availabilityApplicationService.updateAvailability(availabilityId, value);

		return ResponseEntity.ok("");
	}
}
