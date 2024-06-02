package com.capitolmanager.payroll.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import com.capitolmanager.hibernate.AbstractEntity;
import com.capitolmanager.user.domain.User;

@Entity
@Table(name = "payrolls")
public class Payroll extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "start_time", nullable = false)
	private LocalTime start;

	@Column(name = "end_time", nullable = false)
	private LocalTime end;

	@Column(name = "hours", nullable = false)
	private double hours;


	public Payroll(LocalDate date, LocalTime start, LocalTime end) {

		this.date = date;
		this.start = start;
		this.end = end;
	}

	public Payroll() {

	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public User getUser() {

		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}

	public LocalDate getDate() {

		return date;
	}

	public void setDate(LocalDate date) {

		this.date = date;
	}

	public LocalTime getStart() {

		return start;
	}

	public void setStart(LocalTime start) {

		this.start = start;
	}

	public LocalTime getEnd() {

		return end;
	}

	public void setEnd(LocalTime end) {

		this.end = end;
	}

	public double getHours() {

		return hours;
	}

	public void setHours(double hours) {

		this.hours = hours;
	}
}
