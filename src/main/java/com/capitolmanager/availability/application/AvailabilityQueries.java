

package com.capitolmanager.availability.application;

import java.util.List;
import java.util.Optional;

import com.capitolmanager.availability.domain.Availability;


public interface AvailabilityQueries {

	List<Availability> getByUserIdAndEventGroup(Long userId, Long eventGroupId);
	Optional<Availability> findById(Long id);

	List<Availability> getAll();
}
