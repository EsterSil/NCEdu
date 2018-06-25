package threads;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import intdata.IntegerDataGenerator;

import static java.lang.Thread.sleep;

public class ArrayGeneratorRunner implements Runnable {
    private Queue<List<Integer>> arrayQueue;
    private int counter = 0;
    private int capacity;

    public ArrayGeneratorRunner(int capacity) {
        this.arrayQueue = new LinkedBlockingQueue<>(capacity);
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
    private void generateArray(){
        IntegerDataGenerator generator = new IntegerDataGenerator();
        List<Integer> list;
        long start = 0;
        long end = 0;
        long end2 = 0;
        if (arrayQueue.size()<capacity) {
            start = System.nanoTime();
            list = generator.generateList(100, 100000);
            end = System.nanoTime();
            arrayQueue.add(list);
            end2 = System.nanoTime();
            counter++;
            System.out.println((end - start) +" generation");
            System.out.println((end2 - start) +" generation&put");
        }
    }

    @Override
    public  void run() {
        System.out.println("Main is started");
        while (counter<100){
            System.out.println("Main "+ arrayQueue.size());
            generateArray();
            try {
                sleep( 1000);
            } catch (InterruptedException e) {
                System.out.println(" Generator is finished");
                break;
            }
        }
    }
}
