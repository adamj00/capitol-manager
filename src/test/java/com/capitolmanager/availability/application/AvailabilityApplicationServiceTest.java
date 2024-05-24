
package com.capitolmanager.availability.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capitolmanager.availability.domain.Availability;
import com.capitolmanager.event.application.EventGroupQueries;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.show.domain.Show;
import com.capitolmanager.stage.domain.Stage;
import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.user.application.UserQueries;
import com.capitolmanager.user.domain.User;

public class AvailabilityApplicationServiceTest {

	@InjectMocks
	private AvailabilityApplicationService availabilityApplicationService;

	@Mock
	private UserQueries userQueries;

	@Mock
	private UserApplicationService userApplicationService;

	@Mock
	private Repository<Availability> availabilityRepository;

	@Mock
	private EventGroupQueries eventGroupQueries;

	@Mock
	private AvailabilityQueries availabilityQueries;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void shouldInitializeAvailabilitiesForEvent() {
		// Given
		Event event = mock(Event.class);
		User user1 = mock(User.class);
		User user2 = mock(User.class);
		when(userQueries.getAll()).thenReturn(Arrays.asList(user1, user2));

		// When
		availabilityApplicationService.initializeAvailabilities(event);

		// Then
		verify(availabilityRepository, times(2)).saveOrUpdate(any(Availability.class));
	}

	@Test
	public void shouldUpdateAvailability() {
		// Given
		Long availabilityId = 1L;
		boolean value = true;
		Availability availability = mock(Availability.class);
		when(availabilityQueries.findById(availabilityId)).thenReturn(Optional.of(availability));

		// When
		availabilityApplicationService.updateAvailability(availabilityId, value);

		// Then
		verify(availability).setAvailable(value);
		verify(availabilityRepository).saveOrUpdate(availability);
	}

	@Test
	public void shouldThrowExceptionWhenAvailabilityNotFound() {
		// Given
		Long availabilityId = 1L;
		when(availabilityQueries.findById(availabilityId)).thenReturn(Optional.empty());

		// Then
		assertThrows(EntityNotFoundException.class, () -> {
			// When
			availabilityApplicationService.updateAvailability(availabilityId, true);
		});
	}

	@Test
	public void shouldDeleteAvailabilitiesForEvent() {
		// Given
		Long eventId = 1L;
		Availability availability = mock(Availability.class);
		Event event = mock(Event.class);
		when(event.getId()).thenReturn(eventId);
		when(availability.getEvent()).thenReturn(event);
		when(availabilityQueries.getAll()).thenReturn(Arrays.asList(availability));

		// When
		availabilityApplicationService.deleteAvailabilitiesForEvent(eventId);

		// Then
		verify(availabilityRepository).delete(availability);
	}

	@Test
	public void shouldGetAvailabilityPageTitle() {
		// Given
		Long eventGroupId = 1L;
		EventGroup eventGroup = mock(EventGroup.class);
		when(eventGroup.getName()).thenReturn("Event Group Name");
		when(eventGroupQueries.findById(eventGroupId)).thenReturn(Optional.of(eventGroup));

		// When
		String pageTitle = availabilityApplicationService.getAvailabilityPageTitle(eventGroupId);

		// Then
		assertEquals("Event Group Name", pageTitle);
	}

	@Test
	public void shouldThrowExceptionWhenEventGroupNotFoundForPageTitle() {
		// Given
		Long eventGroupId = 1L;
		when(eventGroupQueries.findById(eventGroupId)).thenReturn(Optional.empty());

		// Then
		assertThrows(EntityNotFoundException.class, () -> {
			// When
			availabilityApplicationService.getAvailabilityPageTitle(eventGroupId);
		});
	}

	@Test
	public void shouldGetAvailabilityList() {
		// Given
		EventGroup eventGroup = mock(EventGroup.class);
		when(eventGroup.getId()).thenReturn(1L);
		when(eventGroup.getName()).thenReturn("Event Group Name");
		when(eventGroup.isAvailabilityActive()).thenReturn(true);
		when(eventGroupQueries.getAll()).thenReturn(List.of(eventGroup));

		// When
		List<AvailabilityListDto> availabilityList = availabilityApplicationService.getAvailabilityList();

		// Then
		assertEquals(1, availabilityList.size());
		assertEquals("Event Group Name", availabilityList.get(0).getName());
	}

	@Test
	public void shouldGetAvailabilityRate() {
		// Given
		Long eventGroupId = 1L;
		Long userId = 1L;
		when(userApplicationService.getLoggedUserId()).thenReturn(userId);

		Event event = new Event();
		event.setEventStartTime(LocalDateTime.now());

		Availability availability1 = mock(Availability.class);
		Availability availability2 = mock(Availability.class);
		when(availability1.getAvailable()).thenReturn(true);
		when(availability2.getAvailable()).thenReturn(false);
		when(availability1.getEvent()).thenReturn(event);
		when(availability2.getEvent()).thenReturn(event);
		when(availabilityQueries.getByUserIdAndEventGroup(userId, eventGroupId)).thenReturn(Arrays.asList(availability1, availability2));

		// When
		long rate = availabilityApplicationService.getAvailabilityRate(eventGroupId);

		// Then
		assertEquals(50, rate);
	}

	@Test
	public void shouldGetAvailabilitiesForDay() {
		// Given
		LocalDate day = LocalDate.of(2024, 5, 9);
		Availability availability = mock(Availability.class);

		Show show = new Show();
		show.setStage(new Stage());

		Event event = mock(Event.class);
		when(event.getEventStartTime()).thenReturn(LocalDateTime.of(day, LocalDate.now().atStartOfDay().toLocalTime()));
		when(event.getShow()).thenReturn(show);
		when(availability.getEvent()).thenReturn(event);
		List<Availability> availabilities = List.of(availability);

		// When
		List<AvailabilityDto> availabilitiesForDay = availabilityApplicationService.getAvailabilitiesForDay(day, availabilities);

		// Then
		assertEquals(1, availabilitiesForDay.size());
	}
}