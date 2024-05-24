package com.capitolmanager.user.interfaces;

import static com.capitolmanager.user.interfaces.UserEditForm.F_EMAIL;
import static com.capitolmanager.user.interfaces.UserEditForm.F_FIRST_NAME;
import static com.capitolmanager.user.interfaces.UserEditForm.F_LAST_NAME;
import static com.capitolmanager.user.interfaces.UserEditForm.F_PHONE_NUMBER;
import static com.capitolmanager.user.interfaces.UserValidator.E_INVALID_PHONE_NUMBER;
import static com.capitolmanager.user.interfaces.UserValidator.E_MUST_NOT_BE_EMPTY;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

public class UserValidatorTest {

	@InjectMocks
	private UserValidator userValidator;

	@Mock
	private Errors errors;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void shouldRejectEmptyEmail() {
		// Given
		UserEditForm form = new UserEditForm();
		form.setEmail("");

		// When
		userValidator.validate(form, errors);

		// Then
		verify(errors).rejectValue(F_EMAIL, E_MUST_NOT_BE_EMPTY);
	}

	@Test
	public void shouldRejectEmptyFirstName() {
		// Given
		UserEditForm form = new UserEditForm();
		form.setFirstName("");

		// When
		userValidator.validate(form, errors);

		// Then
		verify(errors).rejectValue(F_FIRST_NAME, E_MUST_NOT_BE_EMPTY);
	}

	@Test
	public void shouldRejectEmptyLastName() {
		// Given
		UserEditForm form = new UserEditForm();
		form.setLastName("");

		// When
		userValidator.validate(form, errors);

		// Then
		verify(errors).rejectValue(F_LAST_NAME, E_MUST_NOT_BE_EMPTY);
	}

	@Test
	public void shouldRejectEmptyPhoneNumber() {
		// Given
		UserEditForm form = new UserEditForm();
		form.setPhoneNumber("");

		// When
		userValidator.validate(form, errors);

		// Then
		verify(errors).rejectValue(F_PHONE_NUMBER, E_MUST_NOT_BE_EMPTY);
	}

	@Test
	public void shouldRejectInvalidPhoneNumber() {
		// Given
		UserEditForm form = new UserEditForm();
		form.setPhoneNumber("123");

		// When
		userValidator.validate(form, errors);

		// Then
		verify(errors).rejectValue(F_PHONE_NUMBER, E_INVALID_PHONE_NUMBER);
	}

	@Test
	public void shouldPassValidForm() {
		// Given
		UserEditForm form = new UserEditForm();
		form.setEmail("test@example.com");
		form.setFirstName("John");
		form.setLastName("Doe");
		form.setPhoneNumber("123456789");

		// When
		userValidator.validate(form, errors);

		// Then
		verify(errors, never()).rejectValue(anyString(), anyString());
	}
}