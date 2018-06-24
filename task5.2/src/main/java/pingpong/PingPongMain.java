package pingpong;

import static java.lang.Thread.sleep;

public class PingPongMain {


    public static void main(String[] args) {
        PingThread pingRun  = new PingThread();
        PongThread pongRun  = new PongThread(pingRun);
        Thread ping = new Thread(pingRun);
        ping.start();
        Thread pong = new Thread(pongRun);
        pong.start();

        while(true){
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
