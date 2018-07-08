import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

public class MostRepeatableTest {
    private MostRepeatable finder = new MostRepeatable();
    private Random random = new Random();

   @ParameterizedTest
   @ValueSource(ints = {5,20,100,1000})
    public void findMostRepeatableTest(int size) {
       System.out.print("array size "+size + ", ");
        int repeatable = random.nextInt();
       System.out.println( "repeatable "+ repeatable);
        int[] array = new int[size];
        int i = 0;
        int index = 0;
        while (i < size / 2 + 1) {
            index = random.nextInt(size);
            if (array[index] == 0) {
                array[index] = repeatable;
                i++;
            }
        }
        for (int j = 0; j < size; j++) {
            if (array[j] == 0) {
                array[j] = random.nextInt();
            }
        }
        for (int k: array){
            System.out.print(k+" ");
        }
        System.out.println();
        Assert.assertEquals(finder.findMostRepeatable(array), repeatable);
    }
}
