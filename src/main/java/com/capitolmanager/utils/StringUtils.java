
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
