package org.camunda.wf.hiring.services;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.google.gson.Gson;

import Utils.Constants;

/*
 * This method is called during process execution and will execute the logic
 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
 */
public class SendPayment implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// We load the entered salary of the applicant to be able to send it to the controlling process
		// because the controlling process is not in our scope, we did not implement this
		// Double salary = (Double) execution.getVariable("salary");

		HttpClient client = HttpClientBuilder.create().build();
		
		// Load externalId of weplacm
		String externalId = (String) execution.getVariable("externalId");
		String JSON = new Gson().toJson(externalId);

		System.out.println(JSON);
		String postURL = Constants.REMOTE_URL + "/processJobInquiry/receive-payment";
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
