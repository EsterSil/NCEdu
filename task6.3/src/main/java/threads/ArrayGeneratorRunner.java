package threads;

import intdata.IntegerDataGenerator;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Thread.sleep;

public class ArrayGeneratorRunner implements Runnable {
    private MyBlockingQueue<List<Integer>> arrayQueue;
    private int capacity;

    public ArrayGeneratorRunner(int capacity) {
        this.arrayQueue = new MyBlockingQueue<>(capacity);
        this.capacity = capacity;
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
        long end2 = 0;
        if (arrayQueue.size()<capacity) {
            start = System.nanoTime();
            list = generator.generateList(100000, 100000);
            end = System.nanoTime();
            result = arrayQueue.add(list);
            System.out.println((end - start) +" generation");
        }
        System.out.println(" Queue is full!");
        return result;
    }

    @Override
    public  void run() {
        System.out.println("Main is started");
        while (true){
            if (!generateArray()) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(" Generator is finished");
                    break;
                }
            }
        }
    }
}
