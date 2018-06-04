package ncedu.intdata;


import ncedu.QuickSort;

import java.util.List;

public class IntegerQuickSort implements QuickSort<Integer> {

    @Override
    public List<Integer> sort(List<Integer> list, int startInd, int endInd) {

        if (list.size() == 0){
            return list;
        }
        Integer pivot = list.get(endInd);
        int leftIndex = startInd;
        int rightIndex = endInd;
        while (leftIndex<rightIndex) {
            while (list.get(leftIndex) < pivot)  {
                leftIndex++;
            }
            while ((rightIndex>=0)&&(list.get(rightIndex) >= pivot)){
                rightIndex--;
            }
            if (leftIndex < rightIndex) {
                swap(list, leftIndex, rightIndex);
            }
        }
//        if (endInd>leftIndex) {
            swap(list, endInd, leftIndex);
//        }
        if(startInd<leftIndex) {
            sort(list, startInd, leftIndex-1);
        }
        if (leftIndex+1<endInd) {
            sort(list, leftIndex+1, endInd);
        }
        return list;
    }

    private void swap(List<Integer> list, int firstInd, int secondInd){
        Integer temp = list.get(firstInd);
        list.set(firstInd, list.get(secondInd));
        list.set(secondInd, temp);
    }

}
