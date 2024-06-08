

package com.capitolmanager.event.interfaces.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capitolmanager.event.application.schedule.ScheduleApplicationService;


@Controller
@RequestMapping("/scheduleEdit")
public class ScheduleEditController {

	private static final String VIEW_NAME = "schedule-edit-view";
	private final ScheduleApplicationService scheduleApplicationService;


	@Autowired
	ScheduleEditController(ScheduleApplicationService scheduleApplicationService) {

		Assert.notNull(scheduleApplicationService, "scheduleApplicationService must not be null");

		this.scheduleApplicationService = scheduleApplicationService;
	}

	@GetMapping
	String getView(Model model, @RequestParam(required = false) Long eventGroup) {

		if (eventGroup == null) {

			return "redirect:/eventGroups";
		}

		model.addAttribute("employees", scheduleApplicationService.getEmployees(eventGroup));
		model.addAttribute("events", scheduleApplicationService.getEvents(eventGroup));
		model.addAttribute("title", scheduleApplicationService.getEventGroupName(eventGroup));

		return VIEW_NAME;
	}

	@PostMapping("/change")
	@ResponseBody
	public ResponseEntity<String> changeSchedule(
		@RequestParam("user") Long userId,
		@RequestParam("event") Long eventId,
		@RequestParam("value") boolean value) {

		scheduleApplicationService.updateSchedule(userId, eventId, value);

		return ResponseEntity.ok("");
	}
}
