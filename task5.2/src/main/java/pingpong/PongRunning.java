package pingpong;


import static java.lang.Thread.sleep;

public class PongRunning implements Runnable {
    private PingRunning ping;


    public PongRunning(PingRunning ping) {
        this.ping = ping;
    }

    @Override
    public void run() {
        int counter = 0;
        while (true) {
            try {
                sleep(0); ///for interruption
                if (ping.getCounter().get() > counter) {
                    counter = ping.getCounter().incrementAndGet();
                    System.out.println("Pong: " + counter);
                    if (counter >= 100){
                        System.out.println("Pong finished");
                        break;
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Pong is interrupted");
                break;
            }
        }
    }
}
