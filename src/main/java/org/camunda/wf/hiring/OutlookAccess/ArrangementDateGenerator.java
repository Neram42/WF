package org.camunda.wf.hiring.OutlookAccess;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ArrangementDateGenerator {
	
	public ArrangementDateGenerator(){
		
	}
	
	public static Calendar setStartdate(){
		Calendar date = new GregorianCalendar();
		// add five days to today
		date.add(Calendar.DAY_OF_MONTH, 5);
		// set the start time at 8 in the morning
		date.set(Calendar.HOUR_OF_DAY, 8);
		date.set(Calendar.MINUTE, 0);
		return date;
	}

	public static Calendar addHour(Calendar date){
		date.add(Calendar.HOUR_OF_DAY, 1);
		date.set(Calendar.MINUTE, 0);
		return date;
	}
	
	public static Calendar nextHour(Calendar date){
		System.out.println("next hour...");
		Calendar nextHour= date;
		nextHour.add(Calendar.HOUR, 1);
		return nextHour;
	}
	
	public static Calendar nextDay(Calendar date){
		System.out.println("next day...");
		Calendar newDate = date;
		newDate.add(Calendar.DAY_OF_MONTH, 1);
		newDate.set(Calendar.HOUR_OF_DAY, 8);
		return newDate;
	}
	
	public static Calendar setTime(Calendar date, int hour){
		Calendar newDate = date;
		newDate.set(Calendar.HOUR_OF_DAY, hour);
		return newDate;
	}
}
