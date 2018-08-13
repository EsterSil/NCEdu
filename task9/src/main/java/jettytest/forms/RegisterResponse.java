package jettytest.forms;

import javax.xml.bind.annotation.XmlElement;

public class RegisterResponse {
    private int resultCode;

    public RegisterResponse() {

    }
    @XmlElement
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
