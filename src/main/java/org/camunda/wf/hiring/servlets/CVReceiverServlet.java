package org.camunda.wf.hiring.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.model.bpmn.builder.IntermediateCatchEventBuilder;

@WebServlet(
        name = "CVReceiver",
        description = "Servlet for receiving the CVs",
        urlPatterns = "/CVReceiver"
)
public class CVReceiverServlet extends HttpServlet  {
	 @SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	  {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		ProcessInstance processInstance;
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		// 
		
		// not finished
		
		//
		String id = request.getParameter("id");
		if (null == id) {
			out.println("<h2>Error</h2><p>Parameter id missing!</p>"); } 
					else {
			try {
			runtimeService.createMessageCorrelation("continueMessage") .processInstanceId(id).correlate();
			out.println("<h1>Message delivered to process</h1><p>ID: " + id + "</p>"); }
			catch (MismatchingMessageCorrelationException e) {
			out.println("<h2>Error</h2><p>No correlating process instance.</p><p>" + id + "</p>");
			} }
			out.println("</body></html>");
	   
	    response.setContentType("text/html");
	    out.close();
	  }
}
