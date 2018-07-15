package chatclient;

import jsonforms.RequestForm;

import java.util.Scanner;

public class ClientConsole implements Runnable {
    ChatClient client;

    public ClientConsole(ChatClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String line = "";
        while( !line.equals("exit")){
            line = scanner.nextLine();
            line = line.trim();
            if (line.startsWith("@")) {
                String words[] = line.split(" ");
                String targetNickName = words[0].substring(1);
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i < words.length; i++) {
                    builder.append(words[i]);
                }
                RequestForm request = new RequestForm(client.getNickName(), builder.toString(), targetNickName);
                client.getMessageQueue().add(request);
            } else {
                RequestForm request = new RequestForm(client.getNickName(), line, null);
                client.getMessageQueue().add(request);
            }
        }
    }
}
