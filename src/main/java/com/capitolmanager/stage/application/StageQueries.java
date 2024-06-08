

package com.capitolmanager.stage.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.stage.domain.Stage;


public interface StageQueries {

	List<Stage> getAll();

	Optional<Stage> findById(Long id);

	Stage get(Long id);
}
