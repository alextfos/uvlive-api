package es.uvlive.utils;

import java.util.List;
import java.util.regex.Pattern;

import es.uvlive.model.Admin;
import es.uvlive.model.Businessman;
import es.uvlive.model.Student;
import es.uvlive.model.Teacher;

public class ValidationUtils {
	
	public static boolean validateString(String string) {
		return string != null && !string.isEmpty();
	}
	
	public static boolean validateDNI(String dni) {
		String dniRegex = "\\d{8}[A-HJ-NP-TV-Z]";
		return validateString(dni) && Pattern.matches(dniRegex, dni);
	}
	
	public static boolean validateInt(int integer) {
		return integer > 0;
	}
	
	public static boolean validateTimeStamp(String timestamp) {
		return validateString(timestamp) && timestamp.length() == 13;
	}
	
	public static boolean validatePassword(String password) {
		return validateString(password) && password.length() >= 4;
	}
	
	public static boolean validateLoginType(String loginType) {
		return Student.LOGIN_TYPE.equals(loginType) | Teacher.LOGIN_TYPE.equals(loginType) |
				Businessman.LOGIN_TYPE.equals(loginType) | Admin.LOGIN_TYPE.equals(loginType);
	}
}
