package methods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class JSONConverter<T> implements ConvertingUtils<T> {

    public String serialize(Class<T> targetClass, T targetObject, String destinationDirectory) {
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            mapper.writeValue(new File(destinationDirectory), targetObject);
            result = mapper.writeValueAsString(targetObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public T deserialize(String destinationDirectory, Class<T> targetClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(destinationDirectory), targetClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
