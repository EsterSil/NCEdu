package jsonforms;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;

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
    @JsonGetter
    public String getNickName() {

        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    @JsonGetter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @JsonGetter
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
