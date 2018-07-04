package intdata;

import java.util.Comparator;
import java.util.List;

public class IntegerChecker implements SortedArrayChecker<Integer> {
    @Override
    public boolean checkList(List<Integer> list) {
        if ((list.size() == 0) || (list.size() == 1)) {
            return true;
        }
        Integer currentInt = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < currentInt) {
                return false;
            }
            currentInt = list.get(i);
        }
        return true;
    }

    @Override
    public boolean checkList(List<Integer> list, Comparator<? super Integer> comparator) {

        if ((list.size() == 0) || (list.size() == 1)) {
            return true;
        }
        Integer currentInt = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (comparator.compare(currentInt, list.get(i))>=0) {
                return false;
            }
            currentInt = list.get(i);
        }
        return true;
    }
}