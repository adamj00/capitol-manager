/*
 * Created on 24-03-2024 12:56 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.user.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.user.domain.User;
import com.capitolmanager.user.interfaces.UserDto;
import com.capitolmanager.user.interfaces.UserEditForm;


@Service
public class UserApplicationService {

	private final UserQueries userQueries;
	private final UserRepository userRepository;

	@Autowired
	UserApplicationService(UserQueries userQueries, UserRepository userRepository) {

		Assert.notNull(userQueries, "userQueries must not be null");
		Assert.notNull(userRepository, "userRepository must not be null");

		this.userQueries = userQueries;
		this.userRepository = userRepository;
	}

	public List<UserDto> getAllUsers() {

		var users = userQueries.getAllUsers();

		return users.stream()
			.map(user -> new UserDto(user.getId(),
				user.getEmail(),
				user.getFirstName(),
				user.getLastName(),
				user.getPhoneNumber()))
			.collect(Collectors.toList());
	}

	public void saveUser(UserEditForm form) {

		if (form.getId() == null) {

			User user =
		}
	}

	private User createUserFromForm(UserEditForm form) {

		User user = new User()
	}
}
