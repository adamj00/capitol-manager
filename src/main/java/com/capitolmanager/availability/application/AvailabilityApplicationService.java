/*
 * Created on 09-05-2024 21:52 by ajarzabe
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

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitolmanager.availability.domain.Availability;
import com.capitolmanager.event.application.EventGroupQueries;
import com.capitolmanager.event.application.EventQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.show.domain.Show;
import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.user.application.UserQueries;
import com.capitolmanager.user.domain.User;
import com.capitolmanager.utils.StringUtils;


@Service
public class AvailabilityApplicationService {

	@Autowired private UserQueries userQueries;
	@Autowired private UserApplicationService userApplicationService;
	@Autowired private Repository<Availability> availabilityRepository;
	@Autowired private AvailabilityQueries availabilityQueries;
	@Autowired private EventGroupQueries eventGroupQueries;
	@Autowired private EventQueries eventQueries;

	public void initializeAvailabilities(Event event) {

		List<User> allUsers = userQueries.getAll();

		for (User user : allUsers) {

			Availability availability = new Availability(event,
				user,
				null);

			availabilityRepository.saveOrUpdate(availability);
		}
	}

	public List<List<DayWithAvailabilities>> getMonthWeeksWithAvailabilities(Long eventGroupId) {

		List<Availability> availabilities = getAvailabilitiesForLoggedUser(eventGroupId);

		List<List<DayWithAvailabilities>> weeks = new ArrayList<>();

		if (availabilities.isEmpty()) {

			return List.of();
		}

		LocalDate firstOfMonth = availabilities.get(0).getEvent().getEventStartTime().toLocalDate();
		LocalDate lastOfMonth = availabilities.get(availabilities.size() - 1).getEvent().getEventStartTime().toLocalDate();
		LocalDate current = firstOfMonth.with(ChronoField.DAY_OF_WEEK, 1);

		while (!current.isAfter(lastOfMonth)) {
			List<DayWithAvailabilities> week = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				DayWithAvailabilities dayWithAvailabilities = new DayWithAvailabilities();
				if (!current.isAfter(lastOfMonth)) {
					if (current.isBefore(firstOfMonth)) {
						dayWithAvailabilities.setDate(null);
					}
					else {
						dayWithAvailabilities.setDate(current);
						dayWithAvailabilities.setAvailabilities(getAvailabilitiesForDay(current, availabilities));
					}
				}
				else {
					dayWithAvailabilities.setDate(null);
				}
				week.add(dayWithAvailabilities);
				current = current.plusDays(1);
			}
			weeks.add(week);
		}

		return weeks;
	}

	public void updateAvailability(Long availabilityId, boolean value) {

		Availability availability = availabilityQueries.findById(availabilityId)
			.orElseThrow(EntityNotFoundException::new);

		availability.setAvailable(value);

		availabilityRepository.saveOrUpdate(availability);
	}

	public void deleteAvailabilitiesForEvent(Long eventId) {

		var availabilities = availabilityQueries.getAll().stream()
			.filter(availability -> availability.getEvent().getId().equals(eventId))
			.toList();

		for (Availability availability : availabilities) {
			availabilityRepository.delete(availability);
		}
	}

	public String getAvailabilityPageTitle(Long eventGroupId) {

		return eventGroupQueries.findById(eventGroupId)
			.orElseThrow(EntityNotFoundException::new)
			.getName();
	}

	public List<AvailabilityListDto> getAvailabilityList() {


		List<EventGroup> eventGroups = eventGroupQueries.getAll();

		return eventGroups.stream()
			.filter(EventGroup::isAvailabilityActive)
			.map(eventGroup -> new AvailabilityListDto(eventGroup.getId(),
				eventGroup.getName(),
				getAvailabilityRate(eventGroup.getId()),
				getAvailabilityFulfilmentString(eventGroup.getId()),
				eventGroup.isAvailabilityActive()))
			.toList();
	}

	public String getAvailabilityFulfilmentString(Long eventGroupId) {

		var availabilities = getAvailabilitiesForLoggedUser(eventGroupId);

		long all = availabilities.size();
		long done = availabilities.stream()
			.filter(availability -> availability.getAvailable() != null)
			.count();

		return done + "/" + all;
	}

	public long getAvailabilityRate(Long eventGroupId) {

		var availabilities = getAvailabilitiesForLoggedUser(eventGroupId);

		long all = availabilities.size();

		if (all == 0) {

			return 100;
		}
		long done = availabilities.stream()
			.filter(availability -> availability.getAvailable() != null && availability.getAvailable())
			.count();

		return (done * 100) / all;
	}

	public boolean isAvailabilityActive(Long eventGroupId) {

		return eventGroupQueries.findById(eventGroupId)
			.orElseThrow(EntityNotFoundException::new)
			.isAvailabilityActive();
	}

	private List<AvailabilityDto> getAvailabilitiesForDay(LocalDate day, List<Availability> availabilities) {

		return availabilities.stream()
			.filter(availability -> availability.getEvent().getEventStartTime().isEqual(day.atTime(availability.getEvent().getEventStartTime().toLocalTime())))
			.map(this::mapAvailabilityToDto)
			.toList();
	}

	private AvailabilityDto mapAvailabilityToDto(Availability availability) {

		Event event = availability.getEvent();
		Show show = availability.getEvent().getShow();

		return new AvailabilityDto(availability.getId(),
			event.getId(),
			show.getTitle(),
			event.getEventStartTime(),
			StringUtils.getDurationString(show.getDuration()),
			show.getStage().getName(),
			availability.getAvailable());
	}

	private List<Availability> getAvailabilitiesForLoggedUser(Long eventGroupId) {

		Long userId = userApplicationService.getLoggedUserId();

		return availabilityQueries.getByUserIdAndEventGroup(userId, eventGroupId).stream()
			.sorted(Comparator.comparing(availability -> availability.getEvent().getEventStartTime()))
			.toList();
	}
}
