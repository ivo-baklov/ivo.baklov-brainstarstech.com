package com.ivo.javatraing.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utill {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	 
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
	public static java.util.Date parseDate(String date) {
	    try {
	        return DATE_FORMAT.parse(date);
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}
	 
	public static java.util.Date parseTimestamp(String timestamp) {
	    try {
	        return DATE_TIME_FORMAT.parse(timestamp);
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}
}
