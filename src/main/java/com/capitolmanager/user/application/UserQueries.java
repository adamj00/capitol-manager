/*
 * Created on 24-03-2024 13:02 by ajarzabe
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
import java.util.Optional;

import com.capitolmanager.user.domain.User;


public interface UserQueries {

	List<User> getAll();

	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);

	User get(Long id);
}
