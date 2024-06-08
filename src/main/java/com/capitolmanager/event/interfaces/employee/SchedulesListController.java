
package com.capitolmanager.event.interfaces.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capitolmanager.availability.application.AvailabilityApplicationService;
import com.capitolmanager.event.application.schedule.ScheduleApplicationService;


@Controller
@RequestMapping("/scheduleList")
public class SchedulesListController {

	private final AvailabilityApplicationService availabilityApplicationService;
	private final ScheduleApplicationService scheduleApplicationService;

	@Autowired
	SchedulesListController(AvailabilityApplicationService availabilityApplicationService, ScheduleApplicationService scheduleApplicationService) {

		Assert.notNull(availabilityApplicationService, "availabilityApplicationService must not be null");
		Assert.notNull(scheduleApplicationService, "scheduleApplicationService must not be null");

		this.availabilityApplicationService = availabilityApplicationService;
		this.scheduleApplicationService = scheduleApplicationService;
	}

	@GetMapping
	String getView(Model model) {

		model.addAttribute("availabilityGroups", availabilityApplicationService.getAvailabilityList());
		model.addAttribute("schedules", scheduleApplicationService.getScheduleList());

		return "schedule-list-view";
	}
}
