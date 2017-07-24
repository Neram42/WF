package org.camunda.wf.hiring.entities;

import java.io.Serializable;

import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.property.complex.ItemId;

@SuppressWarnings("serial")
public class OutlookAppointment implements Serializable {

	transient Appointment app;
	
	OutlookAppointment(){
		
	}
	
	
	public Appointment getApp() {
		return app;
	}

	public void setApp(Appointment appo) {
		this.app = appo;
	}

	public OutlookAppointment(Appointment appo){
		this.app = appo;
	}
	
	public ItemId getItemId() throws ServiceLocalException{
		return this.app.getId();
	}
	
}
