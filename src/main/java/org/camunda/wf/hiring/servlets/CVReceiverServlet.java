package org.camunda.wf.hiring.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.util.json.JSONException;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.model.bpmn.builder.IntermediateCatchEventBuilder;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(
        name = "CVReceiver",
        description = "Servlet for receiving the CVs",
        urlPatterns = "/CVReceiver"
)
public class CVReceiverServlet extends HttpServlet  {
	 @SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	  {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance;
		
		
		request.setCharacterEncoding("UTF-8");
		String contentType = request.getContentType();
		response.setContentType("text/html"); // content type of our response, html for testing
		if (!"application/json".equals(contentType)) { // check if we really get a JSON
			response.getWriter().append("{\"error\":\"invalidRequest\",\"status\":\"wrong content type\"}");
		}
		
		// Instantiate the Jackson JSON processor
		ObjectMapper objectMapper = new ObjectMapper();
		BufferedReader reader = request.getReader(); //contains JSON data, RAW

		CV cv = objectMapper.readValue(reader, CV.class); // transform JSON data into a CV Jave object
		
		if (null == cv) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"CV Object not created\"}");
			return; //break
		} else {
			response.getWriter().append("Versuche, Daten in den Scope zu schreiben ..." + cv.getId() + " "+ cv.getName());
			
			//Load data into scope
			processInstance = runtimeService.startProcessInstanceByMessage("CV"/*, map*/);
			
			ObjectValue typedCV = Variables.objectValue(cv).serializationDataFormat("application/json").create();

			runtimeService.setVariable(processInstance.getId(), "cv", typedCV);
			response.getWriter().append(objectMapper.writeValueAsString(cv));
		}
		
		// build HTML output
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<html><body>");
		String id = cv.getId();
		if (null == id) {
			out.println("<h2>Error</h2><p>Parameter id missing!</p>" + id); } 
		else {
			try {
			runtimeService.createMessageCorrelation("continueMessage") .processInstanceId(id).correlate();
			out.println("<h1>CVs delivered to process</h1><p>ID: " + id + "</p>"); }
			catch (MismatchingMessageCorrelationException e) {
			out.println("<h2>Error</h2><p>No correlating process instance.</p><p>" + id + "</p>");
			} }
			out.println("</body></html>");
	    out.close();
	  }
}

/* Sample JSON Object:

{
	"id" : "359acb27-6e2d-11e7-a15a-06b9ccde9c04",
    "name": "Chuck Norris",
    "address": "Street 2",
    "tel": "222333",
    "study": "IS",
    "degree": "MSC",
    "grade": "3",
    "sectors": "IT and Design",
    "motivation": "Very motivated",
    "skills": "Lots of skills",
    "experience": "Amazing experience",
    "isAccepted": true,
    "rating": "exceptional"
}
 
 */
