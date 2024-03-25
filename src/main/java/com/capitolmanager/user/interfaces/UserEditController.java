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

import static com.capitolmanager.user.interfaces.UserEditController.USER_EDIT_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.user.application.UserApplicationService;


@Controller
@RequestMapping(USER_EDIT_PATH)
public class UserEditController {

	static final String USER_EDIT_PATH = "user/edit";
	static final String USER_EDIT_VIEW = "user-edit-view";
	private static final String P_ID = "id";
	private static final String M_USER_FORM = "user";
	private static final String REDIRECT = "redirect:";

	private final UserApplicationService userApplicationService;
	private final UserFormFactory userFormFactory;

	@Autowired
	UserEditController(UserApplicationService userApplicationService, UserFormFactory userFormFactory) {

		Assert.notNull(userApplicationService, "userApplicationService must not be null");
		Assert.notNull(userFormFactory, "userFormFactory must not be null");

		this.userApplicationService = userApplicationService;
		this.userFormFactory = userFormFactory;
	}

	@GetMapping
	String getEditView(Model model, @RequestParam(required = false, name = P_ID) Long id) {

		if (id == null) {
			model.addAttribute(M_USER_FORM, UserFormFactory.createEmptyUserForm());
		}

		else {
			model.addAttribute(M_USER_FORM, userFormFactory.getUserEditFormById(id));
		}

		return USER_EDIT_VIEW;
	}

	@PostMapping
	String saveOrUpdateUser(@ModelAttribute(M_USER_FORM) UserEditForm userForm, Model model) {

		if (userForm.getId() == null) {
			userApplicationService.saveUser(userForm);
		}
		else {
			userApplicationService.updateUser(userForm);
		}

		return REDIRECT + USER_EDIT_PATH;
	}
}
