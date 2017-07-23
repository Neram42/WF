package org.camunda.wf.hiring.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
>>>>>>> 375e8a193df4adc7a1297188673bb11c13f15e25

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
 * This servlet serves for CV receiving
 */
@WebServlet(name = "CVReceiver", description = "Servlet for receiving the CVs", urlPatterns = "/CVReceiver")
public class CVReceiverServlet extends HttpServlet {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String contentType = request.getContentType();
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json"); // content type of our
														// response, html for
		
													// testing
		if (!"application/json".equals(contentType)) { // check if we really get
														// a JSON
			response.getWriter().append("{\"error\":\"invalidRequest\",\"status\":\"wrong content type\"}");
		}

		// Instantiate the GSONs out of the received JSON
		BufferedReader reader = request.getReader(); // contains JSON data, RAW
		response.setContentType("text/html");
		out.println("<html><body>");
		
		CvCollection cvCollection;
		try {
			cvCollection = new Gson().fromJson(reader, CvCollection.class);
		} catch (Exception e) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"failed to create GSON\"}");
			return; // break
		}

		ObjectValue cvs = Variables.objectValue(cvCollection.getCvCollection()).serializationDataFormat("application/json").create();
//		ArrayList<CV> cvs = cvCollection.getCvCollection();
		
		if (null == cvs) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"CV Object not created\"}");
			return; // break
		} else {
			ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
			RuntimeService runtimeService = processEngine.getRuntimeService();
			
//			ArrayList<CV> cvs = cvCollection.getCvCollection();
//			
			

			runtimeService.setVariable(cvCollection.getProcessInstanceId(), "cvs", cvs);
			// build HTML output
			
			String id = cvCollection.getProcessInstanceId();
			if (null == id) {
				out.println("<h2>Error</h2><p>Parameter id missing!</p>" + id);
			} else {
				try {
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

/*
 * Sample JSON Object:
 * 
 * { "processInstanceId" : "359acb27-6e2d-11e7-a15a-06b9ccde9c04", "cvCollectionen": ["cv1": {"Chuck Norris",
 * "address": "Street 2", "tel": "222333", "study": "IS", "degree": "MSC",
 * "grade": "3", "sectors": "IT and Design", "motivation": "Very motivated",
 * "skills": "Lots of skills", "experience": "Amazing experience", "isAccepted":
 * true, "rating": "exceptional" }]
 * 
 */
