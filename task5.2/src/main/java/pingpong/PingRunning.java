package pingpong;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class PingRunning implements Runnable {

    /**
     * atomic counter for thread-safe exchange of message: the thread could increment this counter only if it was incremented
     * by the other thread. Also it regulate the number of exchanges
     */
    private AtomicInteger counter = new AtomicInteger();
    public AtomicInteger getCounter() {
        return this.counter;
    }
    @Override
    public void run() {
        int current = -1;
        while (true) {
            try {
                if (counter.get() > current) {
                    current = counter.incrementAndGet();
                    System.out.println("Ping: " + current);
                }
                if (current >= 100) {
                    System.out.println("Ping finished");
                    break;
                }
                sleep(0);  //for interruption
            } catch (InterruptedException e) {
                System.out.println("Ping is interrupted");
                break;
            }
        }
    }
}
