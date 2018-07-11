package chatserver.threadtasks;

import chatserver.ChatServer;
import chatserver.ClientData;
import chatserver.jsonforms.RequestForm;
import chatserver.jsonforms.ResponseForm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ReadRunner implements Runnable {
    Selector selector = null;
    ChatServer server = null;

    public ReadRunner(Selector selector, ChatServer server) {
        this.selector = selector;
        this.server = server;
    }
    private void readFromChannel(ByteBuffer buffer, SelectionKey key) throws IOException{
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        String json = new String(buffer.array());
        ObjectMapper mapper = new ObjectMapper();


        RequestForm request = new ObjectMapper().readerFor(RequestForm.class).readValue(json);
        if(request.getMessage().equals("")){
            server.addNewSession( new ClientData(request.getNickName(), client));
            ResponseForm response = new ResponseForm("Register",null, true );
            server.getWriteBuffer().put(mapper.writeValueAsString(response).getBytes());
        }
    }

    @Override
    public void run() {
        while (true) {
            if (selector.isOpen()) {
                try {
                    selector.select();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {

                    SelectionKey key = iter.next();

                    if (key.isReadable()) {
                        try {
                            readFromChannel(server.getReadBuffer(), key);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    iter.remove();
                }
            } else {
                Thread.yield();
            }
        }
    }
}
