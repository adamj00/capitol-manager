

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
