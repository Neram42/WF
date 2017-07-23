package org.camunda.wf.hiring.sendMessages;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.OutlookAccess.OutlookAccess;

import microsoft.exchange.webservices.data.core.ExchangeService;

public class RefuseApplicant implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		ExchangeService service = OutlookAccess.getOutlookAccess("HR_representive@outlook.de", "HRrepresentive");
		String recipient = (String) execution.getVariable("cv.email");
		String applicant = (String) execution.getVariable("cv.name");

		String subject = "Refusal";
		String message = "Hello, "+ applicant  +"\n We are very sorry but we have to refuse your applicaction. \n Best wishes, \n WBIG";

		OutlookAccess.sendEmail(subject, message, recipient, service);

	}
}
