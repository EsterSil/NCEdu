package intdata;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

public class QuickSortTest {
    DataGenerator generator;
    Comparator comparator;
    QuickSort sorter;
    SortedArrayChecker checker;
    @Before
    public void init() {
        generator = new IntegerDataGenerator();
        sorter = new IntegerQuickSort();
        checker = new IntegerChecker();
    }

    @Test
    public void dataIntegerSortTest() {
        List<Integer> simpleUnsortedArray = generator.generateList(10, 100);
        List<Integer> simpleSortedArray = sorter.sort(simpleUnsortedArray);
        Assert.assertTrue(checker.checkList(simpleSortedArray));
    }

    @Test
    public void bigDataIntegerSortTest() {
        List<Integer> bigUnsortedArray = generator.generateList(1000);
        List<Integer> bigSortedArray = sorter.sort(bigUnsortedArray);

        Assert.assertTrue(checker.checkList(bigSortedArray));
    }

    @Test
    public void integerComparatorSortTest() {
       comparator = new IntegerComparator();
        List<Integer> simpleUnsortedArray = generator.generateList(10, 100);
        List<Integer> simpleSortedArray = sorter.sort(simpleUnsortedArray, comparator);
        Assert.assertTrue(checker.checkList(simpleSortedArray, comparator));
    }

    class IntegerComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }
}
