/*
 * Created on 13-04-2024 12:46 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.changepassword.application;

import static com.capitolmanager.changepassword.interfaces.PasswordChangeForm.F_NEW_PASSWORD;
import static com.capitolmanager.changepassword.interfaces.PasswordChangeForm.F_OLD_PASSWORD;
import static com.capitolmanager.utils.StringUtils.hasText;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.capitolmanager.changepassword.interfaces.PasswordChangeForm;
import com.capitolmanager.user.application.UserQueries;
import com.capitolmanager.user.domain.User;


@Service
public class PasswordChangeValidator implements Validator {

	private static final String E_MUST_NOT_BE_EMPTY = "must.not.be.empty";
	private static final String E_WRONG_PASSWORD = "password.change.wrong.old.password";

	private final UserQueries userQueries;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	PasswordChangeValidator(UserQueries userQueries, PasswordEncoder passwordEncoder) {

		Assert.notNull(userQueries, "userQueries must not be null");
		Assert.notNull(passwordEncoder, "passwordEncoder must not be null");

		this.userQueries = userQueries;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public boolean supports(Class<?> clazz) {

		return clazz.equals(PasswordChangeForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		PasswordChangeForm form = (PasswordChangeForm) target;

		if (!hasText(form.getOldPassword())) {

			errors.rejectValue(F_OLD_PASSWORD, E_MUST_NOT_BE_EMPTY);
		}

		else {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = userQueries.findByEmail(authentication.getName())
				.orElseThrow(() -> new EntityNotFoundException("User " + authentication.getName() + " not found"));

			if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {

				errors.rejectValue(F_OLD_PASSWORD, E_WRONG_PASSWORD);
			}
		}

		if (!hasText(form.getNewPassword())) {

			errors.rejectValue(F_NEW_PASSWORD, E_MUST_NOT_BE_EMPTY);
		}
	}
}
