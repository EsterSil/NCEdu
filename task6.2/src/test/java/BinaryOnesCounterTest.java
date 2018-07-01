import org.junit.Test;



public class BinaryOnesCounterTest {

    @Test
    public void countTest() {
        BinaryCounter counter = new BinaryCounter();
        int[] inp = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        int[] out = counter.countOnes(inp);
        for(int i:out){
            System.out.println(i);
        }
    }
}
