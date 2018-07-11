package chatserver;

import chatserver.threadtasks.AcceptorRunner;
import chatserver.threadtasks.ReadRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatServer {

    private static final String POISON_PILL = "POISON_PILL";

    private BlockingQueue<ClientData> sessions;

    private  ByteBuffer readBuffer;
    private  ByteBuffer writeBuffer;

    private AtomicInteger bufferSize;
    private AtomicInteger connectionNumber;


    public AtomicInteger getConnectionNumber() {
        return connectionNumber;
    }

    public ChatServer() {
        this.sessions = new LinkedBlockingQueue<>();
        readBuffer = ByteBuffer.allocate(2048);
        writeBuffer = ByteBuffer.allocate(2048);
        bufferSize = new AtomicInteger(0);
        connectionNumber = new AtomicInteger(0);
    }

    public BlockingQueue<ClientData> getSessions() {
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
        sessions.add(data);
    }

    private  void answerWithEcho(ByteBuffer buffer, SelectionKey key)
            throws IOException {

        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        if (new String(buffer.array()).trim().equals(POISON_PILL)) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        }

        buffer.flip();
        client.write(buffer);
        buffer.clear();
    }



    public void start(){
        Selector selector = null;
        ServerSocketChannel serverSocket = null;

        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread acceptorThread = new Thread(new AcceptorRunner(selector, this));
        Thread readerThread = new Thread (new ReadRunner(selector, this));


    }
}

