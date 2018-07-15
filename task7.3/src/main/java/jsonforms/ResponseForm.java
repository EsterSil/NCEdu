package jsonforms;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;

public class ResponseForm {
    private String to;
    private String message;
    private String from;
    private boolean isPrivate;

    public ResponseForm() {
    }

    public String getTo() {
        return to;
    }

    public ResponseForm(String message, String from, boolean isPrivate, String to) {
        this.to = to;

        this.message = message;
        this.from = from;
        this.isPrivate = isPrivate;
    }
    @JsonAnyGetter
    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        if( isPrivate){
            builder.append("[" +this.from+ "]: ");
        } else {
            builder.append(this.from+ ": ");
        }

        builder.append(this.message);
        return builder.toString();
    }


}
