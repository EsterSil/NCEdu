package threads;

import intdata.IntegerDataGenerator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

/**
 * this class is a task? implementing the
 */
public class
ArrayGeneratorRunner implements Runnable {
    private Queue<List<Integer>> arrayQueue;
    private final int capacity;
    private final int duration;

    private boolean isInterrupted = false;
    public ArrayGeneratorRunner(int capacity, int duration) {
        this.arrayQueue = new LinkedBlockingQueue<>(capacity);
        this.capacity = capacity;
        this.duration = duration;
    }

    public boolean queueIsEmpty(){
        return arrayQueue.isEmpty();
    }
    public List<Integer> removeList(){

        try {
            return arrayQueue.remove();
        } catch (NoSuchElementException e){
            try {
                sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return removeList();
        }
    }


    private boolean generateArray(){
        IntegerDataGenerator generator = new IntegerDataGenerator();
        List<Integer> list;
        boolean result = false;
        long start = 0;
        long end = 0;
        if (arrayQueue.size()<capacity) {
            start = System.nanoTime();
            list = generator.generateList(100000, 100000);
            end = System.nanoTime();
            result = arrayQueue.add(list);
            System.out.println((end - start) +" generation");
        } else {
            System.out.println(" Queue is full!");
        }
        return result;
    }

    @Override
    public  void run() {
        System.out.println("Main is started");
        while (!isInterrupted){
            if (!generateArray()) {
                try {
                    sleep(duration*1000);
                    System.out.println("Main awake");
                } catch (InterruptedException e) {
                    isInterrupted = true;
                    System.out.println(" Generator is finished");
                }
            }
        }
    }
}
