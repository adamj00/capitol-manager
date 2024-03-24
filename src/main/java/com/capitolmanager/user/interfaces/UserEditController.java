/*
 * Created on 24-03-2024 15:21 by ajarzabe
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.user.application.UserApplicationService;


@Controller
public class UserEditController {

	private final UserApplicationService userApplicationService;

	@Autowired
	public UserEditController(UserApplicationService userApplicationService) {

		Assert.notNull(userApplicationService, "userApplicationService must not be null");

		this.userApplicationService = userApplicationService;
	}

	@PostMapping()
	String editUser(@ModelAttribute("userEditForm"), @RequestParam Long id) {


	}
}
