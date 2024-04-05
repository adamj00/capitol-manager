/*
 * Created on 30-03-2024 22:29 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.show.interfaces;

import static com.capitolmanager.show.interfaces.ShowListController.SHOW_LIST_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.show.application.ShowApplicationService;


@Controller
@RequestMapping("/deleteShow")
public class ShowDeleteController {

	private final ShowApplicationService showApplicationService;


	@Autowired
	ShowDeleteController(ShowApplicationService showApplicationService) {

		Assert.notNull(showApplicationService, "showApplicationService must not be null");

		this.showApplicationService = showApplicationService;
	}


	@GetMapping
	String deleteShow(@RequestParam Long id) {

		showApplicationService.deleteShow(id);

		return "redirect:" + SHOW_LIST_PATH;
	}
}