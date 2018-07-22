package chatserver.threadtasks;

import chatserver.ChatServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class AcceptorRunner implements Runnable {
    Selector selector = null;
    ChatServer server = null;

    public AcceptorRunner(ChatServer server) {
        try {
            this.selector =  Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.server = server;
    }

    @Override
    public void run() {

        System.out.println(" SERVER ACCEPTOR^^^^^^^^^^^^^^^^ is started ");
        ServerSocketChannel serverSocket = null;

        try {
            System.out.println(" SERVER ACCEPTOR^^^^^^^^^^^^^^^^ serv sock is opening...");
            serverSocket = ServerSocketChannel.open();
            System.out.println(" SERVER ACCEPTOR^^^^^^^^^^^^^^^^ serv sock is open "+ serverSocket.isOpen());
            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println(" SERVER ACCEPTOR^^^^^^^^^^^^^^^^ serv sock registred ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (selector.isOpen()) {

            try {
                selector.selectNow();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SelectionKey key = serverSocket.keyFor(selector);

            if (key != null) {
                if (key.isAcceptable()) {
                    //System.out.println(" SERVER ACCEPTOR^^^^^^^^^^^^^^^ key is acceptable");
                    try {
                        register(serverSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println(" SERVER ACCEPTOR^^^^^^^^^^^^^^^ selector is closed");
    }

    private void register(ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        if (client != null) {
            System.out.println(" SERVER ACCEPTOR^^^^^^^^^^^^^^^^ cli" + client.toString());
            client.configureBlocking(false);
            System.out.println(" SERVER ACCEPTOR^^^^^^^^^^^^^^^^ selector" + selector.toString());
            server.getUnhandledConnectionsToWrite().add(client);
            server.getUnhandledConnectionsToRead().add(client);
            System.out.println(" SERVER ACCEPTOR^^^^^^^^^^^^^^^^ register success");
            System.out.println();

        }

    }
}
