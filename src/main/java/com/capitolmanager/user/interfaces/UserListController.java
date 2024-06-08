

package com.capitolmanager.user.interfaces;

import static com.capitolmanager.user.interfaces.UserListController.USER_LIST_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capitolmanager.user.application.UserApplicationService;


@Controller
@RequestMapping(USER_LIST_PATH)
public class UserListController {

	static final String USER_LIST_PATH = "/user-list";
	private static final String USER_LIST_VIEW = "user-list-view";
	private static final String M_USERS = "users";

	private final UserApplicationService userApplicationService;

	@Autowired
	UserListController(UserApplicationService userApplicationService) {

		Assert.notNull(userApplicationService, "userApplicationService must not be null");

		this.userApplicationService = userApplicationService;
	}

	@GetMapping
	String getUsersView() {

		return USER_LIST_VIEW;
	}

	@ModelAttribute(M_USERS)
	private List<UserListDto> getUsers() {

		return userApplicationService.getAllUsers();
	}
}
