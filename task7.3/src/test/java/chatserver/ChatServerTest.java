package chatserver;

import org.junit.Test;

public class ChatServerTest {

    @Test
    public void serverStartTest(){
        ChatServer server = new ChatServer();
        server.start();
    }
}
