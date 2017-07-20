package org.camunda.wf.hiring.services;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.OutlookAccess.OutlookAccess;

import microsoft.exchange.webservices.data.core.ExchangeService;

public class WriteDate implements JavaDelegate {

	// TODO: Die Variablen eventuell durch Field Injection füllen?
	public static String subject;
	public static String body;
	public static String instanceID = "ins1";

	public void execute(DelegateExecution execution) throws Exception {
		// Initialize the startdate of the interview
		Calendar startdate = new GregorianCalendar();
		startdate = (Calendar) execution.getVariable("startdate");
		Calendar enddate = new GregorianCalendar();
		enddate = (Calendar) execution.getVariable("enddate");

		String participant = (String) execution.getVariable("name");
		
		// Set subject and body of interview
		// TODO: Name des Applicants bzw Applicant ID
		subject = "Interview with Applicant: " + participant;
		body = "Hello \n this meeting is a job interview for the applicant: " + participant+ ".";

		ExchangeService service = OutlookAccess.getOutlookAccess("HR_representive@outlook.de", "HRrepresentive");

		OutlookAccess.writeCalendar(startdate, enddate, subject, body, service, "HR_employee@outlook.de",
				"Vice_president@outlook.de");
	}

}
