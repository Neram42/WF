package org.camunda.wf.hiring.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.OutlookAccess.OutlookAccess;

import microsoft.exchange.webservices.data.core.service.item.Appointment;

public class DeleteArrangement implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		//Delete Arrangement from Outlook Calendar using the appointment that is saved in a process variable
		Appointment app = (Appointment) execution.getVariable("appointment");
		
		OutlookAccess.deleteAppointment(app);
	}

}
