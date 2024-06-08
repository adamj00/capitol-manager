

package com.capitolmanager.event.interfaces.employee;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.event.application.schedule.ScheduleViewService;


@Controller
@RequestMapping("/schedule")
public class ScheduleViewController {

	private final ScheduleViewService scheduleViewService;

	@Autowired
	ScheduleViewController(ScheduleViewService scheduleViewService) {

		Assert.notNull(scheduleViewService, "scheduleViewService must not be null");

		this.scheduleViewService = scheduleViewService;
	}

	@GetMapping
	String getView(Model model,
		@RequestParam Long scheduleId,
		@RequestParam(required = false, defaultValue = "false") boolean showOnlyUsers) {

		model.addAttribute("weeks", scheduleViewService.getEventsForScheduleViewByWeeks(scheduleId, showOnlyUsers));
		model.addAttribute("title", scheduleViewService.getTitle(scheduleId));
		model.addAttribute("formatter", DateTimeFormatter.ofPattern("HH:mm"));

		return "schedule-view";
	}
}
