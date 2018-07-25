package jsonforms;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.channels.SocketChannel;

/**
 * this class provide a common object for json serialisation with all required setters and getters
 */
public class ResponseForm {
    @JsonIgnore
    private SocketChannel to;
    private String message;
    private String from;
    private boolean isPrivate;

    public ResponseForm() {
    }

    public SocketChannel getTo() {
        return to;
    }

    public ResponseForm(String message, String from, boolean isPrivate, SocketChannel to) {
        this.to = to;
        this.message = message;
        this.from = from;
        this.isPrivate = isPrivate;
    }

    @JsonGetter
    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonGetter
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @JsonGetter("private")
    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    /**
     * overridden method to print the message gotten
     * @return
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (isPrivate) {
            builder.append("[" + this.from + "]: ");
        } else {
            builder.append(this.from + ": ");
        }
        builder.append(this.message);
        return builder.toString();
    }
}
