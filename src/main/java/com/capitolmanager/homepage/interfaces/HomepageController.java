/*
 * Created on 22-03-2024 11:49 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.homepage.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capitolmanager.homepage.application.EventDetailsDto;
import com.capitolmanager.homepage.application.HomepageService;


@Controller
public class HomepageController {

	private final HomepageService homepageService;


	@Autowired
	HomepageController(HomepageService homepageService) {

		Assert.notNull(homepageService, "homepageService must not be null");

		this.homepageService = homepageService;
	}

	@GetMapping
	String getHomepage(Model model) {

		model.addAttribute("events", homepageService.getCloseEventsForLoggedUser());
		model.addAttribute("userName", homepageService.getLoggedUserName());

		return "homepage";
	}

	@GetMapping("/eventDetails")
	@ResponseBody
	ResponseEntity<EventDetailsDto> getDetails(@RequestParam Long eventId) {

		return ResponseEntity.of(homepageService.getEventDetails(eventId));
	}
}
