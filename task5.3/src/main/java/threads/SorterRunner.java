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
            start = System.nanoTime();



            IntegerQuickSort sorter = new IntegerQuickSort();
            end = System.nanoTime();

            System.out.println((end - start) +" sorting");

            List<Integer> sortedList = sorter.sort(unsortedList);
            System.out.println("Array successfully sorted at" );
            sortedList.subList(0,10).forEach(System.out::print);
            System.out.println();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
