
package com.capitolmanager.show.interfaces;

import static com.capitolmanager.show.interfaces.ShowEditController.SHOW_EDIT_PATH;
import static com.capitolmanager.show.interfaces.ShowListController.SHOW_LIST_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.show.application.ShowApplicationService;
import com.capitolmanager.show.application.ShowValidator;
import com.capitolmanager.stage.application.StageApplicationService;
import com.capitolmanager.stage.application.StageSelectionDto;


@Controller
@RequestMapping(SHOW_EDIT_PATH)
public class ShowEditController {

	static final String	SHOW_EDIT_PATH = "show-edit";
	private static final String VIEW_NAME = "show-edit-view";
	private static final String M_STAGES = "stages";
	private static final String M_SHOW = "show";

	private final ShowApplicationService showApplicationService;
	private final StageApplicationService stageApplicationService;
	private final ShowEditFormFactory showEditFormFactory;
	private final ShowValidator showValidator;


	@Autowired
	ShowEditController(ShowApplicationService showApplicationService,
		StageApplicationService stageApplicationService,
		ShowEditFormFactory showEditFormFactory,
		ShowValidator showValidator) {

		Assert.notNull(showApplicationService, "showApplicationService must not be null");
		Assert.notNull(stageApplicationService, "stageApplicationService must not be null");
		Assert.notNull(showEditFormFactory, "showEditFormFactory must not be null");
		Assert.notNull(showValidator, "showValidator must not be null");

		this.showApplicationService = showApplicationService;
		this.stageApplicationService = stageApplicationService;
		this.showEditFormFactory = showEditFormFactory;
		this.showValidator = showValidator;
	}


	@InitBinder
	private void customizeBinding(WebDataBinder binder) {

		binder.setValidator(showValidator);
	}

	@ModelAttribute(M_STAGES)
	List<StageSelectionDto> getAllStages() {

		return stageApplicationService.getAllStagesSelectionDto();
	}

	@GetMapping
	String getEditView(@RequestParam(required = false) Long id, Model model) {

		if (id == null) {
			model.addAttribute(M_SHOW, ShowEditFormFactory.emptyForm());
		}

		else {
			model.addAttribute(M_SHOW, showEditFormFactory.getFormById(id));
		}

		return VIEW_NAME;
	}

	@PostMapping
	String saveShow(@Validated @ModelAttribute(M_SHOW) ShowEditForm show, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			return VIEW_NAME;
		}

		if (show.getId() == null) {

			showApplicationService.saveShow(show);
		}

		else {

			showApplicationService.updateShow(show);
		}

		return "redirect:" + SHOW_LIST_PATH;
	}
}
