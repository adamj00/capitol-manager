
package com.capitolmanager.payroll.interfaces;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;


public class PayrollEditForm {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	private int startHour;
	private int endHour;
	private int startMinute;
	private int endMinute;

	public PayrollEditForm(LocalDate date, int startHour, int endHour, int startMinute, int endMinute) {

		this.date = date;
		this.startHour = startHour;
		this.endHour = endHour;
		this.startMinute = startMinute;
		this.endMinute = endMinute;
	}

	public PayrollEditForm() {

	}

	public LocalDate getDate() {

		return date;
	}

	public void setDate(LocalDate date) {

		this.date = date;
	}

	public int getStartHour() {

		return startHour;
	}

	public void setStartHour(int startHour) {

		this.startHour = startHour;
	}

	public int getEndHour() {

		return endHour;
	}

	public void setEndHour(int endHour) {

		this.endHour = endHour;
	}

	public int getStartMinute() {

		return startMinute;
	}

	public void setStartMinute(int startMinute) {

		this.startMinute = startMinute;
	}

	public int getEndMinute() {

		return endMinute;
	}

	public void setEndMinute(int endMinute) {

		this.endMinute = endMinute;
	}
}
