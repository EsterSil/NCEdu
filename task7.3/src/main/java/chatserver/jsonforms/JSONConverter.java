package chatserver.jsonforms;

import chatserver.jsonforms.ConvertingUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class JSONConverter {

    public String serialize( ResponseForm targetObject) {
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        //mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            result = mapper.writeValueAsString(targetObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public RequestForm deserialize(String destinationDirectory, Class<T> targetClass) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.readValue()
        try {
            return mapper.readValue(new File(destinationDirectory), targetClass);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
