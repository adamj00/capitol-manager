

package com.capitolmanager.homepage.interfaces;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capitolmanager.homepage.application.EventDetailsDto;
import com.capitolmanager.homepage.application.HomepageService;
import com.capitolmanager.homepage.application.calendar.HomepageCalendarService;
import com.capitolmanager.utils.DateUtils;


@Controller
public class HomepageController {

	private final HomepageService homepageService;
	private final HomepageCalendarService homepageCalendarService;


	@Autowired
	HomepageController(HomepageService homepageService, HomepageCalendarService homepageCalendarService) {

		Assert.notNull(homepageService, "homepageService must not be null");
		Assert.notNull(homepageCalendarService, "homepageCalendarService must not be null");

		this.homepageService = homepageService;
		this.homepageCalendarService = homepageCalendarService;
	}

	@GetMapping
	String getHomepage(Model model) {

		model.addAttribute("events", homepageService.getCloseEventsForLoggedUser());
		model.addAttribute("userName", homepageService.getLoggedUserName());

		model.addAttribute("monthName", DateUtils.getMonthString(LocalDate.now().getMonthValue()));
		model.addAttribute("weeks", homepageCalendarService.getUserEventsForMonthByWeeks(LocalDate.now().getYear(),
			LocalDate.now().getMonthValue()));

		model.addAttribute("formatter", DateTimeFormatter.ofPattern("HH:mm"));

		model.addAttribute("currentDate", LocalDate.now());

		return "homepage";
	}

	@GetMapping("/eventDetails")
	@ResponseBody
	ResponseEntity<EventDetailsDto> getDetails(@RequestParam Long eventId) {

		return ResponseEntity.of(homepageService.getEventDetails(eventId));
	}
}
