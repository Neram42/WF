package org.camunda.wf.hiring.sendMessages;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ProvideJobInformation implements JavaDelegate {

	/*
	 * This method provides the needed Job Information in a proper JSON format
	 * 
	 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution execution) throws Exception {
		// build HTTP post with all variables as parameters
		HttpClient client = HttpClientBuilder.create().build();
		
		String id = execution.getProcessInstanceId();
		String location = (String) execution.getVariable("location");
		String title = (String) execution.getVariable("title");
		String requirements = (String) execution.getVariable("requirements");
		String candidateProfile = (String) execution.getVariable("candidateProfile");
		String deadline = (String) execution.getVariable("deadline");
		String tasklist = (String) execution.getVariable("tasklist");
		
		
		String candidateProfileJSON = "[";
		for (String candidateProfiletemp : candidateProfile.split(",")){
			candidateProfileJSON = candidateProfileJSON + "\"" + candidateProfiletemp + "\",";
		}
		candidateProfileJSON = candidateProfileJSON.substring(0, candidateProfileJSON.length() -1);
		candidateProfileJSON = candidateProfileJSON + "]";
		
		System.out.println(candidateProfileJSON);
		
		String tasklistJSON = "[";
		for (String tasklisttemp : tasklist.split(",")){
			tasklistJSON = tasklistJSON + "\"" + tasklisttemp + "\",";
		}
		tasklistJSON = tasklistJSON.substring(0, tasklistJSON.length() -1);
		tasklistJSON = tasklistJSON + "]";
		
		System.out.println(tasklistJSON);
		
		String requirementsJSON = "[";
		for (String requirementstemp : requirements.split(",")){
			requirementsJSON = requirementsJSON + "\"" + requirementstemp + "\",";
		}
		requirementsJSON = requirementsJSON.substring(0, requirementsJSON.length() -1);
		requirementsJSON = requirementsJSON + "]";
		
		System.out.println(requirementsJSON);
		
		String JSON = "{ \"processId\": \"" + id + "\",";
		JSON = JSON + "\"tile\": \"" + title + "\",";
		JSON = JSON + "\"location\": \"" + location + "\",";
		JSON = JSON + "\"requiredGraduation\": \"" + requirementsJSON + "\",";
		JSON = JSON + "\"candidateProfile\": \"" + candidateProfileJSON + "\",";
		JSON = JSON + "\"tasklist\": \"" + tasklistJSON + "\",";
		JSON = JSON + "\"deadline\": \"" + deadline + "\"}";

		System.out.println(JSON);
		

		String postURL = "http://";
		
		try {
			HttpPost post = new HttpPost(postURL);
			StringEntity postString = new StringEntity(JSON);
			
			post.setHeader("content-type", "application/json");
			post.setEntity(postString);
			client.execute(post);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}