package chatserver.threadtasks;

import chatserver.ChatServer;
import chatserver.ClientData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsonforms.ResponseForm;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class WriteRunner implements Runnable {

    Selector selector = null;
    ChatServer server = null;
    ObjectMapper mapper;

    public WriteRunner(Selector selector, ChatServer server) {
        this.selector = selector;
        this.server = server;
        mapper = new ObjectMapper();
    }

    private void privateWrite(ResponseForm form, Set<SelectionKey> selectedKeys) {
        ClientData data = server.getSessions().get(form.getTo());
        if (data != null) {
            SocketChannel client = data.getChannel();

            if (selectedKeys.contains(client.keyFor(selector))) {
                try {
                    server.getWriteBuffer().put(mapper.writeValueAsString(form).getBytes());
                    server.getWriteBuffer().flip();
                    client.write(server.getWriteBuffer());
                    server.getWriteBuffer().clear();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void publicWrite(ResponseForm form, Set<SelectionKey> selectedKeys) {

        try {
            server.getWriteBuffer().put(mapper.writeValueAsString(form).getBytes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Iterator<SelectionKey> iterator = selectedKeys.iterator();
        server.getWriteBuffer().flip();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            SocketChannel client = (SocketChannel) key.channel();
            try {
                client.write(server.getWriteBuffer());
            } catch (IOException e) {
                e.printStackTrace();
            }
            iterator.remove();
        }
        server.getWriteBuffer().clear();

    }

    @Override
    public void run() {
        while (true) {
            if (server.getMessageQueue().size() > 0) {
                ResponseForm currentForm = server.getMessageQueue().peek();
                if (currentForm != null) {
                    if (selector.isOpen()) {
                        try {
                            selector.select();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        if (currentForm.isPrivate()) {
                            privateWrite(currentForm, selectedKeys);
                        } else {
                            publicWrite(currentForm, selectedKeys);
                        }
                        server.getMessageQueue().remove();
                    }
                } else {
                    Thread.yield();
                }
            } else {
                Thread.yield();
            }
        }
    }
}
