package org.camunda.wf.hiring.services;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.OutlookAccess.OutlookAccess;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.service.item.Appointment;

public class WriteDate implements JavaDelegate {

	public static String subject;
	public static String body;
	public static String instanceID;

	public void execute(DelegateExecution execution) throws Exception {
		// Initialize the startdate and the enddate of the interview to set the arrangement from process variables
		Calendar startdate = new GregorianCalendar();
		startdate = (Calendar) execution.getVariable("startdate");
		Calendar enddate = new GregorianCalendar();
		enddate = (Calendar) execution.getVariable("enddate");
		
		//Initialize instanceID from process variables
		instanceID = (String) execution.getVariable("id");

		String participant = (String) execution.getVariable("name");
		
		// Set subject and body of interview
		subject = "Interview with Applicant: " + participant;
		body = "Hello \n this meeting is a job interview for the applicant: " + participant+ ".";
		

		ExchangeService service = OutlookAccess.getOutlookAccess("HR_representive@outlook.de", "HRrepresentive");

		//Write Arrangement into Outlook
		Appointment app = OutlookAccess.writeCalendar(startdate, enddate, subject, body, service, "HR_employee@outlook.de",
				"Vice_president@outlook.de");
		
		execution.setVariable("appointment", app);
	}

}
