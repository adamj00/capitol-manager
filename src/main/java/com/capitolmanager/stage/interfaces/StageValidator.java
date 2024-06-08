
package com.capitolmanager.stage.interfaces;

import static com.capitolmanager.stage.interfaces.StageEditForm.F_NAME;
import static com.capitolmanager.utils.StringUtils.hasText;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.capitolmanager.stage.application.StageApplicationService;


@Service
public class StageValidator implements Validator {

	private static final String E_MUST_NOT_BE_EMPTY = "must.not.be.empty";
	private static final String E_STAGE_NAME_MUST_BE_UNIQUE = "stage.name.must.be.unique";

	private final StageApplicationService stageApplicationService;


	@Autowired
	StageValidator(StageApplicationService stageApplicationService) {

		Assert.notNull(stageApplicationService, "stageApplicationService must not be null");

		this.stageApplicationService = stageApplicationService;
	}

	@Override
	public boolean supports(Class<?> clazz) {

		Assert.notNull(clazz, "clazz must not be null");

		return clazz.equals(StageEditForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {

		Assert.notNull(target, "target must not be null");
		Assert.notNull(errors, "errors must not be null");

		StageEditForm form = (StageEditForm) target;

		if (!hasText(form.getName())) {

			errors.rejectValue(F_NAME, E_MUST_NOT_BE_EMPTY);
		}
		else if (stageApplicationService.existStageWithName(form.getId(), form.getName())) {

			errors.rejectValue(F_NAME, E_STAGE_NAME_MUST_BE_UNIQUE);
		}

	}

}
