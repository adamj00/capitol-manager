

package com.capitolmanager.payroll.application;

public class PayrollEmployeeListDto {

	private final Long id;
	private final String date;
	private final String start;
	private final String end;
	private final double hours;

	public PayrollEmployeeListDto(Long id, String date, String start, String end, double hours) {

		this.id = id;
		this.date = date;
		this.start = start;
		this.end = end;
		this.hours = hours;
	}

	public Long getId() {

		return id;
	}

	public String getDate() {

		return date;
	}

	public String getStart() {

		return start;
	}

	public String getEnd() {

		return end;
	}

	public double getHours() {

		return hours;
	}
}
