package org.camunda.wf.hiring.services;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;


//This method should show Camunda that this project is a business process

@ProcessApplication("Hiring Process")
public class HiringApplication extends ServletProcessApplication {
	//empty implementation
}
