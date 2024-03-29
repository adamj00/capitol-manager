/*
 * Created on 29-03-2024 20:02 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.show.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.show.domain.Show;


public interface ShowQueries  {

	List<Show> getAll();

	Optional<Show> findById(Long id);
}
