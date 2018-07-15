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

    private static final String POISON_PILL = "POISON_PILL";

    private ConcurrentMap<String, ClientData> sessions;
    private BlockingQueue<ResponseForm> messageQueue;

    private  ByteBuffer readBuffer;
    private  ByteBuffer writeBuffer;


    private AtomicInteger bufferSize;
    private AtomicInteger connectionNumber;


    public BlockingQueue<ResponseForm> getMessageQueue() {
        return messageQueue;
    }

    public AtomicInteger getConnectionNumber() {
        return connectionNumber;

    }

    public ChatServer() {
        this.sessions = new ConcurrentHashMap<>();
        readBuffer = ByteBuffer.allocate(2048);
        writeBuffer = ByteBuffer.allocate(2048);
        bufferSize = new AtomicInteger(0);
        connectionNumber = new AtomicInteger(0);
        messageQueue = new LinkedBlockingQueue<>();
    }

    public ConcurrentMap<String, ClientData> getSessions() {
        return sessions;
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }

    public ByteBuffer getWriteBuffer() {
        return writeBuffer;
    }

    public AtomicInteger getBufferSize() {
        return bufferSize;
    }

    public void addNewSession(ClientData data) {
        sessions.put(data.getName(), data);
    }



    public void start(){
        Selector selector = null;
        ServerSocketChannel serverSocket = null;

        try {
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread acceptorThread = new Thread(new AcceptorRunner(selector, this));
        Thread readerThread = new Thread (new ReadRunner(selector, this));
        Thread writeThread = new Thread (new WriteRunner(selector, this));
        acceptorThread.start();
        readerThread.start();
        writeThread.start();

        try {
            acceptorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

