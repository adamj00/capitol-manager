/*
 * Created on 30-03-2024 23:52 by ajarzabe
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

import static com.capitolmanager.user.interfaces.UserEditForm.F_EMAIL;
import static com.capitolmanager.user.interfaces.UserEditForm.F_FIRST_NAME;
import static com.capitolmanager.user.interfaces.UserEditForm.F_LAST_NAME;
import static com.capitolmanager.user.interfaces.UserEditForm.F_PHONE_NUMBER;
import static com.capitolmanager.utils.StringUtils.hasText;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Service
public class UserValidator implements Validator {

	public static final String E_MUST_NOT_BE_EMPTY = "must.not.be.empty";
	public static final String E_INVALID_PHONE_NUMBER = "invalid.phone.number";

	@Override
	public boolean supports(Class<?> clazz) {

		Assert.notNull(clazz, "clazz must not be null");

		return clazz.equals(UserEditForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Assert.notNull(target, "target must not be null");
		Assert.notNull(errors, "errors must not be null");

		UserEditForm form = (UserEditForm) target;

		if (!hasText(form.getEmail())) {

			errors.rejectValue(F_EMAIL, E_MUST_NOT_BE_EMPTY);
		}

		if (!hasText(form.getFirstName())) {

			errors.rejectValue(F_FIRST_NAME, E_MUST_NOT_BE_EMPTY);
		}

		if (!hasText(form.getLastName())) {

			errors.rejectValue(F_LAST_NAME, E_MUST_NOT_BE_EMPTY);
		}

		if (!hasText(form.getPhoneNumber())) {

			errors.rejectValue(F_PHONE_NUMBER, E_MUST_NOT_BE_EMPTY);
		}
		else if (form.getPhoneNumber().length() < 9) {

			errors.rejectValue(F_PHONE_NUMBER, E_INVALID_PHONE_NUMBER);
		}
	}
}
