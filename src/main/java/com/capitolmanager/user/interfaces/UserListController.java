/*
 * Created on 24-03-2024 12:53 by ajarzabe
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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.capitolmanager.user.application.UserApplicationService;


@Controller
public class UserListController {

	private final UserApplicationService userApplicationService;

	@Autowired
	UserListController(UserApplicationService userApplicationService) {

		Assert.notNull(userApplicationService, "userApplicationService must not be null");

		this.userApplicationService = userApplicationService;
	}

	@GetMapping("/user/list")
	String getUsersView() {

		return "user-list-view";
	}

	@ModelAttribute("users")
	private List<UserDto> getUsers() {

		var users = userApplicationService.getAllUsers();
		return users;
	}
}
