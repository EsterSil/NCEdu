package methods;


import javax.xml.bind.*;
import java.io.*;


public class XMLConverter<T> implements ConvertingUtils<T> {


    public String serialize(Class<T> targetClass, T targetObject, String destinationDirectory) {
        StringWriter writer = new StringWriter();
        Marshaller marshaller = null;
        try {
            JAXBContext context = JAXBContext.newInstance(targetClass);
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
            marshaller.marshal(targetObject, new FileOutputStream(new File(destinationDirectory)));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public T deserialize(String destinationDirectory, Class<T> targetClass) {
        try {
            JAXBContext context = JAXBContext.newInstance(targetClass);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new FileReader(destinationDirectory));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
