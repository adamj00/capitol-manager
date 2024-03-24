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

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.user.domain.User;
import com.capitolmanager.user.interfaces.UserEditForm;
import com.capitolmanager.user.interfaces.UserListDto;


@Service
public class UserApplicationService {

	private final UserQueries userQueries;
	private final Repository<User> userRepository;

	@Autowired
	UserApplicationService(UserQueries userQueries, Repository<User> userRepository) {

		Assert.notNull(userQueries, "userQueries must not be null");
		Assert.notNull(userRepository, "userRepository must not be null");

		this.userQueries = userQueries;
		this.userRepository = userRepository;
	}

	public List<UserListDto> getAllUsers() {

		var users = userQueries.getAll();

		return users.stream()
			.map(user -> new UserListDto(user.getId(),
				user.getEmail(),
				user.getFirstName(),
				user.getLastName(),
				user.getPhoneNumber()))
			.collect(Collectors.toList());
	}

	public void saveUser(UserEditForm userEditForm) {

		Assert.notNull(userEditForm, "userEditForm must not be null");

		User user = new User(userEditForm.getEmail(),
			userEditForm.getFirstName(),
			userEditForm.getLastName(),
			userEditForm.getPhoneNumber());

		userRepository.saveOrUpdate(user);
	}

	public void updateUser(UserEditForm userEditForm) {

		Assert.notNull(userEditForm, "userEditForm must not be null");

		User user = userQueries.findById(userEditForm.getId())
			.orElseThrow(() -> new EntityNotFoundException("Not found User with id: " + userEditForm.getId()));

		user.update(userEditForm.getEmail(),
			userEditForm.getFirstName(),
			userEditForm.getLastName(),
			userEditForm.getPhoneNumber());

		userRepository.saveOrUpdate(user);
	}
}
