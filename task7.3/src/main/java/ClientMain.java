import chatclient.ChatClient;

import java.io.IOException;

public class ClientMain {

    public static void main( String[] args){
        ChatClient client = new ChatClient();

        client.start();

    }
}
