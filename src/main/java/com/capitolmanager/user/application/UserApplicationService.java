
package com.capitolmanager.user.application;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.availability.application.AvailabilityInitializer;
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
	private final AvailabilityInitializer availabilityInitializer;


	@Autowired
	UserApplicationService(UserQueries userQueries, Repository<User> userRepository, PasswordEncoder passwordEncoder, AvailabilityInitializer availabilityInitializer) {

		Assert.notNull(userQueries, "userQueries must not be null");
		Assert.notNull(userRepository, "userRepository must not be null");
		Assert.notNull(passwordEncoder, "passwordEncoder must not be null");
		Assert.notNull(availabilityInitializer, "availabilityInitializer must not be null");

		this.userQueries = userQueries;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.availabilityInitializer = availabilityInitializer;
	}

	public List<UserListDto> getAllUsers() {

		var users = userQueries.getAll();

		return users.stream()
			.sorted(Comparator
				.comparing(User::getRole)
				.thenComparing(User::getLastName)
				.thenComparing(User::getFirstName))
			.map(user -> new UserListDto(user.getId(),
				user.getEmail(),
				user.getFirstName(),
				user.getLastName(),
				user.getPhoneNumber(),
				user.getRole().getLabel()))
			.toList();
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

		user = userQueries.findByEmail(user.getEmail())
				.orElseThrow(EntityNotFoundException::new);

		availabilityInitializer.initializeAvailabilityForUser(user);
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

	public List<UserRole> getAllRoles() {

		return Arrays.asList(UserRole.values());
	}

	public void changePassword(PasswordChangeForm form) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userQueries.findByEmail(authentication.getName())
			.orElseThrow(EntityNotFoundException::new);

		if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
			throw new IllegalStateException("Passwords do not match");
		}

		user.setPassword(passwordEncoder.encode(form.getNewPassword()));
		userRepository.saveOrUpdate(user);
	}

	public Long getLoggedUserId() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userQueries.findByEmail(authentication.getName())
			.orElseThrow(() -> new EntityNotFoundException("User " + authentication.getName() + " not found"));

		return user.getId();
	}

	public String getLoggedUserName() {

		return userQueries.get(getLoggedUserId()).getFirstName();
	}

	public boolean isUserManager(Long id) {

		return userQueries.get(id).getRole().equals(UserRole.MANAGER);
	}

	public User getLoggedUser() {

		return userQueries.get(getLoggedUserId());
	}

	public String getUserName(Long userId) {
		return userQueries.get(userId).toString();
	}
}
