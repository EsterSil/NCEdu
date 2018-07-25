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

/**
 * this runnable class provides a task for server's writing thread
 */
public class WriteRunner implements Runnable {

    /**
     * local selector to reveal clients' sockets ready to be written to
     */
    private Selector writeSelector = null;
    /**
     * central thread-keeping class
     */
    private ChatServer server = null;

    /**
     * json mapper object
     */
    private ObjectMapper mapper;

    /**
     * buffer to write to clients sockets
     */
    private ByteBuffer writeBuffer;
    /**
     * flag to stop the process loop in case of exceptions
     */
    private boolean isInterrupted = false;

    /**
     * public runner constructor, initializes all necessary fields and saves the object of thread-parent class
     *
     * @param server parent thread's object
     */
    public WriteRunner(ChatServer server) {
        try {
            this.writeSelector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeBuffer = ByteBuffer.allocate(2048);
        this.server = server;
        mapper = new ObjectMapper();
    }

    /**
     * inner method updates local selector with new registered connection
     *
     * @throws ClosedChannelException
     */
    private void selectorUpDate() throws ClosedChannelException {
        while (server.getUnhandledConnectionsToWrite().size() > 0) {
            SocketChannel channel = server.getUnhandledConnectionsToWrite().remove();
            channel.register(writeSelector, SelectionKey.OP_WRITE);
        }
    }

    /**
     * inner method performing target write to client socked.
     * before write method check if target channel is write ready by using writeSelector
     * if target client is absent in current session method write a error message to sender
     *
     * @param form         response form created by reading thread
     * @param selectedKeys result of selection in writeSelector with OP_WRITE option
     *                     used to check if client's socket is write ready
     * @throws NoSuchElementException throws if map do
     */
    private void privateWrite(ResponseForm form, Set<SelectionKey> selectedKeys) throws NoSuchElementException {

        SocketChannel client = form.getTo();
        if (selectedKeys.contains(client.keyFor(writeSelector))) {
            try {
                writeBuffer.put(mapper.writeValueAsString(form).getBytes());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            writeBuffer.flip();
            try {
                client.write(writeBuffer);
            } catch (IOException e) {
                client.keyFor(writeSelector).cancel();
                System.out.print(e.getCause().toString());
            }
            writeBuffer.clear();
        }

    }

    /**
     * inner method performing global write to all available clients
     *
     * @param form
     * @param selectedKeys
     * @throws IOException
     */
    private void publicWrite(ResponseForm form, Set<SelectionKey> selectedKeys) {
        Iterator<SelectionKey> iterator = selectedKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            SocketChannel client = (SocketChannel) key.channel();
            try {
                writeBuffer.put(mapper.writeValueAsString(form).getBytes());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            writeBuffer.flip();
            try {
                client.write(writeBuffer);
            } catch (IOException e) {
                server.deleteSession(client);
                key.cancel();
                //e.printStackTrace();
            }
            iterator.remove();
            writeBuffer.clear();
        }
    }

    /**
     * overridden method to be called in that separately executing
     * thread.
     */
    @Override
    public void run() {
        System.out.println("=========== SERVER WRITER is started");
        ResponseForm currentForm = null;
        while (!isInterrupted) {
            if (server.getMessageQueue().size() > 0) {
                currentForm = server.getMessageQueue().remove();
                if (currentForm != null) {
                    try {
                        selectorUpDate();
                    } catch (ClosedChannelException e) {
                        isInterrupted = true;
                        e.printStackTrace();
                    }
                    try {
                        writeSelector.selectNow();
                    } catch (IOException e) {
                        isInterrupted = true;
                        e.printStackTrace();
                    }
                    Set<SelectionKey> selectedKeys = writeSelector.selectedKeys();
                    if (currentForm.isPrivate()) {
                        privateWrite(currentForm, selectedKeys);
                    } else {
                        publicWrite(currentForm, selectedKeys);
                    }
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
