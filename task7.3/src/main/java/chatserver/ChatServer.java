package chatserver;

import chatserver.threadtasks.AcceptorRunner;
import chatserver.threadtasks.ReadRunner;
import chatserver.threadtasks.WriteRunner;
import jsonforms.ResponseForm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatServer {

    private ConcurrentMap<String, ClientData> sessions;
    private BlockingQueue<ResponseForm> messageQueue;

    private BlockingQueue<SocketChannel> unhandledConnectionsToRead;
    private BlockingQueue<SocketChannel> unhandledConnectionsToWrite;
    private BlockingQueue<SocketChannel> deleteToWrite;



    public BlockingQueue<SocketChannel> getUnhandledConnectionsToRead() {
        return unhandledConnectionsToRead;
    }

    public BlockingQueue<SocketChannel> getUnhandledConnectionsToWrite() {
        return unhandledConnectionsToWrite;
    }

    public BlockingQueue<ResponseForm> getMessageQueue() {
        return messageQueue;
    }

    public ChatServer() {
        this.sessions = new ConcurrentHashMap<>();
        unhandledConnectionsToRead = new LinkedBlockingQueue<>();
        unhandledConnectionsToWrite = new LinkedBlockingQueue<>();
        messageQueue = new LinkedBlockingQueue<>();
    }

    public ConcurrentMap<String, ClientData> getSessions() {
        return sessions;
    }

    public boolean addNewSession(ClientData data) {
        if (sessions.containsKey(data.getName())){
            return false;
        }
        sessions.put(data.getName(), data);
        return true;
    }


    public void start() {
        System.out.println(" SERVER MAIN ============ is started");
        Thread acceptorThread = new Thread(new AcceptorRunner(this));
        Thread readerThread = new Thread(new ReadRunner(this));
        Thread writeThread = new Thread(new WriteRunner(this));
        acceptorThread.start();
        readerThread.start();
        writeThread.start();
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

