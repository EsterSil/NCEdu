package chatclient.threadtasks;

import chatclient.ChatClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsonforms.ResponseForm;

import java.io.IOException;
import java.nio.ByteBuffer;


/**
 * class provides the task for reader thread
 * class deserialize json formatted strings of responses from server and prints result to console
 */
public class ClientReader implements Runnable {

    private ByteBuffer readBuffer;
    /**
     * central thread-keeping class
     */
    private ChatClient client;
    /**
     * flag to stop the process loop in case of exceptions
     */
    private boolean isInterrupted = false;

    /**
     * public runner constructor, initializes all necessary fields and saves the object of thread-parent class
     *
     * @param client parent thread's object
     */
    public ClientReader(ChatClient client) {
        this.client = client;
        readBuffer = ByteBuffer.allocate(2048);
    }

    /**
     * overridden method to be called in that separately executing
     * thread.
     */
    @Override
    public void run() {
        ResponseForm response = null;
        while (!isInterrupted) {
            try {
                client.getChannel().read(readBuffer);
            } catch (IOException e) {
                isInterrupted = true;
                System.out.println("Server disconnected");
            }
            if (readBuffer.position() > 0) {
                String json = new String(readBuffer.array());
                readBuffer.clear();
                if (!json.equals("")) try {
                    response = new ObjectMapper().readerFor(ResponseForm.class).readValue(json);
                    if (response.getFrom().equals("SERVER")
                            && response.getMessage().equals("OK")) {
                        client.setIsRegisterTo(true);
                        System.out.println("______________________________________________________________");
                    } else {
                        if ((!response.isPrivate() && !response.getFrom().equals(client.getNickName()))
                                || (response.isPrivate())) {
                            System.out.println(response.toString());
                        }
                    }
                } catch (IOException e) {
                    isInterrupted = true;
                    e.printStackTrace();
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
