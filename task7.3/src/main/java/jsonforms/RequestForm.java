package jsonforms;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

/**
 * this class provide an object for json serialisation
 */
public class RequestForm {
    String nickName;
    String message;
    String to;

    public RequestForm(String nickName, String message, String to) {
        this.nickName = nickName;
        this.message = message;
        this.to = to;
    }

    public RequestForm() {

    }
    @JsonAnyGetter
    public String getNickName() {

        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
