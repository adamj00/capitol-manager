

package com.capitolmanager.show.interfaces;

import static com.capitolmanager.show.interfaces.ShowListController.SHOW_LIST_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.show.application.ShowApplicationService;


@Controller
@RequestMapping("/deleteShow")
public class ShowDeleteController {

	private final ShowApplicationService showApplicationService;


	@Autowired
	ShowDeleteController(ShowApplicationService showApplicationService) {

		Assert.notNull(showApplicationService, "showApplicationService must not be null");

		this.showApplicationService = showApplicationService;
	}


	@GetMapping
	String deleteShow(@RequestParam Long id) {

		showApplicationService.deleteShow(id);

		return "redirect:" + SHOW_LIST_PATH;
	}
}