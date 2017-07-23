package org.camunda.wf.hiring.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/*
 * This method is called during process execution and will execute the logic
 * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.DelegateExecution)
 */
public class SendPayment implements JavaDelegate{
	 
	public void execute(DelegateExecution execution) throws Exception {

		  String url = Constants.REMOTE_URL + "/post_test.php";
		  URL obj = new URL(url);
		  HttpURLConnection conection = (HttpURLConnection) obj.openConnection();
		 
		        // Setting basic post request
		  conection.setRequestMethod("POST");
		  //con.setRequestProperty("User-Agent", USER_AGENT);
		  conection.setRequestProperty("Content-Type","application/json");
		 
		  Double salary = (Double) execution.getVariable("salary");
		  salary = salary * 0.05;
		  String jsonData = "'{'salary':"+String.valueOf(salary)+"}'";
		  
		  // Send post request
		  conection.setDoOutput(true);
		  DataOutputStream wr = new DataOutputStream(conection.getOutputStream());
		  wr.writeBytes(jsonData);
		  wr.flush();
		  wr.close();
		 
		  int responseCode = conection.getResponseCode();
		  System.out.println("Sending 'POST' request to URL : " + url);
		  System.out.println("Post Data : " + jsonData);
		  System.out.println("Response Code : " + responseCode);
		 
		  BufferedReader in = new BufferedReader(
		          new InputStreamReader(conection.getInputStream()));
		  String output;
		  StringBuffer response = new StringBuffer();
		 
		  while ((output = in.readLine()) != null) {
		   response.append(output);
		  }
		  in.close();
		  
		  //printing result from response
		  System.out.println(response.toString());
		 }	
	}
	

