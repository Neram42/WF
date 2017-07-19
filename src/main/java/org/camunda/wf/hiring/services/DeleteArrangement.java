package org.camunda.wf.hiring.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DeleteArrangement implements JavaDelegate {

	public void execute(DelegateExecution arg0) throws Exception {
		// TODO Delete Arrangement from Outlook Calendar using interviewDate field from Database		
	}

}
