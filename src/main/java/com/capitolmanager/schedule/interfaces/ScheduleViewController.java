/*
 * Created on 22-05-2024 19:33 by ajarzabe
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

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.schedule.application.ScheduleViewService;


@Controller
@RequestMapping("/schedule")
public class ScheduleViewController {

	private final ScheduleViewService scheduleViewService;

	@Autowired
	ScheduleViewController(ScheduleViewService scheduleViewService) {

		Assert.notNull(scheduleViewService, "scheduleViewService must not be null");

		this.scheduleViewService = scheduleViewService;
	}

	@GetMapping
	String getView(Model model,
		@RequestParam Long scheduleId,
		@RequestParam(required = false, defaultValue = "false") boolean showOnlyUsers) {

		model.addAttribute("weeks", scheduleViewService.getEventsForScheduleViewByWeeks(scheduleId, showOnlyUsers));
		model.addAttribute("title", scheduleViewService.getTitle(scheduleId));
		model.addAttribute("formatter", DateTimeFormatter.ofPattern("HH:mm"));

		return "schedule-view";
	}
}
