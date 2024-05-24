/*
 * Created on 24-05-2024 20:30 by ajarzabe
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

import static com.capitolmanager.show.application.ShowValidator.E_DURATION_MUST_BE_POSITIVE;
import static com.capitolmanager.show.application.ShowValidator.E_SHOW_TITLE_MUST_BE_UNIQUE;
import static com.capitolmanager.show.interfaces.ShowEditForm.F_DURATION;
import static com.capitolmanager.show.interfaces.ShowEditForm.F_STAGE;
import static com.capitolmanager.show.interfaces.ShowEditForm.F_TITLE;
import static com.capitolmanager.user.interfaces.UserValidator.E_MUST_NOT_BE_EMPTY;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import com.capitolmanager.show.interfaces.ShowEditForm;
import com.capitolmanager.stage.application.StageSelectionDto;

public class ShowValidatorTest {

	@InjectMocks
	private ShowValidator showValidator;

	@Mock
	private ShowApplicationService showApplicationService;

	@Mock
	private Errors errors;
	private ShowEditForm form;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		form = new ShowEditForm();
	}

	@Test
	public void shouldRejectEmptyTitle() {
		// Given
		form.setTitle("");

		// When
		showValidator.validate(form, errors);

		// Then
		verify(errors).rejectValue(F_TITLE, E_MUST_NOT_BE_EMPTY);
	}

	@Test
	public void shouldRejectNonUniqueTitle() {
		// Given
		form.setTitle("Unique Show Title");
		form.setId(1L);
		when(showApplicationService.existShowWithTitle(form.getId(), form.getTitle())).thenReturn(true);

		// When
		showValidator.validate(form, errors);

		// Then
		verify(errors).rejectValue(F_TITLE, E_SHOW_TITLE_MUST_BE_UNIQUE);
	}

	@Test
	public void shouldRejectNonPositiveDuration() {
		// Given
		form.setDuration(-1);

		// When
		showValidator.validate(form, errors);

		// Then
		verify(errors).rejectValue(F_DURATION, E_DURATION_MUST_BE_POSITIVE);
	}

	@Test
	public void shouldRejectNullStage() {
		// Given
		form.setStageSelectionDto(null);

		// When
		showValidator.validate(form, errors);

		// Then
		verify(errors).rejectValue(F_STAGE, E_MUST_NOT_BE_EMPTY);
	}

	@Test
	public void shouldPassValidForm() {
		// Given
		form.setTitle("Valid Show Title");
		form.setId(1L);
		form.setDuration(120);
		form.setStageSelectionDto(new StageSelectionDto(1L, "Stage Name"));
		when(showApplicationService.existShowWithTitle(form.getId(), form.getTitle())).thenReturn(false);

		// When
		showValidator.validate(form, errors);

		// Then
		verify(errors, never()).rejectValue(anyString(), anyString());
	}
}