package org.camunda.wf.hiring.listener;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.wf.hiring.dbAccess.DBAccess;

//TODO: DELETE?!?
public class DefineJobListener implements TaskListener{
	
	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	//TODO write information into Database
	@Override
	public void notify(DelegateTask delegateTask){
//		String test = delegateTask.getProcessInstanceId();

		//			DBAccess.insertIntoJobOffer(test);
//		try {
//			DBAccess.insertIntoJobOffer("test", "title", "ocation", "requ", "Profile", "tasklist", "deadline");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("Input database...");
	}


}
