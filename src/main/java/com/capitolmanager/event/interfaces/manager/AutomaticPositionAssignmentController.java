
package com.capitolmanager.event.interfaces.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.event.application.schedule.PositionsAssignmentService;


@Controller
@RequestMapping("/autoPositionAssignment")
public class AutomaticPositionAssignmentController {

	private final PositionsAssignmentService positionsAssignmentService;


	@Autowired
	AutomaticPositionAssignmentController(PositionsAssignmentService positionsAssignmentService) {

		Assert.notNull(positionsAssignmentService, "positionsAssignmentService must not be null");

		this.positionsAssignmentService = positionsAssignmentService;
	}

	@GetMapping
	String assignAndRedirect(@RequestParam Long eventGroupId) {

		positionsAssignmentService.assignPositionsAutomatically(eventGroupId);

		return "redirect:/positionAssigning?eventGroup=" + eventGroupId;
	}
}
