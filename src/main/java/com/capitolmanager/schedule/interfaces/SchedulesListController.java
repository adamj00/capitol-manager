/*
 * Created on 21-05-2024 22:16 by ajarzabe
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capitolmanager.availability.application.AvailabilityApplicationService;
import com.capitolmanager.schedule.application.ScheduleApplicationService;


@Controller
@RequestMapping("/scheduleList")
public class SchedulesListController {

	private final AvailabilityApplicationService availabilityApplicationService;
	private final ScheduleApplicationService scheduleApplicationService;

	@Autowired
	SchedulesListController(AvailabilityApplicationService availabilityApplicationService, ScheduleApplicationService scheduleApplicationService) {

		Assert.notNull(availabilityApplicationService, "availabilityApplicationService must not be null");
		Assert.notNull(scheduleApplicationService, "scheduleApplicationService must not be null");

		this.availabilityApplicationService = availabilityApplicationService;
		this.scheduleApplicationService = scheduleApplicationService;
	}

	@GetMapping
	String getView(Model model) {

		model.addAttribute("availabilityGroups", availabilityApplicationService.getAvailabilityList());
		model.addAttribute("schedules", scheduleApplicationService.getScheduleList());

		return "schedule-list-view";
	}
}
