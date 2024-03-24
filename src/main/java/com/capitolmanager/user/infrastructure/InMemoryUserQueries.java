/*
 * Created on 24-03-2024 13:22 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.user.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.capitolmanager.user.application.UserQueries;
import com.capitolmanager.user.domain.User;


@Service
public class InMemoryUserQueries implements UserQueries {



	@Override
	public List<User> getAllUsers() {

		return List.of(new User(1L, "adam@gmail.com", "Adam", "Jarząbek", "+48123123123"),
			new User(1L, "kasia@gmail.com", "Kasia", "Paterek", "+48123123123"),
			new User(1L, "emilia@gmail.com", "Emilia", "Golec", "+48123123123"));
	}

	@Override
	public Optional<User> findUser(Long id) {

		return Optional.empty();
	}

}
