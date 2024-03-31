/*
 * Created on 31-03-2024 14:14 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.position.interfaces;

import static com.capitolmanager.position.interfaces.PositionsForm.F_POSITIONS;
import static com.capitolmanager.utils.StringUtils.hasText;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.capitolmanager.position.application.PositionDto;


@Service
public class PositionsValidator implements Validator {

	private static final String E_POSITION_NAME_MUST_NOT_BE_EMPTY = "positions.name.must.not.be.empty";
	private static final String E_POSITION_NAME_MUST_BE_UNIQUE = "positions.name.must.be.unique";

	@Override
	public boolean supports(Class<?> clazz) {

		Assert.notNull(clazz, "clazz must not be null");

		return clazz.equals(PositionsForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Assert.notNull(target, "target must not be null");
		Assert.notNull(errors, "errors must not be null");

		PositionsForm form = (PositionsForm) target;

		if (anyPositionNameIsEmpty(form)) {

			errors.rejectValue(F_POSITIONS, E_POSITION_NAME_MUST_NOT_BE_EMPTY);
		}

		if (doNamesHaveDuplicates(form)) {

			errors.rejectValue(F_POSITIONS, E_POSITION_NAME_MUST_BE_UNIQUE);
		}
	}

	private boolean anyPositionNameIsEmpty(PositionsForm form) {

		return form.getPositions().stream()
			.anyMatch(positionDto -> !hasText(positionDto.getName()));
	}

	private boolean doNamesHaveDuplicates(PositionsForm form) {

		return form.getPositions().stream()
			.map(PositionDto::getName)
			.distinct()
			.toList().size() != form.getPositions().size();
	}
}
