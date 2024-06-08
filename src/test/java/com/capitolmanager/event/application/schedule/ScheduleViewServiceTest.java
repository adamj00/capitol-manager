
package com.capitolmanager.event.application.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capitolmanager.event.application.EventGroupQueries;
import com.capitolmanager.event.application.schedule.dto.DayWithEvents;
import com.capitolmanager.event.application.schedule.dto.EventScheduleViewDto;
import com.capitolmanager.event.domain.Event;
import com.capitolmanager.event.domain.EventGroup;
import com.capitolmanager.event.domain.EventPositionAssignment;
import com.capitolmanager.position.domain.Position;
import com.capitolmanager.show.domain.Show;
import com.capitolmanager.stage.domain.Stage;
import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.user.domain.User;

class ScheduleViewServiceTest {

	@InjectMocks
	private ScheduleViewService scheduleViewService;

	@Mock
	private UserApplicationService userApplicationService;

	@Mock
	private EventGroupQueries eventGroupQueries;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldGetEventsForScheduleViewByWeeks() {
		// Given

		Show show = new Show();
		show.setStage(new Stage());

		Long eventGroupId = 1L;
		Long userId = 1L;
		EventGroup eventGroup = mock(EventGroup.class);
		Event event = mock(Event.class);
		when(event.getShow()).thenReturn(show);
		when(eventGroup.getEvents()).thenReturn(Set.of(event));
		when(eventGroupQueries.get(eventGroupId)).thenReturn(eventGroup);
		when(userApplicationService.getLoggedUserId()).thenReturn(userId);
		when(event.getEventStartTime()).thenReturn(LocalDateTime.now());

		// When
		List<List<DayWithEvents>> weeks = scheduleViewService.getEventsForScheduleViewByWeeks(eventGroupId, false);

		// Then
		assertTrue(weeks.stream().allMatch(
			week -> week.size() == 7
		));
	}

	@Test
	void shouldGetTitle() {
		// Given
		Long eventGroupId = 1L;
		EventGroup eventGroup = mock(EventGroup.class);
		when(eventGroup.getName()).thenReturn("Event Group Name");
		when(eventGroupQueries.get(eventGroupId)).thenReturn(eventGroup);

		// When
		String title = scheduleViewService.getTitle(eventGroupId);

		// Then
		assertEquals("Event Group Name", title);
	}

	@Test
	void shouldGetEventsForDay() {
		// Given
		LocalDate date = LocalDate.now();
		EventScheduleViewDto eventDto = mock(EventScheduleViewDto.class);
		when(eventDto.getEventStartTime()).thenReturn(LocalDateTime.now());
		List<EventScheduleViewDto> events = List.of(eventDto);

		// When
		List<EventScheduleViewDto> eventsForDay = scheduleViewService.getEventsForDay(events, date);

		// Then
		assertFalse(eventsForDay.isEmpty());
	}

	@Test
	void shouldGetPositionForUser() {
		// Given
		Long userId = 1L;
		Event event = mock(Event.class);
		EventPositionAssignment assignment = mock(EventPositionAssignment.class);
		Position position = mock(Position.class);
		when(event.getAssignments()).thenReturn(Set.of(assignment));
		when(assignment.getUser()).thenReturn(mock(User.class));
		when(assignment.getUser().getId()).thenReturn(userId);
		when(assignment.getPosition()).thenReturn(position);
		when(position.getName()).thenReturn("Position Name");

		// When
		String positionName = scheduleViewService.getPositionForUser(event, userId);

		// Then
		assertEquals("Position Name", positionName);
	}

	@Test
	void shouldReturnNullWhenPositionForUserNotFound() {
		// Given
		Long userId = 1L;
		Event event = mock(Event.class);
		when(event.getAssignments()).thenReturn(Set.of());

		// When
		String positionName = scheduleViewService.getPositionForUser(event, userId);

		// Then
		assertNull(positionName);
	}

}