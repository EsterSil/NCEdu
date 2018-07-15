package chatclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsonforms.RequestForm;
import jsonforms.ResponseForm;


import java.io.IOException;

public class ClientReader implements Runnable {
    ChatClient client;

    public ClientReader(ChatClient client) {
        this.client = client;
    }


    @Override
    public void run() {
        try {
            client.getClient().read(client.getReadBuffer());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String json = new String(client.getReadBuffer().array());
        client.getReadBuffer().clear();
        try {
            ResponseForm response = new ObjectMapper().readerFor(RequestForm.class).readValue(json);
            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
