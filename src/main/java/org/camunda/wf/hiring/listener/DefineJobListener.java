package org.camunda.wf.hiring.listener;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.wf.hiring.dbAccess.DBAccess;


public class DefineJobListener implements TaskListener{
	
	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	//TODO write information into Database
	@Override
	public void notify(DelegateTask delegateTask){
		String test = (String) delegateTask.getVariable("department");

//		try {
//			DBAccess.insertIntoJobOffer(test);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
	}


}
