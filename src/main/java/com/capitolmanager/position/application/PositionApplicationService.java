/*
 * Created on 26-03-2024 21:24 by ajarzabe
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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.position.domain.Position;
import com.capitolmanager.position.domain.PositionType;
import com.capitolmanager.position.interfaces.PositionsForm;


@Service
public class PositionApplicationService {

	private final PositionQueries positionQueries;
	private final Repository<Position> positionRepository;


	@Autowired
	PositionApplicationService(PositionQueries positionQueries, Repository<Position> positionRepository) {

		Assert.notNull(positionQueries, "positionQueries must not be null");
		Assert.notNull(positionRepository, "positionRepository must not be null");

		this.positionQueries = positionQueries;
		this.positionRepository = positionRepository;
	}

	public List<PositionDto> getAllPositions() {

		return positionQueries.getAll().stream()
			.map(position -> new PositionDto(position.getId(),
				position.getName(),
				position.getPositionType()))
			.toList();
	}

	public PositionsForm getAllPositionsForm() {

		return new PositionsForm(getAllPositions());
	}

	public List<PositionType> getAllPositionTypes() {

		return List.of(PositionType.values());
	}

	public void saveAll(List<PositionDto> positions) {

		for (PositionDto position : positions) {

			if (position.getId() == null) {
				Position newPosition = new Position(position.getName(), position.getPositionType());

				positionRepository.saveOrUpdate(newPosition);
			}
			else {
				Position updatedPosition = positionQueries.findById(position.getId())
					.orElseThrow(IllegalStateException::new);

				updatedPosition.setName(position.getName());
				updatedPosition.setPositionType(position.getPositionType());

				positionRepository.saveOrUpdate(updatedPosition);
			}
		}
	}

	public void deletePosition(Long id) {

		var position = positionQueries.findById(id)
			.orElseThrow(IllegalStateException::new);

		positionRepository.delete(position);
	}
}
