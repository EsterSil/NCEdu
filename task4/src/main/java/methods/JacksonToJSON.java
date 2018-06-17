package methods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.xml.internal.ws.server.ServerRtException;

import java.io.File;
import java.io.IOException;

public class JacksonToJSON<T> {

    public String serializeToJSON(T obj, String path) {
        ObjectMapper mapper = new ObjectMapper();
        String result = null;
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        try {
            mapper.writeValue(new File(path),obj);
            result = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public Object deserializeJSON(String path, Class<T> targetClass){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), targetClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
