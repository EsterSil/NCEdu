package chatserver;

import chatserver.threadtasks.AcceptorRunner;
import chatserver.threadtasks.ReadRunner;
import chatserver.threadtasks.WriteRunner;
import jsonforms.ResponseForm;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * basic class for chat server implementation
 */
public class ChatServer {

    /**
     * concurrent map to held pairs of client nickName as key and ClientData container as value for all the client
     * available in the moment
     */
    private ConcurrentMap<String, ClientData> sessions;
    /**
     * concurrent message queue, common for all threads
     */
    private BlockingQueue<ResponseForm> messageQueue;

    /**
     * this two queue are intended to provide reader and writer threads with new connections
     * each thread has its own container to keep FIFO principle simultaneously in each one and not to interrupt workflow
     */
    private BlockingQueue<SocketChannel> unhandledConnectionsToRead;
    private BlockingQueue<SocketChannel> unhandledConnectionsToWrite;


    /**
     * public  constructor, initializes all necessary fields
     */
    public ChatServer() {
        this.sessions = new ConcurrentHashMap<>();
        unhandledConnectionsToRead = new LinkedBlockingQueue<>();
        unhandledConnectionsToWrite = new LinkedBlockingQueue<>();
        messageQueue = new LinkedBlockingQueue<>();
    }

    /**
     * method delete session by its channel
     * @param channel broken channel to be deleted
     * @return ClientData of deleted client if success
     *         null if it doesn't  exist
     */
    public ClientData deleteSession(SocketChannel channel){
        Iterator<ClientData> iterator =sessions.values().iterator();
        ClientData data = null;
        while (iterator.hasNext()){
            data = iterator.next();
            if (data.getChannel().equals(channel)){
                sessions.remove(data.getName());
                return data;
            }
        }
        return null;
    }

    /**
     * method add new session using ClientData to fulfill the map
     * @param data filled ClientData container
     * @return true if success
     *         false if such key is contained in the map
     */
    public boolean addNewSession(ClientData data) {
        if (sessions.containsKey(data.getName())){
            return false;
        }
        sessions.put(data.getName(), data);
        return true;
    }

    /**
     * method starts all processor's threads and sleeps
     */
    public void start() {
        System.out.println("============ SERVER MAIN  is started");
        Thread acceptorThread = new Thread(new AcceptorRunner(this));
        Thread readerThread = new Thread(new ReadRunner(this));
        Thread writeThread = new Thread(new WriteRunner(this));
        acceptorThread.start();
        readerThread.start();
        writeThread.start();
        boolean isInterrupted = false;
        while (!isInterrupted) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                isInterrupted = true;
                e.printStackTrace();
            }
        }
    }


    /**
     * necessary usual getters and setters for fields
     *
     */
    public BlockingQueue<SocketChannel> getUnhandledConnectionsToRead() {
        return unhandledConnectionsToRead;
    }
    public BlockingQueue<SocketChannel> getUnhandledConnectionsToWrite() {
        return unhandledConnectionsToWrite;
    }
    public BlockingQueue<ResponseForm> getMessageQueue() {
        return messageQueue;
    }
    public ConcurrentMap<String, ClientData> getSessions() {
        return sessions;
    }
}

