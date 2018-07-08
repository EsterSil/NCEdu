package intdata;


import java.util.Comparator;
import java.util.List;

public class IntegerQuickSort implements QuickSort<Integer> {

    /**
     * inner sorting method for recursive call to sort part of list
     *
     * @param unsortedList
     * @param startInd     index in list to start with
     * @param endInd       index to end with
     * @return partly sorted list
     */
    private List<Integer> sort(List<Integer> unsortedList, int startInd, int endInd) {

        if ((unsortedList.size() == 0) || (unsortedList.size() == 1)) {
            return unsortedList;
        }
        Integer pivot = unsortedList.get(endInd);
        int leftIndex = startInd;
        int rightIndex = endInd;
        while (leftIndex < rightIndex) {
            while (unsortedList.get(leftIndex) < pivot) {
                leftIndex++;
            }
            while ((rightIndex >= 0) && (unsortedList.get(rightIndex) >= pivot)) {
                rightIndex--;
            }
            if (leftIndex < rightIndex) {
                swap(unsortedList, leftIndex, rightIndex);
            }
        }
        swap(unsortedList, endInd, leftIndex);
        if (startInd < leftIndex) {
            sort(unsortedList, startInd, leftIndex - 1);
        }
        if (leftIndex + 1 < endInd) {
            sort(unsortedList, leftIndex + 1, endInd);
        }
        return unsortedList;
    }

    /**
     * inner sorting method for recursive call to sort part of unsorted list in order, specified by comparator
     *
     * @param unsortedList
     * @param startInd     index in list to start with
     * @param endInd       index to end with
     * @param comparator
     * @return partly sorted list
     */
    private List<Integer> sort(List<Integer> unsortedList, int startInd, int endInd, Comparator<? super Integer> comparator) {

        if ((unsortedList.size() == 0) || (unsortedList.size() == 1)) {
            return unsortedList;
        }
        Integer pivot = unsortedList.get(endInd);
        int leftIndex = startInd;
        int rightIndex = endInd;
        while (leftIndex < rightIndex) {
            while (comparator.compare(unsortedList.get(leftIndex), pivot) < 0) {
                leftIndex++;
            }
            while ((rightIndex >= 0) && (comparator.compare(unsortedList.get(rightIndex), pivot) >= 0)) {
                rightIndex--;
            }
            if (leftIndex < rightIndex) {
                swap(unsortedList, leftIndex, rightIndex);
            }
        }
        swap(unsortedList, endInd, leftIndex);
        if (startInd < leftIndex) {
            sort(unsortedList, startInd, leftIndex - 1);
        }
        if (leftIndex + 1 < endInd) {
            sort(unsortedList, leftIndex + 1, endInd);
        }
        return unsortedList;
    }

    /**
     * simple swapper, replace two given elements in list
     * @param list
     * @param firstInd
     * @param secondInd
     */
    private void swap(List<Integer> list, int firstInd, int secondInd) {
        Integer temp = list.get(firstInd);
        list.set(firstInd, list.get(secondInd));
        list.set(secondInd, temp);
    }

    @Override
    public List<Integer> sort(List<Integer> unsortedList) {
        return sort(unsortedList, 0, unsortedList.size() - 1);
    }

    @Override
    public List<Integer> sort(List<Integer> unsortedList, Comparator<? super Integer> comparator) {
        return sort(unsortedList, 0, unsortedList.size() - 1, comparator);
    }
}
