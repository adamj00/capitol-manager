

package com.capitolmanager.user.interfaces;

import static com.capitolmanager.user.interfaces.UserEditController.USER_EDIT_PATH;
import static com.capitolmanager.user.interfaces.UserListController.USER_LIST_PATH;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.user.domain.UserRole;


@Controller
@RequestMapping(USER_EDIT_PATH)
public class UserEditController {

	static final String USER_EDIT_PATH = "/user-edit";
	static final String USER_EDIT_VIEW = "user-edit-view";
	private static final String P_ID = "id";
	private static final String M_USER_FORM = "user";
	private static final String REDIRECT = "redirect:";
	private static final String M_ROLES = "roles";

	private final UserApplicationService userApplicationService;
	private final UserFormFactory userFormFactory;
	private final UserValidator userValidator;


	@Autowired
	UserEditController(UserApplicationService userApplicationService, UserFormFactory userFormFactory, UserValidator userValidator) {

		Assert.notNull(userApplicationService, "userApplicationService must not be null");
		Assert.notNull(userFormFactory, "userFormFactory must not be null");
		Assert.notNull(userValidator, "userValidator must not be null");

		this.userApplicationService = userApplicationService;
		this.userFormFactory = userFormFactory;
		this.userValidator = userValidator;
	}

	@InitBinder
	private void customizeBinding(WebDataBinder binder) {

		binder.setValidator(userValidator);
	}

	@GetMapping
	String getEditView(Model model, @RequestParam(required = false, name = P_ID) Long id) {

		if (id == null) {
			model.addAttribute(M_USER_FORM, UserFormFactory.createEmptyUserForm());
		}

		else {
			model.addAttribute(M_USER_FORM, userFormFactory.getUserEditFormById(id));
		}

		return USER_EDIT_VIEW;
	}

	@PostMapping
	String saveOrUpdateUser(@Validated @ModelAttribute(M_USER_FORM) UserEditForm userForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			return USER_EDIT_VIEW;
		}

		if (userForm.getId() == null) {
			userApplicationService.saveUser(userForm);
		}
		else {
			userApplicationService.updateUser(userForm);
		}

		return REDIRECT + USER_LIST_PATH;
	}

	@ModelAttribute(M_ROLES)
	List<UserRole> allRoles() {

		return userApplicationService.getAllRoles();
	}
}
