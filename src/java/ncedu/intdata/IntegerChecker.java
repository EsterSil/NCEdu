package ncedu.intdata;

import ncedu.SortedArrayChecker;

import java.util.List;

public class IntegerChecker implements SortedArrayChecker<Integer> {
    @Override
    public boolean checkArray(List<Integer> list) {
        if (list.size()==0){
            return true;
        }
        Integer currentInt = list.get(0);
        for (int i = 1; i< list.size(); i++){
            if(list.get(i)<currentInt){
                return false;
            }
            currentInt = list.get(i);
        }
        return true;
    }
}