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

	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// ProcessInstance processInstance;
		PrintWriter out = response.getWriter();
		out.println("<html><body>");

		String id = request.getParameter("id");
		if (null == id) {
			out.println("<h2>Error</h2><p>Parameter id missing!</p>");
		} else {
			try {
				runtimeService.createMessageCorrelation("ApprovalReceiver").processInstanceId(id).correlate();
				out.println("<h1>Message delivered to process</h1><p>ID: " + id + "</p>");
			} catch (MismatchingMessageCorrelationException e) {
				out.println("<h2>Error</h2><p>No correlating process instance.</p><p>" + id + "</p>");
			}
		}
		out.println("</body></html>");

		response.setContentType("text/html");
		out.close();
	}

	
	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String contentType = request.getContentType();
		response.setContentType("application/json");
		if (!"application/json".equals(contentType)) { // check if we really get a JSON
			response.getWriter().append("{\"error\":\"invalidRequest\",\"status\":\"wrong content type\"}");
		}

		BufferedReader reader = request.getReader(); // contains JSON data, RAW

		Approval approval;

		try {
			approval = new Gson().fromJson(reader, Approval.class);
		} catch (Exception e) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"failed to creade GSON\"}");
			return; // break
		}

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<html><body>");
		
//		ObjectValue objectApproval = Variables.objectValue(approval)
//				.serializationDataFormat("application/json")
//				.create();

		String peerProcessInstanceId = approval.getOwnId();

		if (null == peerProcessInstanceId) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"Response Object not created\"}");
			return; // break
		} else {
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			RuntimeService runtimeService = processEngine.getRuntimeService();
			try {
				String externalId = approval.getExternalId();
				runtimeService.setVariable(approval.getOwnId(), "externalId", externalId);
				runtimeService.createMessageCorrelation("ApprovalReceiver").processInstanceId(peerProcessInstanceId).correlate();
				out.println("<h1>Message delivered to process</h1><p>ID: " + peerProcessInstanceId + "</p>");
			} catch (MismatchingMessageCorrelationException e) {
				out.println("<h2>Error</h2><p>No correlating process instance.</p><p>" + peerProcessInstanceId + "</p>");
			}
		}
	    response.setContentType("text/html");
	    out.close();
	}
}

