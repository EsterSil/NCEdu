package pingpong;


import static java.lang.Thread.sleep;

public class PongThread implements Runnable {
    private PingThread ping;


    public PongThread(PingThread ping) {
        this.ping = ping;
    }

    @Override
    public void run() {
        int counter = 0;
        while (true) {
            try {
                sleep(1000);
                if (ping.getCounter() > counter) {
                    ping.trunkCounter();
                    counter = ping.getCounter();
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
