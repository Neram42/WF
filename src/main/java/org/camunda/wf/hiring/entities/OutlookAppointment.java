package org.camunda.wf.hiring.entities;

import microsoft.exchange.webservices.data.core.service.item.Appointment;

public class OutlookAppointment {
	
	Appointment app;
	
	public Appointment getApp() {
		return app;
	}

	public void setApp(Appointment app) {
		this.app = app;
	}

	public OutlookAppointment(Appointment appo){
		this.app = appo;
	}
	
}
