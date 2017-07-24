package org.camunda.wf.hiring.OutlookAccess;

import java.net.URI;
import java.util.Calendar;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.service.DeleteMode;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.ItemId;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

public class OutlookAccess {

	/*
	 * This method establishes a connection to the Exchange server. The exchange
	 * server enables access to mails, calendars etc.
	 */
	public static ExchangeService getOutlookAccess(String email, String password) {
		ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
		ExchangeCredentials credentials = new WebCredentials(email, password);
		service.setCredentials(credentials);
		try {
			service.setUrl(new URI("https://outlook.com/EWS/Exchange.asmx"));
			return service;
		} catch (Exception e) {
			System.out.print("Outlook Access not available");
			e.printStackTrace();
			return service;
		}
	}
	
	/*
	 * This method enables sending an Email to a defined recipient.
	 */
	public static void sendEmail(String subject, String message, String recipient, ExchangeService service) {
		EmailMessage msg;
		try {
			msg = new EmailMessage(service);
			msg.setSubject(subject);
			msg.setBody(MessageBody.getMessageBodyFromText(message));
			msg.getToRecipients().add(recipient);
			msg.send();
			System.out.println("Mail sent.");
		} catch (Exception e) {
			System.out.println("Mail not sent.");
			e.printStackTrace();
		}

	}
	
	/*
	 * This method enables accessing the calendar of a defined user and write a
	 * new date into it.
	 */
	public static Appointment writeCalendar(Calendar startdate, Calendar enddate, String subject, String body,
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
			
			return appointment;
		} catch (Exception e) {
			System.out.println("Dates could not be set in the calendars.");
		}
		return null;

	}
	
	

	/*
	 * This method should deletes an appointment
	 */
	public static void deleteAppointment(ExchangeService service, ItemId id) throws ServiceLocalException, Exception{
		
		Appointment appointment = Appointment.bind(service, id);
		appointment.delete(DeleteMode.MoveToDeletedItems);
		
//		 appointment.delete(DeleteMode.HardDelete);
	}
	
}
