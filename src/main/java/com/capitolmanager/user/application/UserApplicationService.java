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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.changepassword.interfaces.PasswordChangeForm;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.user.domain.User;
import com.capitolmanager.user.domain.UserRole;
import com.capitolmanager.user.interfaces.UserEditForm;
import com.capitolmanager.user.interfaces.UserListDto;


@Service
public class UserApplicationService {

	private static final String DEFAULT_PASSWORD = "capitol";

	private final UserQueries userQueries;
	private final Repository<User> userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserApplicationService(UserQueries userQueries, Repository<User> userRepository, PasswordEncoder passwordEncoder) {

		this.userQueries = userQueries;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
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
			userEditForm.getPhoneNumber(),
			userEditForm.getRole(),
			passwordEncoder.encode(DEFAULT_PASSWORD));

		userRepository.saveOrUpdate(user);
	}

	public void updateUser(UserEditForm userEditForm) {

		Assert.notNull(userEditForm, "userEditForm must not be null");

		User user = userQueries.findById(userEditForm.getId())
			.orElseThrow(() -> new EntityNotFoundException("Not found User with id: " + userEditForm.getId()));

		user.update(userEditForm.getEmail(),
			userEditForm.getFirstName(),
			userEditForm.getLastName(),
			userEditForm.getPhoneNumber(),
			userEditForm.getRole());

		userRepository.saveOrUpdate(user);
	}

	public void deleteUser(Long id) {

		User user = userQueries.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));

		userRepository.delete(user);
	}

	public List<UserRole> getAllRoles() {

		return Arrays.asList(UserRole.values());
	}

	public void changePassword(PasswordChangeForm form) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userQueries.findByEmail(authentication.getName())
			.orElseThrow(() -> new EntityNotFoundException("User " + authentication.getName() + " not found"));

		if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
			throw new IllegalStateException("Passwords do not match");
		}

		user.setPassword(passwordEncoder.encode(form.getNewPassword()));
		userRepository.saveOrUpdate(user);
	}
}
