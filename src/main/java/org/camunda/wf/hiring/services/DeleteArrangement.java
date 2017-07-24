package org.camunda.wf.hiring.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.OutlookAccess.OutlookAccess;
import org.camunda.wf.hiring.entities.OutlookAppointment;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.property.complex.ItemId;

public class DeleteArrangement implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		//  Delete Arrangement from Outlook Calendar using the appointment that is saved in a process variable
//		OutlookAppointment app = (OutlookAppointment) execution.getVariable("appointment");
		ItemId app = (ItemId) execution.getVariable("appointmentId");
//		System.out.println(app.getApp().getSubject());
		
		ExchangeService service = OutlookAccess.getOutlookAccess("hr_representive@outlook.de", "HRrepresentive");
		
		OutlookAccess.deleteAppointment(service, app);
	}

}
