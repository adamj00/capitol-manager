
package com.capitolmanager.availability.application;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

	private final UserQueries userQueries;
	private final UserApplicationService userApplicationService;
	private final Repository<Availability> availabilityRepository;
	private final AvailabilityQueries availabilityQueries;
	private final EventGroupQueries eventGroupQueries;

	@Autowired
	AvailabilityApplicationService(UserQueries userQueries,
		UserApplicationService userApplicationService,
		Repository<Availability> availabilityRepository,
		AvailabilityQueries availabilityQueries,
		EventGroupQueries eventGroupQueries) {

		Assert.notNull(userQueries, "userQueries must not be null");
		Assert.notNull(userApplicationService, "userApplicationService must not be null");
		Assert.notNull(availabilityRepository, "availabilityRepository must not be null");
		Assert.notNull(availabilityQueries, "availabilityQueries must not be null");
		Assert.notNull(eventGroupQueries, "eventGroupQueries must not be null");

		this.userQueries = userQueries;
		this.userApplicationService = userApplicationService;
		this.availabilityRepository = availabilityRepository;
		this.availabilityQueries = availabilityQueries;
		this.eventGroupQueries = eventGroupQueries;
	}

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
						dayWithAvailabilities.setDate(current);
					}
					else {
						dayWithAvailabilities.setDate(current);
						dayWithAvailabilities.setAvailabilities(getAvailabilitiesForDay(current, availabilities));
					}
				}
				else {
					dayWithAvailabilities.setDate(current);
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

	List<AvailabilityDto> getAvailabilitiesForDay(LocalDate day, List<Availability> availabilities) {

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
