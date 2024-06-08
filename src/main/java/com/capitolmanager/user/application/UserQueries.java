
package com.capitolmanager.user.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.user.domain.User;


public interface UserQueries {

	List<User> getAll();

	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);

	User get(Long id);
}
