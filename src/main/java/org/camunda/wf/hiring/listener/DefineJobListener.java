package org.camunda.wf.hiring.listener;

import java.sql.SQLException;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.wf.hiring.dbAccess.DBAccess;


public class DefineJobListener implements TaskListener{
	//TODO write informaion into Database
	@Override
	public void notify(DelegateTask delegateTask){
		String test = (String) delegateTask.getVariable("department");
		delegateTask.setVariable("department", test + "test");

		try {
			DBAccess.insertIntoJobOffer(test);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
