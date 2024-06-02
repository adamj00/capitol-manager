/*
 * Created on 01-05-2024 14:54 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class DateUtils {

	private DateUtils() {

		throw new IllegalStateException("Utility class");
	}

	static List<String> weekDays = List.of("poniedziałek",
		"wtorek",
		"środa",
		"czwartek",
		"piątek",
		"sobota",
		"niedziela");

	public static List<String> monthNames = List.of("Styczeń",
		"Luty",
		"Marzec",
		"Kwiecień",
		"Maj",
		"Czerwiec",
		"Lipiec",
		"Sierpień",
		"Wrzesień",
		"Październik",
		"Listopad",
		"Grudzień");

	public static String formatLocalDateToDDMM(LocalDate date) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
		return date.format(formatter);
	}

	public static String formatLocalDateTime(LocalDateTime localDateTime) {

		String dayString = formatLocalDateToDDMM(localDateTime.toLocalDate());
		String weekDay = weekDays.get(localDateTime.getDayOfWeek().getValue() - 1);
		String time = localDateTime.toLocalTime().toString();

		return weekDay + " " + dayString + " " + time;
	}

	public static String formatLocalDateTimeWithGodzina(LocalDateTime localDateTime) {

		String dayString = formatLocalDateToDDMM(localDateTime.toLocalDate());
		String weekDay = weekDays.get(localDateTime.getDayOfWeek().getValue() - 1);
		String time = localDateTime.toLocalTime().toString();

		return weekDay + " " + dayString + ", godzina " + time;
	}

	public static String getMonthString(int month) {

		if (month > 12 || month < 1){
			throw new IllegalArgumentException();
		}

		return monthNames.get(month - 1);
	}

	public static String formatLocalTime(LocalTime time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return time.format(formatter);
	}
}