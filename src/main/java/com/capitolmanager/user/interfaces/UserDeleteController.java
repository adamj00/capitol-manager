

package com.capitolmanager.user.interfaces;

import static com.capitolmanager.user.interfaces.UserListController.USER_LIST_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.user.application.UserDeleter;


@Controller
@RequestMapping("/deleteUser")
public class UserDeleteController {

	private final UserDeleter userDeleter;


	@Autowired
	UserDeleteController(UserDeleter userDeleter) {

		Assert.notNull(userDeleter, "userDeleter must not be null");

		this.userDeleter = userDeleter;
	}

	@GetMapping
	String deleteUser(@RequestParam Long id) {

		userDeleter.deleteUser(id);

		return "redirect:" + USER_LIST_PATH;
	}
}
