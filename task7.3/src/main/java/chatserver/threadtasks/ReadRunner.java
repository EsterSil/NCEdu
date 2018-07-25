package chatserver.threadtasks;

import chatserver.ChatServer;
import chatserver.ClientData;
import jsonforms.RequestForm;
import jsonforms.ResponseForm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * this runnable class provides a task for server's reading thread
 */
public class ReadRunner implements Runnable {
    /**
     * buffer to read from clients' sockets
     */
    private ByteBuffer readBuffer;
    /**
     * local selector to reveal incoming messages from clients sockets
     */
    private Selector readSelector = null;
    /**
     * central thread-keeping class
     */
    private ChatServer server;
    /**
     * flag to stop the process loop in case of exceptions
     */
    private boolean isInterrupted = false;

    /**
     * public runner constructor, initializes all necessary fields and saves the object of thread-parent class
     *
     * @param server parent thread's object
     */
    public ReadRunner(ChatServer server) {
        try {
            this.readSelector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readBuffer = ByteBuffer.allocate(2048);
        this.server = server;
    }

    /**
     * inner method, contain the crucial logic of server's workflow. Here the server's response will be formulated and
     * than pushed to messageQueue. According to the request, response would be private( for one client ) or pubic
     * (for all the clients in current session)
     * If client send the register form, method adds it to current session
     *
     * @param request client request specified by RequestForm
     * @param client  channel of the client which the response is formulated to
     */
    private void formulateResponse(RequestForm request, SocketChannel client) {
        ResponseForm response = null;
        if (request.getMessage().equals("")) {
            if (server.addNewSession(new ClientData(request.getNickName(), client))) {
                server.getMessageQueue().add(new ResponseForm("OK", "SERVER", true, client));
                response = new ResponseForm(request.getNickName() + " joined the chat", "SERVER",
                        false, null);
            } else {
                response = new ResponseForm("Login is already in use", "SERVER", true, client);
            }
        } else {
            if (request.getTo() != null) {
                ClientData data =  server.getSessions().get(request.getTo());
                if(data != null) {
                    response = new ResponseForm(request.getMessage(), request.getNickName(), true, data.getChannel());

                } else{
                    server.getMessageQueue().add(new ResponseForm("NickName is unknown", "Server",
                            true, client));
                }
            } else {
                response = new ResponseForm(request.getMessage(), request.getNickName(), false, null);
            }
        }
        server.getMessageQueue().add(response);
    }

    /**
     * inner method to read message from readable socket channel
     *
     * @param buffer to read to
     * @param key    Selection key which was selected by readSelector with the option OP_READ
     * @throws IOException if buffer do
     */
    private void readFromChannel(ByteBuffer buffer, SelectionKey key) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        if (buffer.position() > 0) {
            String json = new String(buffer.array());
            buffer.clear();
            RequestForm request = mapper.readerFor(RequestForm.class).readValue(json);
            formulateResponse(request, client);
        }
    }

    /**
     * inner method updates local selector with new registered connection
     *
     * @throws ClosedChannelException
     */
    private void selectorUpDate() throws ClosedChannelException {
        SocketChannel channel = null;
        while (server.getUnhandledConnectionsToRead().size() > 0) {
            channel = server.getUnhandledConnectionsToRead().remove();
            channel.register(readSelector, SelectionKey.OP_READ);
            System.out.println(" SERVER READER:: selector updated ");
        }
    }

    /**
     * overridden method to be called in that separately executing
     * thread.
     */
    @Override
    public void run() {
        System.out.println("=========== SERVER READER is started ");
        int readReady;
        while (!isInterrupted) {
            try {
                selectorUpDate();
            } catch (ClosedChannelException e) {
                isInterrupted = true;
                e.printStackTrace();
            }
            readReady = 0;
            try {
                readReady = readSelector.selectNow();
            } catch (IOException e) {
                isInterrupted = true;
                e.printStackTrace();
            }
            if (readReady > 0) {
                Set<SelectionKey> selectedKeys = readSelector.selectedKeys();
                if (selectedKeys.size() != 0) {
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isReadable()) {
                            try {
                                readFromChannel(readBuffer, key);
                            } catch (IOException e) {
                                key.cancel();
                                ClientData data = server.deleteSession((SocketChannel) key.channel());
                                server.getMessageQueue().add(new ResponseForm(data.getName() + " leaves the chat",
                                        "SERVER", false, null));
                                readBuffer.clear();
                            }
                        }
                        iterator.remove();
                    }
                }
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    isInterrupted = true;
                }
            }
        }
    }
}
