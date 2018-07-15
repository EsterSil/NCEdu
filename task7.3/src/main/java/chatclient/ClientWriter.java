package chatclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsonforms.RequestForm;

import java.io.IOException;

public class ClientWriter implements Runnable {

    ChatClient client;
    ObjectMapper mapper;

    public ClientWriter(ChatClient client) {
        this.client = client;
        mapper = new ObjectMapper();
    }



    @Override
    public void run() {
        while (true) {
            if (client.getMessageQueue().size() > 0) {
                RequestForm request = client.getMessageQueue().peek();
                //RequestForm request = new RequestForm(client.getNickName(), message, null);
                try {
                    client.getWriteBuffer().put(mapper.writeValueAsString(request).getBytes());
                    client.getWriteBuffer().flip();
                    client.getClient().write(client.getWriteBuffer());
                    client.getWriteBuffer().clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
