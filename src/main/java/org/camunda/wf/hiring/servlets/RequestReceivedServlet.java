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
 * This servlet serves for request receiving
 */
@WebServlet(
        name = "RequestReceiver",
        description = "Servlet e.g. for: Request for additional job information received",
        urlPatterns = "/RequestReceiver"
)
public class RequestReceivedServlet extends HttpServlet  {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	// GET request
//	@SuppressWarnings("deprecation")
//	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
//	  {
//		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//		RuntimeService runtimeService = processEngine.getRuntimeService();
//		PrintWriter out = response.getWriter();
//		out.println("<html><body>");
//		
//		String id = request.getParameter("id");
//		if (null == id) {
//			out.println("<h2>Error</h2><p>Parameter id missing!</p>"); } 
//					else {
//			try {
//			runtimeService.createMessageCorrelation("RequestReceiver") .processInstanceId(id).correlate();
//			out.println("<h1>Message delivered to process</h1><p>ID: " + id + "</p>"); }
//			catch (MismatchingMessageCorrelationException e) {
//			out.println("<h2>Error</h2><p>No correlating process instance.</p><p>" + id + "</p>");
//			} }
//			out.println("</body></html>");
//	   
//	    response.setContentType("text/html");
//	    out.close();
//	  }
//	
	
	// POST request
	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String contentType = request.getContentType();
		response.setContentType("application/json");
		if (!"application/json".equals(contentType)) { // check if we really get a JSON
			response.getWriter().append("{\"error\":\"invalidRequest\",\"status\":\"wrong content type\"}");
		}

		BufferedReader reader = request.getReader(); // contains JSON data, RAW

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

		if (null == id) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"Response Object not created\"}");
			return; // break
		} else {
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			RuntimeService runtimeService = processEngine.getRuntimeService();
			try {

				runtimeService.createMessageCorrelation("RequestReceiver").processInstanceId(id).correlate();
				out.println("<h1>Message delivered to process</h1><p>ID: " + id + "</p>");
			} catch (MismatchingMessageCorrelationException e) {
				out.println("<h2>Error</h2><p>No correlating process instance.</p><p>" + id + "</p>");
			}
		}
	    response.setContentType("text/html");
	    out.close();
	}
	
}
