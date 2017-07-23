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
import org.camunda.wf.hiring.entities.Approval;

import com.google.gson.Gson;

/*
 * This servlet serves for approval receiving
 */
@WebServlet(name = "ApprovalReceiver", description = "Servlet for receiving the approval of job info. Calling the servlet leads to continuation", urlPatterns = "/ApprovalReceiver")
public class ApprovalReceivedServlet extends HttpServlet {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * This method waits for an approval 
	 */
	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String contentType = request.getContentType();
		response.setContentType("application/json");
		
		// check if request object has a json object in the body
		if (!"application/json".equals(contentType)) { // check if we really get a JSON
			response.getWriter().append("{\"error\":\"invalidRequest\",\"status\":\"wrong content type\"}");
		}

		 // contains JSON data in RAW
		BufferedReader reader = request.getReader();

		// initiate an Approval object 
		Approval approval;

		try {
			// load JSON content into the approval object
			approval = new Gson().fromJson(reader, Approval.class);
		} catch (Exception e) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"failed to creade GSON\"}");
			return; // break
		}

		// get our Instance Id
		String peerProcessInstanceId = approval.getOwnId();

		// check if our process instance Id is null
		if (null == peerProcessInstanceId) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"Response Object not created\"}");
			return; // break
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			RuntimeService runtimeService = processEngine.getRuntimeService();
			try {
				// load Instance Id of weplacm and save it 
				String externalId = approval.getExternalId();
				runtimeService.setVariable(approval.getOwnId(), "externalId", externalId);
				
				// continue process
				runtimeService.createMessageCorrelation("ApprovalReceiver").processInstanceId(peerProcessInstanceId).correlate();
							
				out.println("<html><body>");
				out.println("<h1>Message delivered to process</h1><p>ID: " + peerProcessInstanceId + "</p>");
			} catch (MismatchingMessageCorrelationException e) {
				out.println("<h2>Error</h2><p>No correlating process instance.</p><p>" + peerProcessInstanceId + "</p>");
			}
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("</body></html>");
	    out.close();
	}
}

