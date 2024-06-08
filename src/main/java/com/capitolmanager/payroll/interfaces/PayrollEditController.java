

package com.capitolmanager.payroll.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capitolmanager.payroll.application.PayrollApplicationService;


@Controller
@RequestMapping("payroll-edit")
public class PayrollEditController {

	private static final String PAYROLL_EDIT_VIEW = "payrollEdit-view";
	private final PayrollApplicationService payrollApplicationService;


	@Autowired
	PayrollEditController(PayrollApplicationService payrollApplicationService) {

		Assert.notNull(payrollApplicationService, "payrollApplicationService must not be null");

		this.payrollApplicationService = payrollApplicationService;
	}

	@GetMapping
	String getView(Model model) {

		model.addAttribute("payroll", new PayrollEditForm());


		return PAYROLL_EDIT_VIEW;
	}
	@PostMapping
	String savePayroll(@ModelAttribute PayrollEditForm payroll) {

		payrollApplicationService.savePayroll(payroll);

		return "redirect:/payroll/employee";
	}

	@ModelAttribute("hours")
	List<String> getHourValues() {
		List<String> hours = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			hours.add(String.format("%02d", i));
		}
		return hours;
	}

	@ModelAttribute("minutes")
	List<String> getMinuteValues() {

		List<String> minutes = new ArrayList<>();
		for (int i = 0; i < 60; i += 15) {
			minutes.add(String.format("%02d", i));
		}

		return minutes;
	}
}
