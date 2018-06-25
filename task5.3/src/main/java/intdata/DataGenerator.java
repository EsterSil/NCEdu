package intdata;

import java.util.List;

public interface DataGenerator<T extends Number> {

    /**
     * Method generates a list of given length
     *
     * @param length
     * @param bound  is upper bound
     * @return list of random numbers, all are positive and less than bound
     */
    List<T> generateList(int length, T bound);

    /**
     * method generate a list of given length
     *
     * @param length
     * @return list of random numbers
     */
    List<T> generateList(int length);
}
