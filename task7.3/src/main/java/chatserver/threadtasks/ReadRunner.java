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

public class ReadRunner implements Runnable {
    private ByteBuffer readBuffer;
    private Selector readSelector = null;
    private ChatServer server = null;
    private boolean isInterrupted = false;

    public ReadRunner(ChatServer server) {
        try {
            this.readSelector = Selector.open(); //server.getSelector();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readBuffer = ByteBuffer.allocate(2048);
        this.server = server;
    }
    private void formulateResponse(RequestForm request, SocketChannel client){
        ResponseForm response = null;
        if (request.getMessage().equals("")) {
            if (server.addNewSession(new ClientData(request.getNickName(), client))) {
                response = new ResponseForm(request.getNickName() + " joined the chat", "SERVER", false, null);
            } else{
                response = new ResponseForm("Login is already in use", "SERVER", true, request.getNickName() );
            }
        } else {
            if (request.getTo() != null){
                response = new ResponseForm(request.getMessage(), request.getNickName(), true, request.getTo());
            } else {
                response = new ResponseForm(request.getMessage(), request.getNickName(), false, null);
            }
        }
        server.getMessageQueue().add(response);
    }


    private void readFromChannel(ByteBuffer buffer, SelectionKey key) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(" SERVER READER//////////////// read from channel");
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        String json = new String(buffer.array());
        buffer.clear();
        System.out.println(" SERVER READER//////////////// read from channel " + json);
        RequestForm request = new ObjectMapper().readerFor(RequestForm.class).readValue(json);
        formulateResponse(request, client);
    }


    private void selectorUpDate() throws ClosedChannelException {
        //System.out.println(" SERVER READER///////////////// selector upd ");
        //System.out.println(" SERVER READER///////////////// selector upd  " + server.getUnhandledConnectionsToRead().size());
        while (server.getUnhandledConnectionsToRead().size() > 0) {
            //System.out.println(" SERVER READER///////////////// selector upd  " + server.getUnhandledConnectionsToRead().size());
            SocketChannel channel = server.getUnhandledConnectionsToRead().remove();
            channel.register(readSelector, SelectionKey.OP_READ);
            //System.out.println(" SERVER READER///////////////// selector upd registered ");
            //readSelector.keys().remove(channel.keyFor(readSelector));
        }

    }

    private void deleteConnection(){

    }
    @Override
    public void run() {
        System.out.println(" SERVER READER///////////////// is started ");
        while (!isInterrupted) {
            try {
                selectorUpDate();
            } catch (ClosedChannelException e) {

                e.printStackTrace();
            }
            int readReady = 0;
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
                        System.out.println(" SERVER READER///////////////// iter ");
                        SelectionKey key = iterator.next();
                        if (key.isReadable()) {
                            System.out.println(" SERVER READER//////////////// key" + selectedKeys.size());
                            try {
                                readFromChannel(readBuffer, key);
                            } catch (IOException e) {
                                //isInterrupted = true;
                                /// delete sock
                                e.getCause();
                                //e.printStackTrace();
                            }
                        }
                        iterator.remove();
                    }
                }

            } else {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
