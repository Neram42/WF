package org.camunda.wf.hiring.sendMessages;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendInvitation implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO TESTEN SOBALD INFOs zur EMAIL Usw. stehen
		
//		ExchangeService service= OutlookAccess.getOutlookAccess("HR_representive@outlook.de", "HRrepresentive");
//		String recipient = (String) execution.getVariable("email");
//		String date = (String) execution.getVariable("showStartdate");
//		
//		String subject = "Invitation for job interview";
//		String message = "Hello, \n we would like to invite you to a job interview on: " + date +".  \n Please confirm our invitation. \n Best wishes, \n WBIG";
//		
//		OutlookAccess.sendEmail(subject, message, recipient, service);
		
		
	}

}
