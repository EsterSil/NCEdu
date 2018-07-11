package chatserver.threadtasks;

import chatserver.ChatServer;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class AcceptorRunner implements Runnable {
    Selector selector = null;
    ChatServer server = null;

    public AcceptorRunner(Selector selector, ChatServer server) {
        this.selector = selector;
        this.server = server;
    }

    @Override
    public void run() {
        ServerSocketChannel serverSocket = null;


        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {

                SelectionKey key = iter.next();

                if (key.isAcceptable()) {
                    try {
                        register(serverSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private  void register( ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        client.register(selector, SelectionKey.OP_WRITE);
        //ClientData data  = new ClientData(server.getConnectionNumber().incrementAndGet(), client);
        //server.addNewSession(data);
    }
}
