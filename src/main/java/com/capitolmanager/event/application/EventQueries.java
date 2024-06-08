
package com.capitolmanager.event.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.event.domain.Event;


public interface EventQueries {

	List<Event> getAll();

	Optional<Event> findById(Long id);

	Event get(Long id);

}
