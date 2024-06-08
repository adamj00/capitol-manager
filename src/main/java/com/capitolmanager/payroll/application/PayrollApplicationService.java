

package com.capitolmanager.payroll.application;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.capitolmanager.hibernate.Repository;
import com.capitolmanager.payroll.domain.Payroll;
import com.capitolmanager.payroll.interfaces.PayrollEditForm;
import com.capitolmanager.user.application.UserApplicationService;
import com.capitolmanager.utils.DateUtils;


@Service
public class PayrollApplicationService {

	private final PayrollQueries payrollQueries;
	private final Repository<Payroll> payrollRepository;
	private final UserApplicationService userApplicationService;


	@Autowired
	PayrollApplicationService(PayrollQueries payrollQueries, Repository<Payroll> payrollRepository, UserApplicationService userApplicationService) {

		Assert.notNull(payrollQueries, "payrollQueries must not be null");
		Assert.notNull(payrollRepository, "payrollRepository must not be null");
		Assert.notNull(userApplicationService, "userApplicationService must not be null");

		this.payrollQueries = payrollQueries;
		this.payrollRepository = payrollRepository;
		this.userApplicationService = userApplicationService;
	}

	public List<PayrollEmployeeListDto> getPayrollEmployeeList(Long userId, String month, Integer year) {

		List<Payroll> payrolls = payrollQueries.getAll();

		return payrolls.stream()
			.filter(payroll -> payroll.getUser().getId().equals(userId))
			.filter(payroll -> month == null || payroll.getDate().getMonthValue() == DateUtils.monthNames.lastIndexOf(month) + 1)
			.filter(payroll -> year == null || payroll.getDate().getYear() == year)
			.sorted(Comparator.comparing(Payroll::getDate))
			.map(this::mapToDto)
			.toList();
	}

	public void savePayroll(PayrollEditForm form) {

		Payroll payroll = new Payroll(form.getDate(),
			getTimeByValues(form.getStartHour(), form.getStartMinute()),
			getTimeByValues(form.getEndHour(), form.getEndMinute()));

		payroll.setUser(userApplicationService.getLoggedUser());
		payroll.setHours(calculateHours(payroll.getStart(), payroll.getEnd()));

		payrollRepository.saveOrUpdate(payroll);
	}

	public void deletePayroll(Long payrollId) {
		payrollRepository.delete(payrollQueries.get(payrollId));
	}

	public double getSum(Long userId, String month, Integer year) {
		List<Payroll> payrolls = payrollQueries.getAll();

		return payrolls.stream()
			.filter(payroll -> payroll.getUser().getId().equals(userId))
			.filter(payroll -> month == null || payroll.getDate().getMonthValue() == DateUtils.monthNames.lastIndexOf(month) + 1)
			.filter(payroll -> year == null || payroll.getDate().getYear() == year)
			.mapToDouble(Payroll::getHours)
			.sum();
	}

	public void deletePayrollsForUser(Long userId) {

		List<Payroll> payrolls = payrollQueries.getAll();

		payrolls.stream()
			.filter(payroll -> payroll.getUser().getId().equals(userId))
			.forEach(payrollRepository::delete);
	}

	private PayrollEmployeeListDto mapToDto(Payroll payroll) {

		return new PayrollEmployeeListDto(payroll.getId(),
			DateUtils.formatLocalDateToDDMM(payroll.getDate()),
			DateUtils.formatLocalTime(payroll.getStart()),
			DateUtils.formatLocalTime(payroll.getEnd()),
			payroll.getHours());
	}

	private LocalTime getTimeByValues(int hour, int minute) {
		return LocalTime.of(hour, minute);
	}

	private double calculateHours(LocalTime startTime, LocalTime endTime) {
		Duration duration = Duration.between(startTime, endTime);

		var result =  duration.toMinutes() / 60.0;

		return result >= 0 ? result : 0.0;
	}
}
