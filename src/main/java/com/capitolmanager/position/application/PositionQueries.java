

package com.capitolmanager.position.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.position.domain.Position;


public interface PositionQueries {

	List<Position> getAll();

	Optional<Position> findById(Long id);

	Position get(Long id);
}
