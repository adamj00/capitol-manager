/*
 * Created on 13-04-2024 11:08 by ajarzabe
 *
 * Copyright (c) 2001-2024 Unity S.A.
 * ul. Strzegomska 2-4, 53-611 Wrocław, Poland
 * Wszelkie prawa zastrzeżone
 *
 * Niniejsze oprogramowanie jest własnością Unity S.A.
 * Wykorzystanie niniejszego oprogramowania jest możliwe tylko na podstawie
 * i w zgodzie z warunkami umowy licencyjnej zawartej z Unity S.A.
 */

package com.capitolmanager.changepassword.interfaces;

public class PasswordChangeForm {

	public static final String F_OLD_PASSWORD = "oldPassword";
	public static final String F_NEW_PASSWORD = "newPassword";

	private String oldPassword;
	private String newPassword;

	public PasswordChangeForm() {

	}

	public String getOldPassword() {

		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {

		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {

		return newPassword;
	}

	public void setNewPassword(String newPassword) {

		this.newPassword = newPassword;
	}
}
