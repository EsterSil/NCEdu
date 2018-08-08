import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsonforms.RequestForm;
import org.junit.Test;

public class JsonEncodeTest {

    @Test
    public void encodeTest(){
        ObjectMapper mapper = new ObjectMapper();
        RequestForm request = new RequestForm("ana", "hello", null);
        String json = null;
        try {
            json = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json);
    }
}
