
package com.capitolmanager.stage.interfaces;

import static com.capitolmanager.stage.interfaces.StageListController.STAGE_LIST_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capitolmanager.stage.application.StageApplicationService;
import com.capitolmanager.stage.application.StageListDto;


@Controller
@RequestMapping(STAGE_LIST_PATH)
public class StageListController {

	static final String STAGE_LIST_PATH = "/stage-list";
	private static final String STAGE_LIST_VIEW = "stage-list-view";
	private static final String M_STAGES = "stages";

	private final StageApplicationService stageApplicationService;

	@Autowired
	StageListController(StageApplicationService stageApplicationService) {

		Assert.notNull(stageApplicationService, "stageApplicationService must not be null");

		this.stageApplicationService = stageApplicationService;
	}

	@GetMapping
	String getStagesView() {

		return STAGE_LIST_VIEW;
	}

	@ModelAttribute(M_STAGES)
	private List<StageListDto> getStages() {

		return stageApplicationService.findAllStages();
	}
}
