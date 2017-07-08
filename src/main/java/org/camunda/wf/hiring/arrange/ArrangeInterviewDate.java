package org.camunda.wf.hiring.arrange;

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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;

import microsoft.exchange.webservices.data.*;
import microsoft.exchange.webservices.data.autodiscover.exception.AutodiscoverRemoteException;

public class ArrangeInterviewDate {

	public static void main(String[] args) {

		ExchangeService service = getOutlookAccess("hr_employee@outlook.de", "HRemployee");
		// sendEmail("Hallooo", "Message", "t_soll03@uni-muenster.de", service);

		// Date
		String startdate = "2017-08-22 12:00:00";
		String enddate = "2017-08-22 13:00:00";
		// writeCalendar(startdate, enddate, "TestTest", service);
		// searchDate(startdate, enddate);

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = formatter.parse(startdate);
			Date endDate = formatter.parse(enddate);
			checkDate(startDate, endDate);
		} catch (Exception e) {
		}

	}

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
			System.out.print("klappt nicht");
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
			System.out.println("Mail gesendet");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Mail wurde nicht gesendet");
			e.printStackTrace();
		}

	}

	/*
	 * This method searches for a date in the given time where the three
	 * participants are free and writes an arrangement onto their outlook
	 * calendars (SuppressWarning because I used deprecated methods...TODO:
	 * decide if this is appropriate) TODO: Abwarten, dass im Camunda Cockpit
	 * der Termin angenommen wird
	 */

	@SuppressWarnings("deprecation")
	public static void checkDate(Date startdate, Date enddate) {
		// Initialization of the access to the three accounts
		ExchangeService service1 = getOutlookAccess("hr_employee@outlook.de", "HRemployee");
		ExchangeService service2 = getOutlookAccess("vice_president@outlook.de", "Vicepresident");
		ExchangeService service3 = getOutlookAccess("hr_representive@outlook.de", "HRrepresentive");
		// System.out.println(service1.getCredentials().toString() +
		// service2.getCredentials().toString() +
		// service3.getCredentials().toString());

		try {
			// Lookup in each calendar to check if the date is already occupied
			// in the next step
			CalendarFolder cf1 = CalendarFolder.bind(service1, WellKnownFolderName.Calendar);
			FindItemsResults<Appointment> findResults1 = cf1.findAppointments(new CalendarView(startdate, enddate));

			CalendarFolder cf2 = CalendarFolder.bind(service2, WellKnownFolderName.Calendar);
			FindItemsResults<Appointment> findResults2 = cf2.findAppointments(new CalendarView(startdate, enddate));

			CalendarFolder cf3 = CalendarFolder.bind(service3, WellKnownFolderName.Calendar);
			FindItemsResults<Appointment> findResults3 = cf3.findAppointments(new CalendarView(startdate, enddate));

			// Check if calendars are at the given time empty
			if (findResults1.getItems().isEmpty() && findResults2.getItems().isEmpty()
					&& findResults3.getItems().isEmpty()) {
				// System.out.println("Alle haben Zeit " + startdate.getHours()
				// + " " + findResults1.getItems().isEmpty() + " " +
				// findResults2.getItems() + " " + findResults2.getItems());
				// If the calendars are empty at the given time an appointment
				// in the calenders are done
				// TODO: Ein Interview Object in allen Kalendern, mit
				// participants
				writeCalendar(startdate, enddate, "Interview", "Body", service1);
				writeCalendar(startdate, enddate, "Interview", "Body", service2);
				writeCalendar(startdate, enddate, "Interview", "Body", service3);
			} else {
				// Set no date after 17:00
				if (enddate.getHours() < 17) {
					System.out.println("next hour...");
					Date newStartDate = DateUtils.addHours(startdate, 1);
					Date newEndDate = DateUtils.addHours(enddate, 1);
					checkDate(newStartDate, newEndDate);
				} else {
					System.out.println("Einer hat keine Zeit");
				}
				// If no date is available look at the next day
				// TODO: Wochenenden auslassen!
				if (enddate.getHours() == 16) {
					System.out.println("next date...");
					Date newStartDate = DateUtils.addDays(startdate, 1);
					newStartDate.setHours(8);
					Date newEndDate = DateUtils.addDays(enddate, 1);
					newEndDate.setHours(9);
					checkDate(newStartDate, newEndDate);
				}
			}

		} catch (Exception e) {
			System.out.println("Exception checkDate");
		}

	}

	/*
	 * This method enables accessing the calendar of a defined user and write a
	 * new date into it.
	 */
	public static void writeCalendar(Date startdate, Date enddate, String subject, String body,
			ExchangeService service) {
		try {
			// Set up a new appointment
			Appointment appointment = new Appointment(service);
			appointment.setSubject(subject);
			appointment.setBody(MessageBody.getMessageBodyFromText(body));

			// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd
			// HH:mm:ss");
			// Date startDate = formatter.parse(startdate);
			// Date endDate = formatter.parse(enddate);

			appointment.setStart(startdate);// new
											// Date(2010-1900,5-1,20,20,00));
			appointment.setEnd(enddate); // new Date(2010-1900,5-1,20,21,00));

			appointment.getStart();
			appointment.save();
			System.out.println("Date set");
		} catch (Exception e) {
			System.out.println("Date not set");
		}

	}

}
