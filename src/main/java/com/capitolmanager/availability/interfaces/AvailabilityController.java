
package com.capitolmanager.availability.interfaces;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capitolmanager.availability.application.AvailabilityApplicationService;


@Controller
@RequestMapping("/availability")
public class AvailabilityController {

	@Autowired private AvailabilityApplicationService availabilityApplicationService;

	@GetMapping
	String getView(@RequestParam(required = false) Long eventGroup, Model model) {

		if (eventGroup == null || !availabilityApplicationService.isAvailabilityActive(eventGroup)) {

			return "redirect:/availabilityList";
		}

		model.addAttribute("weeks", availabilityApplicationService.getMonthWeeksWithAvailabilities(eventGroup));
		model.addAttribute("formatter", DateTimeFormatter.ofPattern("HH:mm"));
		model.addAttribute("title", availabilityApplicationService.getAvailabilityPageTitle(eventGroup));

		return "availability";
	}
	@PostMapping("/change")
	@ResponseBody
	public ResponseEntity<String> changeAvailability(
		@RequestParam("id") Long availabilityId,
		@RequestParam("value") boolean value) {

		availabilityApplicationService.updateAvailability(availabilityId, value);

		return ResponseEntity.ok("");
	}
}
