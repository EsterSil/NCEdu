package pingpong;

/**
 * this class initialize both threads, ping and pong
 * than main is waiting
 */

public class PingPongMain {


    public static void main(String[] args) {
        PingRunning pingRun = new PingRunning();
        PongRunning pongRun = new PongRunning(pingRun);
        Thread ping = new Thread(pingRun);
        ping.start();
        Thread pong = new Thread(pongRun);
        pong.start();
        while (true) {
            System.out.println("Main thread is waiting");
            try {
                ping.join();
                pong.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
