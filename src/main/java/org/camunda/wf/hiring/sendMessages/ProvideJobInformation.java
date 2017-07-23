package org.camunda.wf.hiring.sendMessages;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.wf.hiring.services.Constants;

public class ProvideJobInformation implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// build HTTP post with all variables as parameters
		HttpClient client = HttpClientBuilder.create().build();
		
		//Load parameters from camunda cockpit
		String id = execution.getProcessInstanceId();
		String location = (String) execution.getVariable("location");
		String title = (String) execution.getVariable("title");
		String requirements = (String) execution.getVariable("requirements");
		String candidateProfile = (String) execution.getVariable("candidateProfile");
		String deadline = (String) execution.getVariable("deadline");
		String tasklist = (String) execution.getVariable("tasklist");
		
		// Generate String arrays 
		String candidateProfileJSON = "[";
		for (String candidateProfiletemp : candidateProfile.split(",")){
			candidateProfileJSON = candidateProfileJSON + "\"" + candidateProfiletemp + "\",";
		}
		candidateProfileJSON = candidateProfileJSON.substring(0, candidateProfileJSON.length() -1);
		candidateProfileJSON = candidateProfileJSON + "]";

		String tasklistJSON = "[";
		for (String tasklisttemp : tasklist.split(",")){
			tasklistJSON = tasklistJSON + "\"" + tasklisttemp + "\",";
		}
		tasklistJSON = tasklistJSON.substring(0, tasklistJSON.length() -1);
		tasklistJSON = tasklistJSON + "]";
			
		// create final Json
		String JSON = "{ \"processId\": \"" + id + "\",";
		JSON = JSON + "\"title\": \"" + title + "\",";
		JSON = JSON + "\"location\": \"" + location + "\",";
		JSON = JSON + "\"requiredGraduation\": \"" + requirements + "\",";
		JSON = JSON + "\"candidateProfile\": " + candidateProfileJSON + ",";
		JSON = JSON + "\"taskList\": " + tasklistJSON + ",";
		JSON = JSON + "\"deadline\": \"" + deadline + "\"}";

		System.out.println(JSON);
		
		String postURL = Constants.REMOTE_URL + "/processJobInquiry/job-inquiry";
		
		try {
			// create post request
			HttpPost post = new HttpPost(postURL);
			StringEntity postString = new StringEntity(JSON);
			
			post.setHeader("content-type", "application/json");
			post.setEntity(postString);
			
			// execute post
			client.execute(post);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}