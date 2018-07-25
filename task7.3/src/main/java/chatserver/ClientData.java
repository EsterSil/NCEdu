package chatserver;

import java.nio.channels.SocketChannel;

/**
 * class-container with related client data
 * have only necessary getters and setters
 * name - client nickname
 * channel - socket associated with this client
 */

public class ClientData {

    String name;
    SocketChannel channel;


    public SocketChannel getChannel() {
        return channel;
    }

    public ClientData(String name, SocketChannel channel) {
        this.name = name;
        this.channel = channel;
    }

    public String getName() {
        return name;
    }
}

