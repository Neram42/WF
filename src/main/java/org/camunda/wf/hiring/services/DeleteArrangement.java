package org.camunda.wf.hiring.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.OutlookAccess.OutlookAccess;

import microsoft.exchange.webservices.data.core.service.item.Appointment;

public class DeleteArrangement implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// TODO Delete Arrangement from Outlook Calendar using interviewDate field from Database
		Appointment app = (Appointment) execution.getVariable("appointment");
		
		OutlookAccess.deleteArrangement(app);
	}

}
