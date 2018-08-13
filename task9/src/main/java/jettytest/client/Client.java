package jettytest.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class Client {

    //HttpClient httpClient = null;
    public final String serverURL = "localhost:8080";

    private String post(String message){
        PostMethod post = new PostMethod(serverURL);
        post.setRequestHeader(
                "Content-type", "text/xml");
        post.setRequestEntity( );
    }


    public void start(){
        HttpClient httpClient = new HttpClient();


        try {
            httpClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RegisterRequest request = new RegisterRequest();
        request.setLogin("9067411654");
        request.setPassword("password");
        request.setType("registerCustomer");
        XMLConverter converter = new XMLConverter();
        String message = converter.serialize(request, RegisterRequest.class);
        String answer = post(message);
    }


}
