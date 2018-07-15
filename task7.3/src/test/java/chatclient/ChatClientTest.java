package chatclient;

import org.junit.Test;

public class ChatClientTest {
    @Test
    public void clientStartTest(){
        ChatClient client = new ChatClient();
        client.start();
    }
}
