package intdata;

import java.util.Comparator;
import java.util.List;

public interface SortedArrayChecker<T> {
    /**
     * method checks the given list
     *
     * @param list
     * @return true if all elements are in natural order, false if not
     */
    boolean checkList(List<T> list);

    /**
     * method checks the given list in the order, specified by comparator
     *
     * @param list
     * @param comparator
     * @return
     */
    boolean checkList(List<T> list, Comparator<? super T> comparator);


}
