package chatclient;

import chatserver.threadtasks.WriteRunner;
import jsonforms.RequestForm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatClient {
    private  SocketChannel client;
    private  ByteBuffer writeBuffer;
    private  ByteBuffer readBuffer;
    private BlockingQueue<RequestForm> messageQueue;
    private String nickName;


    public ChatClient() {
        messageQueue = new LinkedBlockingQueue<>();
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 8080));
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeBuffer = ByteBuffer.allocate(2048);
        readBuffer = ByteBuffer.allocate(2048);
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to chat! Enter your nickname ");
        nickName = scanner.next();

        Thread writeThread = new Thread(new ClientWriter(this));
        Thread readThread = new Thread(new ClientReader(this));
        Thread consoleThread = new Thread(new ClientConsole(this));
        writeThread.start();
        readThread.start();
        consoleThread.start();
        try {
            writeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public SocketChannel getClient() {
        return client;
    }

    public ByteBuffer getWriteBuffer() {
        return writeBuffer;
    }

    public String getNickName() {
        return nickName;
    }


    public BlockingQueue<RequestForm> getMessageQueue() {
        return messageQueue;
    }

    public ByteBuffer getReadBuffer() {
        return readBuffer;
    }



}
