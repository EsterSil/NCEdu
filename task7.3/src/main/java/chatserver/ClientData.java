package chatserver;

import java.nio.channels.SocketChannel;

public class ClientData {
    int clientID;
    String name;
    int sessionID;


    SocketChannel channel;

    public ClientData() {

    }


    public ClientData(String name, SocketChannel channel) {
        this.name = name;
        this.channel = channel;
    }

    public ClientData(int clientID, String name, int sessionID, SocketChannel channel) {

        this.clientID = clientID;
        this.name = name;
        this.sessionID = sessionID;
        this.channel = channel;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }
}
