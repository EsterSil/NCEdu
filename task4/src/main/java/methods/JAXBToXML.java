package methods;

import tables.*;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JAXBToXML<T> {
    public void serializeTable(Class<T> targetClass, T targetObject, String destinationDirectory) {

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
            marshaller.marshal(targetObject,System.out );
            marshaller.marshal(targetObject, new FileOutputStream(new File(destinationDirectory)));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Object deserializeTable( String sourcePath){
        JAXBContext context = null;
        Unmarshaller unmarshaller = null;
        try {
            context = JAXBContext.newInstance(OrderTable.class);
            unmarshaller = context.createUnmarshaller();
            return unmarshaller.unmarshal(new FileReader(sourcePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
