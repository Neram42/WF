package org.camunda.wf.hiring.services;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

import java.net.URI;
import java.security.spec.RSAKeyGenParameterSpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.OutlookAccess.ArrangementDateGenerator;
import org.camunda.wf.hiring.OutlookAccess.OutlookAccess;
import org.camunda.wf.hiring.dbAccess.DBAccess;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

//TODO: Exception handling

public class ArrangeInterviewDate implements JavaDelegate {

	// Variable to show the time in the right format if needed
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH");

	// TODO: Die Variablen eventuell durch Field Injection füllen?!
	public static String subject;
	public static String body;

	/*
	 * This method is called during process execution and will execute the logic
	 * 
	 * @see
	 * org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.
	 * engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution arg0) throws Exception {
		
		Calendar startdate = ArrangementDateGenerator.setStartdate();
		Calendar enddate = ArrangementDateGenerator.setEnddate();

//		// Initialize the startdate of the interview
//		Calendar startdate = new GregorianCalendar();
//		// add five days
//		startdate.add(Calendar.DAY_OF_MONTH, 5);
//		// set the start time at 8 in the morning
//		startdate.set(Calendar.HOUR_OF_DAY, 8);
//		startdate.set(Calendar.MINUTE, 0);
//		// Initialize end time of the interview
//		Calendar enddate = new GregorianCalendar();
//		enddate.add(Calendar.DAY_OF_MONTH, 5);
//		enddate.set(Calendar.HOUR_OF_DAY, 9);
//		enddate.set(Calendar.MINUTE, 0);

		// Set subject and body of interview
		// TODO: Name des Applicants bzw Applicant ID
		// TODO: PDF der Bewerbung?? bzw. Bewerbungsdokument
		subject = "Interview with Applicant";
		body = "Hello \n this meeting is a job interview for the applicant...";

		// checkDate(startdate, enddate);

	}

	/*
	 * THIS METHOD IS ONLY FOR TEST REASONS!! TODO: DELETE CONTENT BEOFRE SUBMISSION
	 */
	public static void main(String[] args) {
		

		Calendar startdate = ArrangementDateGenerator.setStartdate();
		Calendar enddate = ArrangementDateGenerator.setEnddate();
//		
//		// Initialize the startdate of the interview
//		Calendar startdate = new GregorianCalendar();
//		// add five days
//		startdate.add(Calendar.DAY_OF_MONTH, 5);
//		// set the start time at 8 in the morning
//		startdate.set(Calendar.HOUR_OF_DAY, 8);
//		startdate.set(Calendar.MINUTE, 0);
//		// Initialize end time of the interview
//		Calendar enddate = new GregorianCalendar();
//		enddate.add(Calendar.DAY_OF_MONTH, 5);
//		enddate.set(Calendar.HOUR_OF_DAY, 9);
//		enddate.set(Calendar.MINUTE, 0);

		// Set subject and body of interview
		// TODO: Name des Applicants bzw Applicant ID
		// TODO: PDF der Bewerbung?? bzw. Bewerbungsdokument
		subject = "Interview with Applicant";
		body = "Hello \n this meeting is a job interview for the applicant...";

		checkDate(startdate, enddate);
	}



	/*
	 * This method searches for a date in the given time where the three
	 * participants are free and writes an arrangement onto their outlook
	 * calendars TODO: Abwarten, dass im Camunda Cockpit der Termin angenommen
	 * wird
	 */
	public static void checkDate(Calendar startdate, Calendar enddate) {
		// Initialization of the access to the three accounts and save their
		// email addresses for further use
		String initiator = "hr_employee@outlook.de";
		String participant2 = "hr_representive@outlook.de";
		String participant1 = "vice_president@outlook.de";
		ExchangeService service1 = OutlookAccess.getOutlookAccess(initiator, "HRemployee");
		ExchangeService service2 = OutlookAccess.getOutlookAccess(participant1, "Vicepresident");
		ExchangeService service3 = OutlookAccess.getOutlookAccess(participant2, "HRrepresentive");

		try {
			// After checking if start and enddate not on the weekend...
			// Lookup in each calendar to check if the date is already occupied
			
			
			//TODO: OUTLOOK PACKAGE?!

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

					// Save the date temporarily on the localhost database
					// The status is set to TODO ... to show that the interview
					// is planned
					// TODO get the instanceID from a processvariable
					DBAccess.addInterviewToDB("int2", startdate, "Invitation sent");

					// writeCalendar(startdate, enddate, subject, body,
					// service1, participant1, participant2);

				} else {
					// Set no date after 17:00
					if (enddate.get(Calendar.HOUR_OF_DAY) < 17) {
						System.out.println("next hour...");
						Calendar newStartDate = startdate;
						newStartDate.add(Calendar.HOUR, 1);
						// newStartDate.set(Calendar.MINUTE, 0);
						Calendar newEndDate = enddate;
						newEndDate.add(Calendar.HOUR, 1);
						checkDate(newStartDate, newEndDate);
					} else {
						// If no date is available look at the next day
						System.out.println("next day...");
						Calendar newStartDate = startdate;
						newStartDate.add(Calendar.DAY_OF_MONTH, 1);
						newStartDate.set(Calendar.HOUR_OF_DAY, 8);
						// newStartDate.set(Calendar.MINUTE, 0);
						Calendar newEndDate = enddate;
						newEndDate.add(Calendar.DAY_OF_MONTH, 1);
						newEndDate.set(Calendar.HOUR_OF_DAY, 9);
						checkDate(newStartDate, newEndDate);
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
