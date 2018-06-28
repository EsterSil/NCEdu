import org.junit.Assert;
import org.junit.Test;

public class DecoderAlgorithmTest {

    @Test (expected = IllegalArgumentException.class)
    public void decodeTest() {
        DecoderAlgorithm decoder = new DecoderAlgorithm();
        Assert.assertEquals(decoder.decodeString("abba2[ad3[e]2[c]]bb"),"abbaadeeeccadeeeccbb");
        decoder.decodeString("abba2[ad3[e]2[c]bb");
    }
}
