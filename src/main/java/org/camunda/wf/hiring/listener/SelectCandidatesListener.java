package org.camunda.wf.hiring.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class SelectCandidatesListener implements TaskListener {
	
	public void notify(DelegateTask delegateTask){
		//Set the Processvariable instanceID
		//TODO: aus dem formular des User Task holen...
		String instanceID = "ins1";
		delegateTask.setVariable("instanceID", instanceID);
	}
}
