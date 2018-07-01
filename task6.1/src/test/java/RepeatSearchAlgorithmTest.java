import org.junit.Assert;
import org.junit.Test;

public class RepeatSearchAlgorithmTest {

    @Test
    public void searchTest() {
        RepeatSearchAlgorithm algorithm = new RepeatSearchAlgorithm();
        int[] inp = {1, 9, 3, 4, 5, 2, 3, 7, 6};
        int result = algorithm.search(inp);
        Assert.assertEquals(result, 1);
    }
}
