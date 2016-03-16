package com.siwind.hello;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by admin on 2015/11/2.
 */
public class PingPongAtom {

    public static void main(String[] args) throws Exception{
        Ball b = new Ball();
        Ping ping = new Ping(b,0);
        Pong pong = new Pong(b,1,5000000);
        //Pong pong = new Pong(b,1,50);

        Thread pingThr = new Thread(ping);
        Thread pongThr = new Thread(pong);

        long t1 = System.currentTimeMillis();
        pingThr.start();
        pongThr.start();
        //pingThr.join();
        pongThr.join();
        long t2 = System.currentTimeMillis();

        pingThr.stop();

        System.out.printf("time: %dms\n", t2-t1);
        System.out.printf("ping.curVal = %d\n", ping.curVal);
        System.out.printf("pong.curVal = %d\n", pong.curVal);
    }
}

class Ball{
    AtomicInteger val = null;

    public Ball(){
        this(0);
    }
    public Ball(int val){
        this.val = new AtomicInteger(val);
    }
    public int waitOnPlay(int newval){
        while( val.get() != newval ){
            Thread.yield();            //wait and play!
        }

        return val.incrementAndGet();  //now fire to next player!
    }


}

class PingPongPlayer implements Runnable{

    Ball ball = null;
    public int curVal = 0;

    PingPongPlayer(Ball b, int val){
        this.ball = b;
        this.curVal = val;
    }

    public void play(){ //play ball
        ball.waitOnPlay(curVal);
        //System.out.println("Play " + curVal);
        curVal += 2;
    }

    @Override
    public void run() {
        while (true){
            play();  //
        }
    }
}

class Ping extends PingPongPlayer{
    Ping(Ball b,int val){
        super(b,val);
    }
}

class Pong extends PingPongPlayer{
    int maxval = 0;
    public Pong(Ball b, int val, int maxval){
        super(b,val);
        this.maxval = maxval;
    }

    @Override
    public void run() {
        while (curVal <=maxval){
            play();  //
        }
    }
}