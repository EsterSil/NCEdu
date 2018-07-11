package chatserver.threadtasks;

import chatserver.ChatServer;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class WriteRunner implements Runnable {

    Selector selector = null;
    ChatServer server = null;
    @Override
    public void run() {
        while (true) {
            if (server.getBufferSize().get() > 0) {
                if (selector.isOpen()) {
                    try {
                        selector.select();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectedKeys.iterator();
                    while (iter.hasNext()) {

                        SelectionKey key = iter.next();

                        iter.remove();
                    }
                } else {
                    Thread.yield();
                }
            }
        }
    }
}
