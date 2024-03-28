/*
 * Created on 28-03-2024 12:20 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.user.interfaces;

import static com.capitolmanager.user.interfaces.UserListController.USER_LIST_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.user.application.UserApplicationService;


@Controller
@RequestMapping("/deleteUser")
public class UserDeleteController {

	private final UserApplicationService userApplicationService;


	@Autowired
	UserDeleteController(UserApplicationService userApplicationService) {

		Assert.notNull(userApplicationService, "userApplicationService must not be null");

		this.userApplicationService = userApplicationService;
	}


	@GetMapping
	String deleteUser(@RequestParam Long id) {

		userApplicationService.deleteUser(id);

		return "redirect:" + USER_LIST_PATH;
	}
}
