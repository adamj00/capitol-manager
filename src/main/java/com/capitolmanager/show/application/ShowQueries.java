
package com.capitolmanager.show.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.show.domain.Show;


public interface ShowQueries  {

	List<Show> getAll();

	Optional<Show> findById(Long id);
}
