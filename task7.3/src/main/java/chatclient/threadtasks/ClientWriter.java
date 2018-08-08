package chatclient.threadtasks;

import chatclient.ChatClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsonforms.RequestForm;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * class provides the task for writer thread
 * pop requests from message queue, serialize them and send to server
 */
public class ClientWriter implements Runnable {
    /**
     * buffer to write to socket
     */
    private ByteBuffer writeBuffer;
    /**
     * central thread-keeping class
     */
    private ChatClient client;

    /**
     * json mapper object
     */
    private ObjectMapper mapper;
    /**
     * flag to stop the process loop in case of exceptions
     */
    private boolean isInterrupted = false;

    /**
     * public runner constructor, initializes all necessary fields and saves the object of thread-parent class
     *
     * @param client parent thread's object
     */
    public ClientWriter(ChatClient client) {
        this.client = client;
        mapper = new ObjectMapper();
        writeBuffer = ByteBuffer.allocate(2048);
    }

    /**
     * overridden method to be called in that separately executing
     * thread.
     */
    @Override
    public void run() {
        while (!isInterrupted) {
            if (client.getMessageQueue().size() > 0) {
                RequestForm request = client.getMessageQueue().remove();
                try {
                    writeBuffer.put(mapper.writeValueAsString(request).getBytes());
                    writeBuffer.flip();
                    client.getChannel().write(writeBuffer);
                    writeBuffer.clear();
                } catch (IOException e) {
                    isInterrupted = true;
                    System.out.println("Server disconnected");
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    isInterrupted = true;
                    e.printStackTrace();
                }
            }
        }
    }
}
