package org.camunda.wf.hiring.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/*
 * This method is called during process execution and will execute the logic
 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
 */
public class SendPayment implements JavaDelegate{
	 
	public void execute(DelegateExecution execution) throws Exception {

		
		  HttpClient client = HttpClientBuilder.create().build();
		  String id = execution.getProcessInstanceId();
		  String JSON =  "\""+ id+ "\"";
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

