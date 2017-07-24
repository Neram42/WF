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

import com.google.gson.Gson;

/*
 * This class is a servlet which serves for Notification receiving
 */
@WebServlet(name = "NotificationReceiver", description = "Servlet e.g. for: Notification of no matching CVs received", urlPatterns = "/NotificationReceiver")
public class NotificationReceivedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * this method waits for post request an continues the process by receiving
	 */
	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String contentType = request.getContentType();
		response.setContentType("application/json");
		
		// check if we received a Json in body
		if (!"application/json".equals(contentType)) { 
			response.getWriter().append("{\"error\":\"invalidRequest\",\"status\":\"wrong content type\"}");
		}

		// load JSON data in raw form
		BufferedReader reader = request.getReader(); 

		// Load id 
		String id;
		try {
			id = new Gson().fromJson(reader, String.class);
		} catch (Exception e) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"failed to creade GSON\"}");
			return; // break
		}

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<html><body>");

		// check if id is null
		if (null == id) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"Response Object not created\"}");
			return; // break
		} else {
			
			// continue process
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			RuntimeService runtimeService = processEngine.getRuntimeService();
			try {
				runtimeService.createMessageCorrelation("NotificationReceiver").processInstanceId(id).correlate();
				out.println("<h1>Message delivered to process</h1><p>ID: " + id + "</p>");
			} catch (MismatchingMessageCorrelationException e) {
				out.println("<h2>Error</h2><p>No correlating process instance.</p><p>" + id + "</p>");
			}
		}
		response.setContentType("text/html");
		out.close();
	}
}
