package org.camunda.wf.hiring.OutlookAccess;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ArrangementDateGenerator {
	
	public ArrangementDateGenerator(){
		
	}
	
	public static Calendar setStartdate(){
		Calendar startdate = new GregorianCalendar();
		// add five days to today
		startdate.add(Calendar.DAY_OF_MONTH, 5);
		// set the start time at 8 in the morning
		startdate.set(Calendar.HOUR_OF_DAY, 8);
		startdate.set(Calendar.MINUTE, 0);
		return startdate;
	}

	public static Calendar setEnddate(){
		// Initialize end time of the interview
		Calendar enddate = new GregorianCalendar();
		enddate.add(Calendar.DAY_OF_MONTH, 5);
		enddate.set(Calendar.HOUR_OF_DAY, 9);
		enddate.set(Calendar.MINUTE, 0);
		return enddate;
	}
}
