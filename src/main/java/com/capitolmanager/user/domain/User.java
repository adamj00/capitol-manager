
package com.capitolmanager.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.util.Assert;

import com.capitolmanager.hibernate.AbstractEntity;

@Entity(name="users")
public class User extends AbstractEntity {

	@Column
	private String email;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String phoneNumber;

	@Column
	private String password;

	@Column
	@Enumerated(EnumType.STRING)
	private UserRole role;

	public User(String email, String firstName, String lastName, String phoneNumber, UserRole role, String password) {

		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.password = password;
	}

	public User() {

	}
	
	public void update(String email, String firstName, String lastName, String phoneNumber, UserRole role) {

		Assert.notNull(email, "email must not be null");
		
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.role = role;
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

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public UserRole getRole() {

		return role;
	}

	public void setRole(UserRole role) {

		this.role = role;
	}

	@Override
	public String toString() {

		return firstName + " " + lastName;
	}
}
