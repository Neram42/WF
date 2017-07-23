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

/*
 * This class calls for new CVs at WEPLACM
 */
public class RequestNewCVs implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// build HTTP post with all variables as parameters
		HttpClient client = HttpClientBuilder.create().build();

		String externalID = (String) execution.getVariable("externalID");
		String currentDate = (String) execution.getVariable("deadline");

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(currentDate);

			Date newDate;

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, 2); 
			newDate = cal.getTime(); // New date

			String newDuration = formatter.format(newDate);
			execution.setVariable("deadline", newDuration);

			String JSON = "{ \"processId\": \"" + externalID + "\",";
			JSON = JSON + "\"newDuration\": \"" + newDuration + "\"}";
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

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
