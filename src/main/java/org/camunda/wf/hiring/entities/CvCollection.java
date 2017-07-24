package org.camunda.wf.hiring.entities;

import java.util.ArrayList;

/**
 * This class provides an CvCollection Object
 *
 */
public class CvCollection {
	private ArrayList<CV> cvCollection;
	private String processInstanceId;
	
	public CvCollection(){
		
	}
	public CvCollection(ArrayList<CV> cvCollection, String processInstanceId) {
		this.cvCollection = cvCollection;
		this.processInstanceId = processInstanceId;
	}

	public ArrayList<CV> getCvCollection() {
		return cvCollection;
	}

	public void setCvCollection(ArrayList<CV> cvs) {
		this.cvCollection = cvs;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}
