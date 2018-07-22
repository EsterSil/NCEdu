package chatserver.threadtasks;

import chatserver.ChatServer;
import chatserver.ClientData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsonforms.ResponseForm;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class WriteRunner implements Runnable {

    private Selector writeSelector = null;
    private ChatServer server = null;
    private ObjectMapper mapper;
    private boolean isInterrupted = false;
    private ByteBuffer writeBuffer;
    public WriteRunner(ChatServer server) {
        try {
            this.writeSelector = Selector.open();// server.getSelector();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeBuffer = ByteBuffer.allocate(2048);
        this.server = server;
        mapper = new ObjectMapper();
    }

    private void selectorUpDate() throws ClosedChannelException {
        while (server.getUnhandledConnectionsToWrite().size() > 0) {
            SocketChannel channel = server.getUnhandledConnectionsToWrite().remove();
            channel.register(writeSelector, SelectionKey.OP_WRITE);
        }
    }

    private void privateWrite(ResponseForm form, Set<SelectionKey> selectedKeys) throws IOException, NoSuchElementException {
        System.out.println(" SERVER WRITER>>>>>>>>>>>>>>>>> private is on ");
        System.out.println(" SERVER WRITER>>>>>>>>>>>>>>>>> private to " + form.getTo());
        System.out.println(" SERVER WRITER>>>>>>>>>>>>>>>>> private size " + server.getSessions().size());
        ClientData data = server.getSessions().get(form.getTo());
        if (data != null) {
            System.out.println(" SERVER WRITER>>>>>>>>>>>>>>>>> private data for " + data.getName());
            SocketChannel client = data.getChannel();
            //int i = selector.selectNow();
            //Set<SelectionKey> selectedKeys = selector.selectedKeys();
            System.out.println(" SERVER WRITER>>>>>>>>>>>>>>>>> private selector  " +  " " + selectedKeys.size());
            if (selectedKeys.contains(client.keyFor(writeSelector))) {
                System.out.println(" SERVER WRITER>>>>>>>>>>>>>>>>> private json  " + mapper.writeValueAsString(form));
                writeBuffer.put(mapper.writeValueAsString(form).getBytes());
                writeBuffer.flip();
                client.write(writeBuffer);
                writeBuffer.clear();
            }
        } else {
            System.out.println(" data null");
        }
    }

    private void publicWrite(ResponseForm form, Set<SelectionKey> selectedKeys) throws IOException {
        System.out.println(" SERVER WRITER>>>>>>>>>>>>>>>>>  public");
        try {
            writeBuffer.put(mapper.writeValueAsString(form).getBytes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Iterator<SelectionKey> iterator = selectedKeys.iterator();
        writeBuffer.flip();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            SocketChannel client = (SocketChannel) key.channel();
            client.write(writeBuffer);
            iterator.remove();
        }
        writeBuffer.clear();
    }

    @Override
    public void run() {
        System.out.println(" SERVER WRITER>>>>>>>>>>>>>>>>> is started");
        while (!isInterrupted) {
            if (server.getMessageQueue().size() > 0) {
                System.out.println(" SERVER WRITER>>>>>>>>>>>>>>>>> get mess");
                ResponseForm currentForm = server.getMessageQueue().remove();
                if (currentForm != null) {
                    try {
                        selectorUpDate();
                    } catch (ClosedChannelException e) {
                        e.printStackTrace();
                    }

                    try {
                        writeSelector.select();
                    } catch (IOException e) {
                        isInterrupted = true;
                        e.printStackTrace();
                    }

                    Set<SelectionKey> selectedKeys = writeSelector.selectedKeys();
                    System.out.println(" SERVER WRITER>>>>>>>>>>>>>>>>> selector " + selectedKeys.size());
                    if (currentForm.isPrivate()) {

                        try {
                            privateWrite(currentForm, selectedKeys);
                        } catch (IOException e) {
                            isInterrupted = true;
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            publicWrite(currentForm, selectedKeys);
                        } catch (IOException e) {
                            isInterrupted = true;
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                Thread.yield();
            }

        }
    }
}
