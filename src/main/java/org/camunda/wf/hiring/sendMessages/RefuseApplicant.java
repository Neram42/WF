package org.camunda.wf.hiring.sendMessages;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.OutlookAccess.OutlookAccess;

import microsoft.exchange.webservices.data.core.ExchangeService;

/**
 * 
 * This class sends an email to the applicant to refuse him/her
 *
 */
public class RefuseApplicant implements JavaDelegate {

	/**
	 * This method loads the data of the applicant and sends an email to him/her
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		// Load Outlook exchange service
		ExchangeService service = OutlookAccess.getOutlookAccess("HR_representive@outlook.de", "HRrepresentive");
		
		// Load variables of applicant
		String recipient = (String) execution.getVariable("cv.email");
		String applicant = (String) execution.getVariable("cv.name");

		// Create email
		String subject = "Refusal";
		String message = "Hello, "+ applicant  +"\n We are very sorry, but we have to refuse your applicaction. \n Best wishes, \n WBIG";

		// Send messages
		OutlookAccess.sendEmail(subject, message, recipient, service);

	}
}
