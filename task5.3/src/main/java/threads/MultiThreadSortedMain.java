package threads;

import com.sun.istack.internal.NotNull;

import static java.lang.Thread.sleep;

public class MultiThreadSortedMain {


    public static void main(@NotNull String[] args) {
        int threadNumber = Integer.parseInt(args[0]);
        int capacity = Integer.parseInt(args[1]);

        ArrayGeneratorRunner runner = new ArrayGeneratorRunner(capacity);
        Thread generatorThread = new Thread(runner);
        generatorThread.start();
        Thread[] sorterThreads = new Thread[threadNumber];
        for (int i = 0; i < threadNumber; i++) {
            sorterThreads[i] = new Thread(new SorterRunner(runner));
            sorterThreads[i].start();
        }
        try {
            sleep(10000);
            generatorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
