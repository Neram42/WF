package org.camunda.wf.hiring.sendMessages;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
public class ProvideJobInformation implements JavaDelegate {
    public void execute(DelegateExecution execution) throws Exception {
        // build HTTP post with all variables as parameters
        HttpClient client = HttpClientBuilder.create().build();
//      RequestBuilder requestBuilder = RequestBuilder.get()
////                .setUri("https://requestb.in/https://requestb.in/1iv9qco1")
//              .addParameter("id", execution.getProcessInstanceId());
//      for (String variable : execution.getVariableNames()) {
//          requestBuilder.addParameter(variable, String.valueOf(execution.getVariable(variable)));
//          }
//      // execute request
//      HttpUriRequest request = requestBuilder.build();
//      HttpResponse response = client.execute(request);
        
        String id = execution.getProcessInstanceId();
        String department = (String) execution.getVariable("department");
        String title = (String) execution.getVariable("job_position");
        String requirements = (String) execution.getVariable("requirements");
        String description = (String) execution.getVariable("job_description");
        String deadline = (String) execution.getVariable("date");
//      String candidateProfile = (String) execution.getVariable("candidateProfile");
        
        String JSON = "{ \"department\": \""+ department +"\",";
        JSON = JSON + "\"processId\": \""+ id +"\",";
        JSON = JSON + "\"title\": \""+ title +"\",";
        JSON = JSON + "\"requiredGraduation\": \""+ requirements +"\",";
        JSON = JSON + "\"candidateProfile\": \""+ description +"\",";
        JSON = JSON + "\"deadline\": \""+ deadline +"\"}";
        
        
        String postURL = "http://";
        try{
        HttpPost post = new HttpPost(postURL);
        StringEntity postString = new StringEntity(JSON);
        
        post.setHeader("content-type", "application/json");
        post.setEntity(postString);
        client.execute(post);
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
}