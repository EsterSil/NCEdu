package pingpong;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class PingThread implements Runnable {

    public void trunkCounter() {
        this.counter++;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    private volatile Integer counter = 0;

    @Override
    public void run() {
        int current = -1;
        while (true) {
            try {
                synchronized (counter) {
                    if (getCounter() > current) {
                        this.trunkCounter();
                        current = getCounter();
                        System.out.println("Ping: " + current);
                    }
                }
                if (current >= 100){
                    System.out.println("Ping finished");
                    break;
                }
                sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Ping is interrupted");
                break;
            }
        }
    }
}
