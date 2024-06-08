

package com.capitolmanager.event.interfaces.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capitolmanager.event.application.schedule.ScheduleApplicationService;


@Service
@RequestMapping("/positionAssigning")
public class PositionAssigningController {

	private static final String VIEW_NAME = "position-assignment-view";
	@Autowired private ScheduleApplicationService scheduleApplicationService;

	@GetMapping
	String getView(Model model, @RequestParam("eventGroup") Long eventGroupId) {

		if (eventGroupId == null) {

			return "redirect:/eventGroups";
		}

		model.addAttribute("employees", scheduleApplicationService.getEmployeesForAssignment(eventGroupId));
		model.addAttribute("events", scheduleApplicationService.getEventsForAssignment(eventGroupId));
		model.addAttribute("assignedPositions", scheduleApplicationService.getAssignments(eventGroupId));
		model.addAttribute("title", scheduleApplicationService.getEventGroupName(eventGroupId));
		model.addAttribute("eventGroup", eventGroupId);

		return VIEW_NAME;
	}

	@PostMapping("/assignPosition")
	@ResponseBody
	ResponseEntity<String> assignPosition(@RequestParam Long eventId,
		@RequestParam Long userId,
		@RequestParam Long positionId) {

		scheduleApplicationService.assignPosition(eventId, userId, positionId);

		return ResponseEntity.ok("");
	}
}
