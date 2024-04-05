/*
 * Created on 26-03-2024 21:25 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.position.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.position.domain.Position;


public interface PositionQueries {

	List<Position> getAll();

	Optional<Position> findById(Long id);
}
