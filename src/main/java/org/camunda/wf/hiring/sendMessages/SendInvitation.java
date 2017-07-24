package org.camunda.wf.hiring.sendMessages;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.OutlookAccess.OutlookAccess;

import microsoft.exchange.webservices.data.core.ExchangeService;

/**
 * This class sends an invitation to the applicant via email
 *
 */
public class SendInvitation implements JavaDelegate {

	/**
	 * This method loads data of applicant and sends email
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		// Load Outlook exchange service
		ExchangeService service = OutlookAccess.getOutlookAccess("HR_representive@outlook.de", "HRrepresentive");
		
		// Load information of Applicant
		String recipient = (String) execution.getVariable("cv.email");
		String date = (String) execution.getVariable("showStartdate");
		String applicant = (String) execution.getVariable("cv.name");

		// create email
		String subject = "Invitation for job interview";
		String message = "Hello, "+ applicant  +"\n we would like to invite you to a job interview on: " + date
				+ ".  \n Please confirm our invitation. \n Best wishes, \n WBIG";

		// sends email
		OutlookAccess.sendEmail(subject, message, recipient, service);

	}

}
