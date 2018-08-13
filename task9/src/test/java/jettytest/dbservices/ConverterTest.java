package jettytest.dbservices;

import jettytest.forms.RegisterRequest;
import jettytest.forms.XMLConverter;
import org.junit.Test;

public class ConverterTest {

    @Test
    public void deserializeTest(){
        String xml = "<?xml version=”1.0” encoding=”utf-8”?>\n" +
                "\n" +
                "<request>\n" +
                "\n" +
                "<type>registerCustomer</type>\n" +
                "\n" +
                "<login>1234567890</login>\n" +
                "\n" +
                "<password>password</password>\n" +
                "\n" +
                "</request>";

        XMLConverter converter = new XMLConverter();
        RegisterRequest request = (RegisterRequest) converter.deserialize(xml, RegisterRequest.class);

    }
}
