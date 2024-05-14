/*
 * Created on 09-05-2024 21:46 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.availability.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.availability.domain.Availability;


public interface AvailabilityQueries {

	List<Availability> getByUserIdAndEventGroup(Long userId, Long eventGroupId);
	Optional<Availability> findById(Long id);

	List<Availability> getAll();
}
