
package com.capitolmanager.event.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.event.domain.EventGroup;


public interface EventGroupQueries {

	Optional<EventGroup> findById(Long id);

	List<EventGroup> getAll();

	EventGroup get(Long id);
}
