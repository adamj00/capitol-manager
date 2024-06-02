/*
 * Created on 01-06-2024 21:55 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.payroll.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitolmanager.payroll.application.PayrollApplicationService;
import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.utils.DateUtils;


@Controller
@RequestMapping("/payroll")
public class PayrollListController {

	private final UserApplicationService userApplicationService;
	private final PayrollApplicationService payrollApplicationService;

	@Autowired
	PayrollListController(UserApplicationService userApplicationService, PayrollApplicationService payrollApplicationService) {

		Assert.notNull(userApplicationService, "userApplicationService must not be null");
		Assert.notNull(payrollApplicationService, "payrollApplicationService must not be null");

		this.userApplicationService = userApplicationService;
		this.payrollApplicationService = payrollApplicationService;
	}

	@GetMapping("/employee")
	String getView(Model model, @RequestParam(required = false) String month, @RequestParam(required = false) Integer year) {

		Long userId = userApplicationService.getLoggedUserId();

		model.addAttribute("payrolls", payrollApplicationService.getPayrollEmployeeList(userId, month, year));
		model.addAttribute("sum", payrollApplicationService.getSum(userId, month, year));

		return "payrollEmployee-list-view";
	}

	@GetMapping("/manager")
	String getManagerView(Model model, @RequestParam Long userId, @RequestParam(required = false) String month, @RequestParam(required = false) Integer year) {

		model.addAttribute("payrolls", payrollApplicationService.getPayrollEmployeeList(userId, month, year));
		model.addAttribute("sum", payrollApplicationService.getSum(userId, month, year));
		model.addAttribute("userName", userApplicationService.getUserName(userId));
		model.addAttribute("userId", userId);

		return "payrollManager-list-view";

	}

	@GetMapping("/delete")
	String deletePayroll(@RequestParam Long id, @RequestParam(required = false) Long userId) {

		payrollApplicationService.deletePayroll(id);

		if (userApplicationService.isUserManager(userApplicationService.getLoggedUserId())) {

			return "redirect:/payroll/manager?userId=" + userId;
		}

		return "redirect:/payroll/employee";
	}

	@ModelAttribute("months")
	List<String> getMonths() {

		return DateUtils.monthNames;
	}
}
