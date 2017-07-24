package org.camunda.wf.hiring.entities;


import java.util.Date;

/**
 * This class provides an Request Object
 *
 */
public class Request {

		private Date deadline;
		private String processInstanceId;	

		public Request(String processInstanceId, Date deadline) {
			this.deadline = deadline;
			this.processInstanceId = processInstanceId;
		}

		public String getProcessInstanceId() {
			return processInstanceId;
		}

		public void setProcessInstanceId(String processInstanceId) {
			this.processInstanceId = processInstanceId;
		}

		public Date getDeadline() {
			return deadline;
		}

		public void setDeadline(Date deadline) {
			this.deadline = deadline;
		}

	}

