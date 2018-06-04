package intdata;

import ncedu.intdata.IntegerChecker;
import ncedu.intdata.IntegerDataGenerator;
import ncedu.intdata.IntegerQuickSort;
import ncedu.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class QuickSortTest {

    @Test
    public void dataIntegerSortTest(){
        DataGenerator generator = new IntegerDataGenerator();
        List<Integer> simpleUnsortedArray = generator.generateArray(10, 100);
        QuickSort sorter = new IntegerQuickSort();
        List<Integer> simpleSortedArray = sorter.sort(simpleUnsortedArray,0,simpleUnsortedArray.size()-1);
        SortedArrayChecker checker = new IntegerChecker();
        Assert.assertTrue(checker.checkArray(simpleSortedArray));
    }

    @Test
    public void bigDataIntegerSortTest(){
        DataGenerator generator = new IntegerDataGenerator();
        List<Integer> bigUnsortedArray = generator.generateArray(1000);
        QuickSort sorter = new IntegerQuickSort();
        List<Integer> bigSortedArray = sorter.sort(bigUnsortedArray,0,bigUnsortedArray.size()-1);
        SortedArrayChecker checker = new IntegerChecker();
        Assert.assertTrue(checker.checkArray(bigSortedArray));
    }

}
