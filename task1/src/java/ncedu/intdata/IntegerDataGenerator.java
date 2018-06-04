package ncedu.intdata;

import ncedu.DataGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntegerDataGenerator implements DataGenerator<Integer> {

    @Override
    public List<Integer> generateArray(int length, Integer bound){
        List<Integer> resultList = new ArrayList<>();
        if (length < 1){
            throw new IllegalArgumentException();
        }
        for (int i = 0; i<length; i++) {
            Random random = new Random();
            if (bound == null) {
                resultList.add(random.nextInt());
            } else {
                resultList.add(random.nextInt(bound));
            }
        }
        return resultList;
    }

    @Override
    public List<Integer> generateArray(int length) {
        return generateArray(length, null);
    }

}
