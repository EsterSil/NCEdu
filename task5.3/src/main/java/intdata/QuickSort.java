package intdata;

import java.util.Comparator;
import java.util.List;

public interface QuickSort<T extends Comparable<? super T>> {
    /**
     * method performs sorting of numbers in "natural order"
     *
     * @param unsortedList
     * @return sorted list
     */
    List<T> sort(List<T> unsortedList);

    /**
     * performs sorting in order specified by comparator
     *
     * @param unsortedList
     * @param comparator
     * @return
     */
    List<T> sort(List<T> unsortedList, Comparator<? super T> comparator);
}
