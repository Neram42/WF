package org.camunda.wf.hiring.services;

import java.sql.SQLException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.dbAccess.*;

/*
 * This method is called during process execution and will execute the logic
 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
 */
public class SendPayment {

	public void execute(DelegateExecution arg0) throws Exception {
		// TODO Auto-generated method stub
		// Datenbank auslesen 
		// Daten ausrechnen
		
	}
	
	/*
	 * THIS METHOD IS ONLY FOR TEST REASONS!! TODO: DELETE CONTENT BEOFRE
	 * SUBMISSION
	 */
	public static void main(String[] args) {
		// Get DB connection
		DBAccess database = new DBAccess();
		try {
			database.getSumOfSalaries();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Get all participants which have been accepted and have a salary
	
	}
}