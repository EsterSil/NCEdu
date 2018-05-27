package ncedu;

import java.util.List;

public interface DataGenerator<T> {

    /*
     *
     */
    List<T> generateArray(int length, T bound);

    List<T> generateArray (int length);
}
