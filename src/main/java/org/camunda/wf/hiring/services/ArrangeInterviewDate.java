package org.camunda.wf.hiring.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.OutlookAccess.ArrangementDateGenerator;
import org.camunda.wf.hiring.OutlookAccess.OutlookAccess;
import org.camunda.wf.hiring.dbAccess.DBAccess;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

//TODO: Exception handling

public class ArrangeInterviewDate implements JavaDelegate {

	// Variable to show the time in the right format if needed
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH");

	// Initialization of the access to the three accounts and save their
	// email addresses for further use
	public static String initiator;
	public static String participant2;
	public static String participant1;
	public static ExchangeService service1;
	public static ExchangeService service2;
	public static ExchangeService service3;
	
	//Saving the instanceID of the applicant
	public static String instanceID;

	/*
	 * This method is called during process execution and will execute the logic
	 * 
	 * @see
	 * org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.
	 * engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {

		//Set the start- end enddate (the startdate is 5 days after today, and the enddate is 1 hour later than the startdate)
		Calendar startdate = ArrangementDateGenerator.setStartdate();
		Calendar enddate = ArrangementDateGenerator.setStartdate();
		enddate = ArrangementDateGenerator.nextHour(enddate);
		
		//get the instanceID from camunda
		instanceID = (String) execution.getVariable("instanceID");

		participant2 = "hr_employee@outlook.de";
		initiator = "hr_representive@outlook.de";
		participant1 = "vice_president@outlook.de";
		//Get Access to the three required Outlook accounts
		service1 = OutlookAccess.getOutlookAccess(initiator, "HRemployee");
		service2 = OutlookAccess.getOutlookAccess(participant1, "Vicepresident");
		service3 = OutlookAccess.getOutlookAccess(participant2, "HRrepresentive");
		
		//Check when the participants are free for the interview
		checkDate(startdate, enddate, execution);

	}

	/*
	 * This method searches for a date in the given time where the three
	 * participants are free and writes an arrangement onto their outlook
	 * calendars
	 */
	public static void checkDate(Calendar startdate, Calendar enddate, DelegateExecution execution) {

		try {
			// After checking if start and enddate are not on the weekend:
			// Lookup in each calendar to check if the date is already occupied
			if (enddate.get(Calendar.DAY_OF_WEEK) != 1 || enddate.get(Calendar.DAY_OF_WEEK) != 7) {

				CalendarFolder cf1 = CalendarFolder.bind(service1, WellKnownFolderName.Calendar);
				FindItemsResults<Appointment> findResults1 = cf1
						.findAppointments(new CalendarView(startdate.getTime(), enddate.getTime()));

				CalendarFolder cf2 = CalendarFolder.bind(service2, WellKnownFolderName.Calendar);
				FindItemsResults<Appointment> findResults2 = cf2
						.findAppointments(new CalendarView(startdate.getTime(), enddate.getTime()));

				CalendarFolder cf3 = CalendarFolder.bind(service3, WellKnownFolderName.Calendar);
				FindItemsResults<Appointment> findResults3 = cf3
						.findAppointments(new CalendarView(startdate.getTime(), enddate.getTime()));

				
				// Check if calendars are at the given time empty
				if (findResults1.getItems().isEmpty() && findResults2.getItems().isEmpty()
						&& findResults3.getItems().isEmpty()) {
					System.out.println("The participants are all free at " + startdate.getTime());

					//TODO: DECIDE IF DATABASE ACCESS IS REQUIRED
					// Save the date temporarily on the localhost database
					DBAccess.addInterviewToDB(instanceID, startdate, "Invitation sent");
					
					//Setting of Process variables in camunda to remember Interview time
					execution.setVariable("startdate", startdate);
					execution.setVariable("enddate", enddate);
				} else {
					// Set no date after 17:00
					if (enddate.get(Calendar.HOUR_OF_DAY) < 17) {
						System.out.println("next hour...");
						//Add an hour to look for a new free spot in the calendars
						Calendar newStartDate = ArrangementDateGenerator.addHour(startdate);
						// newStartDate.set(Calendar.MINUTE, 0);
						Calendar newEndDate = ArrangementDateGenerator.addHour(enddate);

						checkDate(newStartDate, newEndDate, execution);
					} else {
						// If no date is available look at the next day
						System.out.println("next day...");
						//Add a day to look for a new free spot in the calendars
						Calendar newStartDate = ArrangementDateGenerator.nextDay(startdate);
						// newStartDate.set(Calendar.MINUTE, 0);
						Calendar newEndDate = ArrangementDateGenerator.nextDay(enddate);
						enddate = ArrangementDateGenerator.addHour(enddate);

						checkDate(newStartDate, newEndDate, execution);
					}
				}

			}

			else {
				System.out.println("Skip Weekends.");
				startdate.add(Calendar.DAY_OF_MONTH, 1);
				enddate.add(Calendar.DAY_OF_MONTH, 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
