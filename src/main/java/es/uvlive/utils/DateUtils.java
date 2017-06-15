package es.uvlive.utils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DateUtils {
	public static String format(GregorianCalendar calendar){
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd-HH:mm:SS");
	    fmt.setCalendar(calendar);
	    String dateFormatted = fmt.format(calendar.getTime());
	    return dateFormatted;
	}
	
	public static GregorianCalendar getCurrentDate() {
		return new GregorianCalendar();
	}
}