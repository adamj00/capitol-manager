/*
 * Created on 24-05-2024 20:23 by ajarzabe
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
import static com.capitolmanager.position.interfaces.PositionsValidator.E_POSITION_NAME_MUST_BE_UNIQUE;
import static com.capitolmanager.position.interfaces.PositionsValidator.E_POSITION_NAME_MUST_NOT_BE_EMPTY;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import com.capitolmanager.position.application.PositionDto;
import com.capitolmanager.position.domain.PositionType;


public class PositionsValidatorTest {

	@InjectMocks
	private PositionsValidator positionsValidator;

	@Mock
	private Errors errors;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void shouldRejectWhenPositionNameIsEmpty() {
		// Given
		PositionDto emptyNamePosition = new PositionDto(1L, "", PositionType.OTHER);
		PositionsForm form = new PositionsForm(List.of(emptyNamePosition));

		// When
		positionsValidator.validate(form, errors);

		// Then
		verify(errors).rejectValue(F_POSITIONS, E_POSITION_NAME_MUST_NOT_BE_EMPTY);
	}

	@Test
	public void shouldRejectWhenPositionNamesAreNotUnique() {
		// Given
		PositionDto position1 = new PositionDto(1L, "PositionName", PositionType.OTHER);
		PositionDto position2 = new PositionDto(2L, "PositionName", PositionType.OTHER); // Duplicate name
		PositionsForm form = new PositionsForm(Arrays.asList(position1, position2));

		// When
		positionsValidator.validate(form, errors);

		// Then
		verify(errors).rejectValue(F_POSITIONS, E_POSITION_NAME_MUST_BE_UNIQUE);
	}

	@Test
	public void shouldNotRejectWhenAllPositionNamesAreUniqueAndNotEmpty() {
		// Given
		PositionDto position1 = new PositionDto(1L, "PositionName1", PositionType.OTHER);
		PositionDto position2 = new PositionDto(2L, "PositionName2", PositionType.OTHER);
		PositionsForm form = new PositionsForm(Arrays.asList(position1, position2));

		// When
		positionsValidator.validate(form, errors);

		// Then
		verify(errors, never()).rejectValue(anyString(), anyString());
	}
}