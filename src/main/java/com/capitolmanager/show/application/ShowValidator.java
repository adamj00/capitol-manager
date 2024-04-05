/*
 * Created on 05-04-2024 13:17 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.show.application;

import static com.capitolmanager.show.interfaces.ShowEditForm.F_DURATION;
import static com.capitolmanager.show.interfaces.ShowEditForm.F_STAGE;
import static com.capitolmanager.show.interfaces.ShowEditForm.F_TITLE;
import static com.capitolmanager.utils.StringUtils.hasText;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.capitolmanager.show.interfaces.ShowEditForm;


@Service
public class ShowValidator implements Validator {

	private static final String E_MUST_NOT_BE_EMPTY = "must.not.be.empty";
	private static final String E_SHOW_TITLE_MUST_BE_UNIQUE = "show.title.must.be.unique";
	private static final String E_DURATION_MUST_BE_POSITIVE = "show.duration.must.be.positive";

	private final ShowApplicationService showApplicationService;


	@Autowired
	ShowValidator(ShowApplicationService showApplicationService) {

		Assert.notNull(showApplicationService, "showApplicationService must not be null");

		this.showApplicationService = showApplicationService;
	}

	@Override
	public boolean supports(Class<?> clazz) {

		Assert.notNull(clazz, "clazz must not be null");

		return clazz.equals(ShowEditForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Assert.notNull(target, "target must not be null");
		Assert.notNull(errors, "errors must not be null");

		ShowEditForm form = (ShowEditForm) target;

		if (!hasText(form.getTitle())) {

			errors.rejectValue(F_TITLE, E_MUST_NOT_BE_EMPTY);
		}
		else if (showApplicationService.existShowWithTitle(form.getId(), form.getTitle())) {

			errors.rejectValue(F_TITLE, E_SHOW_TITLE_MUST_BE_UNIQUE);
		}

		if (form.getDuration() <= 0) {

			errors.rejectValue(F_DURATION, E_DURATION_MUST_BE_POSITIVE);
		}

		if (form.getStageSelectionDto() == null) {

			errors.rejectValue(F_STAGE, E_MUST_NOT_BE_EMPTY);
		}
	}
}
