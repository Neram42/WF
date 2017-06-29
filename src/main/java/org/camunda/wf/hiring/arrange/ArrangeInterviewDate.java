package org.camunda.wf.hiring.arrange;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

import java.net.URI;

import microsoft.exchange.webservices.data.*;
import microsoft.exchange.webservices.data.autodiscover.exception.AutodiscoverRemoteException;

public class ArrangeInterviewDate {

	
	 public static void main(String[] args) {
		 ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
		 ExchangeCredentials credentials = new WebCredentials("hr_employee@outlook.de", "HRemployee");
		 service.setCredentials(credentials);
		 try {
			//service.autodiscoverUrl("hr_employee@outlook.de");
			 service.setUrl(new URI("https://outlook.com/EWS/Exchange.asmx"));
		} catch (Exception e) {
			System.out.print("klappt nicht");
			e.printStackTrace();
		}
		 
		 
		 EmailMessage msg;
		try {
			msg = new EmailMessage(service);
			 msg.setSubject("Hello world!");
			 msg.setBody(MessageBody.getMessageBodyFromText("Sent using the EWS Java API."));
			 msg.getToRecipients().add("t_soll03@uni-muenster.de");
			 msg.send();
			 System.out.println("Mail gesendet");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
}
