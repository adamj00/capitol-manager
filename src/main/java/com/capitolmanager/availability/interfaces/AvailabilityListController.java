/*
 * Created on 12-05-2024 17:09 by ajarzabe
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capitolmanager.availability.application.AvailabilityApplicationService;


@Controller
@RequestMapping("/availabilityList")
public class AvailabilityListController {

	@Autowired private AvailabilityApplicationService availabilityApplicationService;

	@GetMapping
	String getView(Model model) {

		model.addAttribute("eventGroups", availabilityApplicationService.getAvailabilityList());

		return "availability-list-view";
	}
}
