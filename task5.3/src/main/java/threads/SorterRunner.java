package threads;

import intdata.IntegerQuickSort;

import java.util.List;

import static java.lang.Thread.sleep;

public class SorterRunner implements Runnable {

    private final ArrayGeneratorRunner generator;
    private int number;

    public SorterRunner(ArrayGeneratorRunner generator , int number) {
        this.generator = generator;
        this.number = number;
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
            if (unsortedList.size()> 0) {
                IntegerQuickSort sorter = new IntegerQuickSort();
                start = System.nanoTime();
                List<Integer> sortedList = sorter.sort(unsortedList);
                end = System.nanoTime();
                System.out.println((end - start) + " sorting time" );
                System.out.println("Array successfully sorted at thread "+ number);
                //sortedList.subList(0, 10).forEach(System.out::print);
                //System.out.println();
            }
            try {
                sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
