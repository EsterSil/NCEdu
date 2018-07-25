package chatclient;

import chatclient.threadtasks.ClientConsole;
import chatclient.threadtasks.ClientReader;
import chatclient.threadtasks.ClientWriter;
import jsonforms.RequestForm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatClient {
    /**
     * associated channel
     */
    private  SocketChannel channel;
    /**
     * concurrent message queue, common for all threads
     */
    private BlockingQueue<RequestForm> messageQueue;
    /**
     * unique identificator for this client
     */
    private String nickName;
    /**
     * flag indicates if this client is registered on server
     */
    private AtomicBoolean isRegister;
    /**
     * public  constructor, initializes all necessary fields
     */
    public ChatClient() {
        messageQueue = new LinkedBlockingQueue<>();
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect( new InetSocketAddress("localhost", 8080));
            while(!channel.finishConnect()){
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        isRegister = new AtomicBoolean(false);
    }
    
    /**
     * method starts all processor's threads and sleeps
     * also it perform initial registration on server by unique nickName
     */
    public void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to chat! Enter your nickname: ");
        Thread writeThread = new Thread(new ClientWriter(this));
        Thread readThread = new Thread(new ClientReader(this));
        Thread consoleThread = new Thread(new ClientConsole(this));
        writeThread.start();
        readThread.start();
        consoleThread.start();
        while(!getIsRegister()) {
            nickName = scanner.next();
            messageQueue.add(new RequestForm(nickName, "", null));
            try {
                Thread.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            writeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * setter & getter for atomic boolean field by its meaning
     *
     */
    public boolean getIsRegister() {
        return isRegister.get();
    }

    public void setIsRegisterTo(boolean isRegister) {
        this.isRegister.set(isRegister);
    }
    /**
     * necessary usual getters and setters for fields
     *
     */
    public SocketChannel getChannel() {
        return channel;
    }

    public String getNickName() {
        return nickName;
    }

    public BlockingQueue<RequestForm> getMessageQueue() {
        return messageQueue;
    }

}
