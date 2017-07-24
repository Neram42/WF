package org.camunda.wf.hiring.entities;

/**
 * This class provides an Approval Object
 *
 */
public class Approval {
	private String peerProcessInstanceId;
	private String ownProcessInstanceId;
	
	public Approval(String peerProcessInstanceId, String ownProcessInstanceId){
		this.peerProcessInstanceId = peerProcessInstanceId;
		this.ownProcessInstanceId = ownProcessInstanceId;
	}
	
	public String getExternalId(){
		return ownProcessInstanceId;
	}
	public void setExternalId(String ownProcessInstanceId){
		this.ownProcessInstanceId = ownProcessInstanceId;
	}
	
	public String getOwnId(){
		return peerProcessInstanceId;
	}
	public void setOwnId(String peerProcessInstanceId){
		this.peerProcessInstanceId = peerProcessInstanceId;
	}

}
