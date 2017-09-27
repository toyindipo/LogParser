package com.ef.utils;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateLib {
	private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
	public static Date getDateWithTimeFromString(String dateString) throws ParseException {
        java.util.Date date = null;
        date = sdf.parse(dateString);
        return new Date(date.getTime());
    }
	
	public static String dateToString(Date date) {
		return sdf.format(date);
	}
	
	public static boolean dateBetween(Date dateToCompare, Date startDate, Date endDate) {
		if ((dateToCompare.after(startDate) && dateToCompare.before(endDate)) 
				|| dateToCompare.equals(startDate) || dateToCompare.equals(endDate)) {
			return true;
		} else {
			return false;
		}
	}
}
