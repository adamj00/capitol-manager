/*
 * Created on 11-05-2024 21:56 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.event.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.event.domain.EventGroup;


public interface EventGroupQueries {

	Optional<EventGroup> findById(Long id);

	List<EventGroup> getAll();

	EventGroup get(Long id);
}
