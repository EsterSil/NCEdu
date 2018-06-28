package threads;

import intdata.IntegerQuickSort;

import java.util.List;

import static java.lang.Thread.sleep;

public class SorterRunner implements Runnable {

    private ArrayGeneratorRunner generator;

    public SorterRunner(ArrayGeneratorRunner generator) {
        this.generator = generator;
    }

    @Override
    public void run() {
        System.out.println("Sorter is started");
        long start = 0;
        long end = 0;
        while(true) {
            while (generator.queueIsEmpty()) {
                Thread.yield();
            }
            List<Integer> unsortedList = generator.removeList();
            if (unsortedList.size()!= 0) {
                IntegerQuickSort sorter = new IntegerQuickSort();
                System.out.println((end - start) + " sorting "+unsortedList.size() );
                start = System.nanoTime();
                List<Integer> sortedList = sorter.sort(unsortedList);
                unsortedList = null;
                end = System.nanoTime();
                System.out.println("Array successfully sorted at");
                sortedList.subList(0, 10).forEach(System.out::print);
                System.out.println();
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
