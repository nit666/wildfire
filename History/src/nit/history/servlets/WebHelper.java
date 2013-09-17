package nit.history.servlets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WebHelper {
	public static void doError(boolean condition, String message, List<String> messages) throws Exception {
		if (condition) {
			messages.add(message);
			throw new Exception();
		}
	}

	public static boolean doWarning(boolean condition, String message, List<String> messages) {
		if (condition) {
			messages.add(message);
			return false;
		}
		return true;
	}
	
	public static boolean isEmpty(String str) {
		if (str != null && str.length() > 0) {
			return false;
		}
		return true;
	}
	
	public static boolean verifyStringParam(String param) {
		if (!isEmpty(param)) {
			return true;
		}
		return false;
	}
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static Date stringToDatePamameter(String dateString) {
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;	
		}
	}
}
