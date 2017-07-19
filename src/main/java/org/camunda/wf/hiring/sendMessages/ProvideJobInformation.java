package org.camunda.wf.hiring.sendMessages;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.commons.httpclient.methods.*;
//import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
//import java.io.*;


public class ProvideJobInformation implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		// build HTTP request with all variables as parameters
		HttpClient client = HttpClients.createDefault();
		RequestBuilder requestBuilder = RequestBuilder.get()
				.setUri("https://requestb.in/https://requestb.in/1iv9qco1")
				.addParameter("id", execution.getProcessInstanceId());
		
		for (String variable : execution.getVariableNames()){
			requestBuilder.addParameter(variable, String.valueOf(execution.getVariable(variable)));			
		}
		
		// execute request
		HttpUriRequest request = requestBuilder.build();
		HttpResponse response = client.execute(request);
		
		//log debug information
		System.out.println(request.getURI());
		System.out.println(response.getStatusLine());
	}
	

}
