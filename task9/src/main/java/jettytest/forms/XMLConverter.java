package jettytest.forms;


import org.xml.sax.InputSource;

import javax.xml.bind.*;
import java.io.*;


public class XMLConverter  {





    public String serialize( Object targetObject, Class clazz) {
        StringWriter writer = new StringWriter();
        Marshaller marshaller = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            marshaller = context.createMarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (PropertyException e) {
            e.printStackTrace();
        }
        try {
            marshaller.marshal(targetObject, writer);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public Object deserialize(String xml, Class clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
