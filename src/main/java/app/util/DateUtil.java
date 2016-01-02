package app.util;

import java.util.Date;

import app.model.DateFormat;

public class DateUtil implements DateFormat {

	public static String format(Date date) {

		String stringDate = format.format(date);
		return stringDate;
	}

}
