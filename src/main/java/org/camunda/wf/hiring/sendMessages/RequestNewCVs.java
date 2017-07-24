package org.camunda.wf.hiring.sendMessages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.camunda.wf.hiring.entities.Request;
import org.camunda.wf.hiring.services.Constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/*
 * This class waits for new CVs of WEPLACM
 */
public class RequestNewCVs implements JavaDelegate {

	/**
	 * this method builds the http post with all needed variables as Json body
	 */
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();

		// Load parameters from cockpit
		String externalId = (String) execution.getVariable("externalId");
		String currentDate = (String) execution.getVariable("deadline");

		try {
			// convert deadline into date format
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(currentDate);

			Date newDate;

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);

			// change month to two month later
			cal.add(Calendar.MONTH, 2);
			// create new date
			newDate = cal.getTime(); 

			// re-convert back to String
			String newDuration = formatter.format(newDate);
			execution.setVariable("deadline", newDuration);

			// create JSON
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String JSON = gson.toJson(new Request(externalId, newDate));
			
			// Load postURL
			String postURL = Constants.REMOTE_URL + "/processJobInquiry/receive-prolongation";

			try {
				// create post request
				HttpPost post = new HttpPost(postURL);
				StringEntity postString = new StringEntity(JSON);

				post.setHeader("content-type", "application/json");
				post.setEntity(postString);

				// send post request
				client.execute(post);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
