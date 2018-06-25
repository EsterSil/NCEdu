package intdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntegerDataGenerator implements DataGenerator<Integer> {

    @Override
    public List<Integer> generateList(int length, Integer bound) {
        List<Integer> resultList = new ArrayList<>();
        if (length < 1) {
            throw new IllegalArgumentException();
        }
        Random random = new Random();
        if (bound == null) {
            for (int i = 0; i < length; i++) {
                resultList.add(random.nextInt());
            }
        } else {
            for (int i = 0; i < length; i++) {
                resultList.add(random.nextInt(bound));
            }
        }
        return resultList;
    }

    @Override
    public List<Integer> generateList(int length) {
        return generateList(length, null);
    }

}
