package threads;

import com.sun.istack.internal.NotNull;

import static java.lang.Thread.sleep;

public class MultiThreadSortedMain {

    /**
     * main thread starts generator and sorter thread and wait for generator thread
     * @param args first denote the duration of generator sleeping in seconds, second - capacity of queue
     */
    public static void main(@NotNull String[] args) {
        int sleepDuration = Integer.parseInt(args[0]);
        int capacity = Integer.parseInt(args[1]);

        ArrayGeneratorRunner runner = new ArrayGeneratorRunner(capacity, sleepDuration);
        Thread generatorThread = new Thread(runner);
        generatorThread.start();
        Thread sorterThreads = new Thread(new SorterRunner(runner, 1));
        sorterThreads.start();

        try {
            sleep(10000);
            generatorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
