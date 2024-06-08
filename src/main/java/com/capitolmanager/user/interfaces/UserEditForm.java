
package com.capitolmanager.user.interfaces;

import com.capitolmanager.user.domain.UserRole;


public class UserEditForm {

	public static final String F_EMAIL = "email";
	public static final String F_FIRST_NAME = "firstName";
	public static final String F_LAST_NAME = "lastName";
	public static final String F_PHONE_NUMBER = "phoneNumber";
	public static final String F_ROLE = "role";


	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private UserRole role;

	public UserEditForm(Long id, String email, String firstName, String lastName, String phoneNumber, UserRole role) {

		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public UserEditForm() {

	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getFirstName() {

		return firstName;
	}

	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	public String getLastName() {

		return lastName;
	}

	public void setLastName(String lastName) {

		this.lastName = lastName;
	}

	public String getPhoneNumber() {

		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {

		this.phoneNumber = phoneNumber;
	}

	public UserRole getRole() {

		return role;
	}

	public void setRole(UserRole role) {

		this.role = role;
	}
}
