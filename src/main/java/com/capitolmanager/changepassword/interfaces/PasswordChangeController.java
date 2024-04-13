/*
 * Created on 13-04-2024 11:09 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.changepassword.interfaces;

import static com.capitolmanager.changepassword.interfaces.PasswordChangeController.CHANGE_PASSWORD_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capitolmanager.changepassword.application.PasswordChangeValidator;
import com.capitolmanager.user.application.UserApplicationService;


@Controller
@RequestMapping(CHANGE_PASSWORD_PATH)
public class PasswordChangeController {

	static final String CHANGE_PASSWORD_PATH = "/changePassword";
	private static final String VIEW_NAME = "changePassword-view";
	private static final String M_FORM = "passwordForm";

	private final UserApplicationService userApplicationService;
	private final PasswordChangeValidator passwordChangeValidator;


	@Autowired
	PasswordChangeController(UserApplicationService userApplicationService, PasswordChangeValidator passwordChangeValidator) {

		Assert.notNull(userApplicationService, "userApplicationService must not be null");
		Assert.notNull(passwordChangeValidator, "passwordChangeValidator must not be null");

		this.userApplicationService = userApplicationService;
		this.passwordChangeValidator = passwordChangeValidator;
	}

	@InitBinder
	void initBinder(WebDataBinder binder) {

		binder.setValidator(passwordChangeValidator);
	}

	@GetMapping
	String getView(Model model) {

		model.addAttribute(M_FORM, new PasswordChangeForm());

		return VIEW_NAME;
	}

	@PostMapping
	String changePassword(@Validated @ModelAttribute(M_FORM) PasswordChangeForm form, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			return VIEW_NAME;
		}

		userApplicationService.changePassword(form);

		return "redirect:/";
	}
}
