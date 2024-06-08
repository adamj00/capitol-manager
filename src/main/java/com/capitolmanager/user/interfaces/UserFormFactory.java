
package com.capitolmanager.user.interfaces;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.user.application.UserQueries;
import com.capitolmanager.user.domain.User;


@Service
public class UserFormFactory {

	private final UserQueries userQueries;

	@Autowired
	UserFormFactory(UserQueries userQueries) {

		Assert.notNull(userQueries, "userQueries must not be null");

		this.userQueries = userQueries;
	}

	public UserEditForm getUserEditFormById(Long id) {

		Assert.notNull(id, "id must not be null");

		User user = userQueries.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Not found User with id: " + id));

		return createUserFormFromEntity(user);
	}

	public static UserEditForm createEmptyUserForm() {

		return new UserEditForm();
	}

	private static UserEditForm createUserFormFromEntity(User user) {

		return new UserEditForm(user.getId(),
			user.getEmail(),
			user.getFirstName(),
			user.getLastName(),
			user.getPhoneNumber(),
			user.getRole());
	}
}
