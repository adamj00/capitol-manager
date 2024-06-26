
package com.capitolmanager.stage.interfaces;

import static com.capitolmanager.stage.interfaces.StageListController.STAGE_LIST_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.stage.application.StageApplicationService;


@Controller
@RequestMapping("/deleteStage")
public class StageDeleteController {

	private final StageApplicationService stageApplicationService;


	@Autowired
	StageDeleteController(StageApplicationService stageApplicationService) {

		Assert.notNull(stageApplicationService, "stageApplicationService must not be null");

		this.stageApplicationService = stageApplicationService;
	}


	@GetMapping
	String deleteStage(@RequestParam Long id) {

		stageApplicationService.deleteStage(id);

		return "redirect:" + STAGE_LIST_PATH;
	}
}