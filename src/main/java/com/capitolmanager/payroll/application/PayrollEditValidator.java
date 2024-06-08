

package com.capitolmanager.payroll.application;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.capitolmanager.payroll.interfaces.PayrollEditForm;


@Service
public class PayrollEditValidator implements Validator {

	private static final String END_TIME_MUST_BE_AFTER_START_TIME = "show.end.time.must.be.past.start.time";

	@Override
	public boolean supports(Class<?> clazz) {

		return clazz.equals(PayrollEditForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		PayrollEditForm form = (PayrollEditForm) target;

		if (form.getStartHour() > form.getEndHour() || (form.getStartHour() == form.getEndHour() && form.getStartMinute() >= form.getEndMinute())) {
			errors.rejectValue("endHour", END_TIME_MUST_BE_AFTER_START_TIME);
			errors.rejectValue("endMinute", END_TIME_MUST_BE_AFTER_START_TIME);
		}
	}
}
