package chatclient.threadtasks;

import chatclient.ChatClient;
import jsonforms.RequestForm;
import java.util.Scanner;


/**
 * class provides the task for console thread
 * console loop handle input client commands as:
 *      <i>some text with spaces</i>   as public message to all users in chat
 *      <i>@someNickname some text with spaces started with @</i>   as private message to user
 *                                                                 with nickname <quote> someNickname</quote>
 *  according this order class formulate requests to server and push them to message queue then writer thread could send
 */
public class ClientConsole implements Runnable {
    private ChatClient client;
    private boolean isInterrupted = false;
    public ClientConsole(ChatClient client) {
        this.client = client;
    }

    /**
     * overridden method to be called in that separately executing
     * thread.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String line = "";
        while (!isInterrupted) {
            if (client.getIsRegister()) {
                line = scanner.nextLine();
                line = line.trim();
                if (line.equals("exit")){
                    break;
                }
                RequestForm request = null;
                if (line.startsWith("@")) {
                    String[] words = line.split(" ");
                    String targetNickName = words[0].substring(1);
                    StringBuilder builder = new StringBuilder();
                    for (int i = 1; i < words.length; i++) {
                        builder.append(words[i] + " ");
                    }
                    request = new RequestForm(client.getNickName(), builder.toString(), targetNickName);
                } else {
                    request = new RequestForm(client.getNickName(), line, null);
                }
                client.getMessageQueue().add(request);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                isInterrupted = true;
                e.printStackTrace();
            }
        }
    }
}
