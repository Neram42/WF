package org.camunda.wf.hiring.OutlookAccess;

import java.util.Calendar;
import java.util.GregorianCalendar;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

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
	
	
	public static FindItemsResults<Appointment> findAppointments(ExchangeService service, Calendar startdate, Calendar enddate){
		CalendarFolder cf1;
		try {
			cf1 = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
			FindItemsResults<Appointment> findResults = cf1
					.findAppointments(new CalendarView(startdate.getTime(), enddate.getTime()));
			return findResults;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
