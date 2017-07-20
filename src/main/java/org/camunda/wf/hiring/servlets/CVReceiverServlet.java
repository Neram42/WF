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
		response.setContentType("application/json");
		if (!"application/json".equals(contentType)) {
			response.getWriter().append("{\"error\":\"invalidRequest\",\"status\":\"wrong content type\"}");
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		BufferedReader reader = request.getReader();
		CV cv = objectMapper.readValue(reader, CV.class);
		
		if (null == cv) {
			response.getWriter().append("{\"error\":\"invalidRequest\", \"status\":\"GSON not correctly created\"}");
			return; //break
		} else {
			
			processInstance = runtimeService.startProcessInstanceByMessage("CV"/*, map*/);
			
			ObjectValue typedJobInquiry = Variables.objectValue(cv).serializationDataFormat("application/json").create();

			runtimeService.setVariable(processInstance.getId(), "jobInquiry", typedJobInquiry);
			response.getWriter().append(objectMapper.writeValueAsString(cv));
		}
		
		// build HTML output
		/*PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<html><body>");
		String id = request.getParameter("id");
		if (null == id) {
			out.println("<h2>Error</h2><p>Parameter id missing!</p>" + cvString); } 
		else {
			try {
			runtimeService.createMessageCorrelation("continueMessage") .processInstanceId(id).correlate();
			out.println("<h1>CVs delivered to process</h1><p>ID: " + id + "</p>" + cvString); }
			catch (MismatchingMessageCorrelationException e) {
			out.println("<h2>Error</h2><p>No correlating process instance.</p><p>" + id + "</p>");
			} }
			out.println("</body></html>");
	    out.close();*/
	  }
}

class CV {
	private String name;
	private String address;
	private String email;
	private String tel;
	private Calendar birthday;
	private String study;
	private String degree;
	private String grade;
	private String sectors;
	private String motivation;
	private String skills;
	private String experience;
	private Boolean isAccepted;
	private String rating;

	public CV() {
		this.name = "Peter Pan";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public String getStudy() {
		return study;
	}

	public void setStudy(String study) {
		this.study = study;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSectors() {
		return sectors;
	}

	public void setSectors(String sectors) {
		this.sectors = sectors;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}
	
	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
}


/*
class CV {

    private String instanceID;
    private String name;
    private String firstname;
    private String address;
    private String email;
    private Calendar birthday;
    private String study;
    private String degree;
    private String grade;
    private String sectors;
    private String specialSkills;
    private String practicalExperience;
    private int rating;
    private Calendar receivedOn;

    public CV() {
        //the famous empty constructor for Jackson
    }

    public CV(String instanceID,String name, String address,String email,Calendar birthday,String study,String degree,String grade,String sectors,String specialSkills,String practicalExperience,int rating ) {
      
    }

	public String getInstanceID() {
		return instanceID;
	}

	public void setInstanceID(String instanceID) {
		this.instanceID = instanceID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public String getStudy() {
		return study;
	}

	public void setStudy(String study) {
		this.study = study;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSectors() {
		return sectors;
	}

	public void setSectors(String sectors) {
		this.sectors = sectors;
	}

	public String getSpecialSkills() {
		return specialSkills;
	}

	public void setSpecialSkills(String specialSkills) {
		this.specialSkills = specialSkills;
	}

	public String getPracticalExperience() {
		return practicalExperience;
	}

	public void setPracticalExperience(String practicalExperience) {
		this.practicalExperience = practicalExperience;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int raing) {
		this.rating = raing;
	}

	public Calendar getReceivedOn() {
		return receivedOn;
	}

	public void setReceivedOn(Calendar receivedOn) {
		this.receivedOn = receivedOn;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Name="+getName()+"\n");
		return sb.toString();
	}

}
*/