package jettytest.server;

import jettytest.server.HttpServer;

public class ServerMain {

    public static void main(String [] args){
        HttpServer server = new HttpServer();
        server.start();
    }
}
