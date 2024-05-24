/*
 * Created on 24-05-2024 20:16 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.stage.application;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.position.application.PositionQueries;
import com.capitolmanager.stage.domain.Stage;
import com.capitolmanager.stage.interfaces.StageEditForm;
import com.capitolmanager.stage.interfaces.StagePositionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

public class StageApplicationServiceTest {

	@InjectMocks
	private StageApplicationService stageApplicationService;

	@Mock
	private StageQueries stageQueries;

	@Mock
	private Repository<Stage> stageRepository;

	@Mock
	private PositionQueries positionQueries;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void shouldSaveStageSuccessfully() {
		// Given
		StageEditForm stageEditForm = mock(StageEditForm.class);
		when(stageEditForm.getRequiredPositions()).thenReturn(new LinkedList<>());

		// When
		stageApplicationService.saveStage(stageEditForm);

		// Then
		verify(stageRepository).saveOrUpdate(any(Stage.class));
	}

	@Test
	public void shouldThrowExceptionWhenPositionNotFoundOnSave() {
		// Given
		StageEditForm stageEditForm = mock(StageEditForm.class);
		StagePositionDto stagePositionDto = mock(StagePositionDto.class);
		when(stageEditForm.getRequiredPositions()).thenReturn(Arrays.asList(stagePositionDto));
		when(positionQueries.findById(anyLong())).thenReturn(Optional.empty());

		// Then
		assertThrows(EntityNotFoundException.class, () -> {
			// When
			stageApplicationService.saveStage(stageEditForm);
		});
	}

	@Test
	public void shouldUpdateStageSuccessfully() {
		// Given
		StageEditForm stageEditForm = mock(StageEditForm.class);
		Stage stage = mock(Stage.class);
		when(stageEditForm.getId()).thenReturn(1L);
		when(stageQueries.findById(anyLong())).thenReturn(Optional.of(stage));

		// When
		stageApplicationService.updateStage(stageEditForm);

		// Then
		verify(stageRepository).saveOrUpdate(any(Stage.class));
	}

	@Test
	public void shouldThrowExceptionWhenStageNotFoundOnUpdate() {
		// Given
		StageEditForm stageEditForm = mock(StageEditForm.class);
		when(stageEditForm.getId()).thenReturn(1L);
		when(stageQueries.findById(anyLong())).thenReturn(Optional.empty());

		// Then
		assertThrows(EntityNotFoundException.class, () -> {
			// When
			stageApplicationService.updateStage(stageEditForm);
		});
	}

	@Test
	public void shouldDeleteStageSuccessfully() {
		// Given
		Long stageId = 1L;
		Stage stage = mock(Stage.class);
		when(stageQueries.findById(stageId)).thenReturn(Optional.of(stage));

		// When
		stageApplicationService.deleteStage(stageId);

		// Then
		verify(stageRepository).delete(stage);
	}

	@Test
	public void shouldThrowExceptionWhenStageNotFoundOnDelete() {
		// Given
		Long stageId = 1L;
		when(stageQueries.findById(stageId)).thenReturn(Optional.empty());

		// Then
		assertThrows(EntityNotFoundException.class, () -> {
			// When
			stageApplicationService.deleteStage(stageId);
		});
	}

	@Test
	public void shouldReturnTrueWhenStageWithNameExists() {
		// Given
		Long stageId = 1L;
		String stageName = "Stage Name";
		Stage stage = mock(Stage.class);
		when(stage.getId()).thenReturn(2L);
		when(stage.getName()).thenReturn(stageName);
		when(stageQueries.getAll()).thenReturn(Arrays.asList(stage));

		// When
		boolean result = stageApplicationService.existStageWithName(stageId, stageName);

		// Then
		assertTrue(result);
	}

	@Test
	public void shouldReturnFalseWhenStageWithNameDoesNotExist() {
		// Given
		Long stageId = 1L;
		String stageName = "Unique Stage Name";
		when(stageQueries.getAll()).thenReturn(Arrays.asList());

		// When
		boolean result = stageApplicationService.existStageWithName(stageId, stageName);

		// Then
		assertFalse(result);
	}
}