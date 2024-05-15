/*
 * Created on 14-05-2024 11:45 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.schedule.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.schedule.domain.Schedule;


public interface ScheduleQueries {

	Optional<Schedule> findById(Long id);

	Optional<Schedule> findByEventGroup(Long eventGroupId);

	List<Schedule> getAll();

}
