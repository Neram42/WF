package org.camunda.wf.hiring.arrange;

import java.net.URI;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

public class WriteDate implements JavaDelegate {

	public void execute(DelegateExecution arg0) throws Exception {
		// TODO zugriff auf Process variable
		// Initialize the startdate of the interview
				Calendar startdate = new GregorianCalendar();
				// add five days
				startdate.add(Calendar.DAY_OF_MONTH, 5);
				// set the start time at 8 in the morning
				startdate.set(Calendar.HOUR_OF_DAY, 8);
				startdate.set(Calendar.MINUTE, 0);
				// Initialize end time of the interview
				Calendar enddate = new GregorianCalendar();
				enddate.add(Calendar.DAY_OF_MONTH, 5);
				enddate.set(Calendar.HOUR_OF_DAY, 9);
				enddate.set(Calendar.MINUTE, 0);
				
				String subject = "Subject";
				String body = "Interview for...";
				ExchangeService service = 
						getOutlookAccess("hr_representive@outlook.de", "HRrepresentive");
				
		writeCalendar(startdate, enddate, subject, body, service, "HR_employee@outlook.de", "Vice_president@outlook.de");
	}
	
	public static ExchangeService getOutlookAccess(String email, String password) {
		ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
		ExchangeCredentials credentials = new WebCredentials(email, password);
		service.setCredentials(credentials);
		try {
			service.setUrl(new URI("https://outlook.com/EWS/Exchange.asmx"));
			return service;
		} catch (Exception e) {
			// TODO: Exception handling
			System.out.print("klappt nicht");
			e.printStackTrace();
			return service;
		}
	}
	
	/*
	 * This method enables accessing the calendar of a defined user and write a
	 * new date into it.
	 */
	public static void writeCalendar(Calendar startdate, Calendar enddate, String subject, String body,
			ExchangeService service, String participant1, String participant2) {
		try {
			// Set up a new appointment
			Appointment appointment = new Appointment(service);
			appointment.setSubject(subject);
			appointment.setBody(MessageBody.getMessageBodyFromText(body));

			// Set the required attendees
			appointment.getRequiredAttendees().add(participant1);
			appointment.getRequiredAttendees().add(participant2);

			// Set start and enddate
			appointment.setStart(startdate.getTime());
			appointment.setEnd(enddate.getTime());

			// Save appointment
			appointment.getStart();
			appointment.save();

		} catch (Exception e) {
			// TODO: Exception handling
			System.out.println("Dates not set");
		}

	}

}
