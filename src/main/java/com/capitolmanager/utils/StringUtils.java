/*
 * Created on 30-03-2024 23:59 by ajarzabe
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

public class StringUtils {

	private StringUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean hasText(String string) {

		return string != null && !string.isEmpty();
	}

	public static String getDurationString(int duration) {

		int hours = duration / 60;

		String result = "";

		if (hours > 0) {

			result += hours + "h ";
		}

		int minutes = duration % 60;

		if (minutes > 0) {

			result += minutes + "m";
		}

		return result;
	}
}
