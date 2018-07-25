package chatserver.threadtasks;

import chatserver.ChatServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;


/**
 * class provides the task for thread accepting new connections
 */
public class AcceptorRunner implements Runnable {
    /**
     * local selector associated with listen socket, bound to localhost port 8080
     */
    private Selector selector = null;

    /**
     * central thread-keeping class
     */
    private ChatServer server = null;

    /**
     * public runner constructor, initializes all necessary fields and saves the object of thread-parent class
     *
     * @param server parent thread's object
     */
    public AcceptorRunner(ChatServer server) {
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.server = server;
    }

    /**
     * inner method accepts new connection and registers it
     *
     * @param serverSocket
     * @throws IOException
     */
    private void register(ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        if (client != null) {
            client.configureBlocking(false);
            server.getUnhandledConnectionsToWrite().add(client);
            server.getUnhandledConnectionsToRead().add(client);
            System.out.println(" SERVER ACCEPTOR:: new connection registered successfully");
        }
    }

    /**
     * overridden method to be called in that separately executing
     * thread.
     */
    @Override
    public void run() {
        System.out.println("=========== SERVER ACCEPTOR is started ");
        ServerSocketChannel serverSocket = null;
        try {
            System.out.println(" SERVER ACCEPTOR:: server's socket is opening...");
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println(" SERVER ACCEPTOR:: server's socket registered ");
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
                    try {
                        register(serverSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
