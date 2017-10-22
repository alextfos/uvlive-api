package es.uvlive.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	public static String format(Calendar calendar){
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd-HH:mm:SS");
	    fmt.setCalendar(calendar);
	    return fmt.format(calendar.getTime());
	}
	
	public static GregorianCalendar getCurrentDate() {
		return new GregorianCalendar();
	}

	public static long getCurrentTimestamp() {
		return Calendar.getInstance().getTimeInMillis();
	}
}