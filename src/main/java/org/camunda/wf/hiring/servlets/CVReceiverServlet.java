package org.camunda.wf.hiring.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.wf.hiring.entities.CvCollection;

import com.google.gson.Gson;
/*
 * This class is a servlet that serves for CV receiving
 */
@WebServlet(name = "CVReceiver", description = "Servlet for receiving the CVs", urlPatterns = "/CVReceiver")
public class CVReceiverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * this methods waits for CV from a http post request
	 */
	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String contentType = request.getContentType();
		
		PrintWriter out = response.getWriter();
		// set content type of our response for testing
		response.setContentType("application/json"); 
		
		// check if the received request contains a JSON
		if (!"application/json".equals(contentType)) { 
			response.getWriter().append("{\"error\":\"invalidRequest\",\"status\":\"wrong content type\"}");
		}

		// Load data in raw form of the received JSON
		BufferedReader reader = request.getReader();
		response.setContentType("text/html");
		out.println("<html><body>");
		
		// Initiate a CvCollection object
		CvCollection cvCollection;
		try {
			cvCollection = new Gson().fromJson(reader, CvCollection.class);
		} catch (Exception e) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"failed to create GSON\"}");
			return; // break
		}

		// serializes the cvs contained in the cvCollection
		ObjectValue cvs = Variables.objectValue(cvCollection.getCvCollection()).serializationDataFormat("application/json").create();
		
		// check if cvs are empty
		if (null == cvs) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"CV Object not created\"}");
			return; // break
		} else {
			
			// set cvs as a variable in camunda cockpit
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			RuntimeService runtimeService = processEngine.getRuntimeService();
			runtimeService.setVariable(cvCollection.getProcessInstanceId(), "cvs", cvs);
			
			// check if received Id is null
			String id = cvCollection.getProcessInstanceId();
			if (null == id) {
				out.println("<h2>Error</h2><p>Parameter id missing!</p>" + id);
			} else {
				try {
					// continue process
					runtimeService.createMessageCorrelation("CVReceiver").processInstanceId(id).correlate();
					out.println("<h1>CVs delivered to process</h1><p>ID: " + id + "</p>");
				} catch (MismatchingMessageCorrelationException e) {
					out.println("<h2>Error</h2><p>No correlating process instance.</p><p>" + id + "</p>");
				}
			}
			
		}
		out.println("</body></html>");
		out.close();

	}
}

