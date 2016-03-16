package com.siwind.hello;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by admin on 2015/11/2.
 */
public class PingPong {
    public static void main(String[] args) throws Exception{
        Baller b = new Baller();
        Pinger ping = new Pinger(b,0);
        //Ponger pong = new Ponger(b,1,5000000);
        Ponger pong = new Ponger(b,1,5);

        Thread pingThr = new Thread(ping);
        Thread pongThr = new Thread(pong);

        long t1 = System.currentTimeMillis();
        pingThr.start();
        pongThr.start();
        pingThr.join();
        pongThr.join();
        long t2 = System.currentTimeMillis();

        //pingThr.stop();

        System.out.printf("time: %dms\n", t2-t1);
        System.out.printf("ping.curVal = %d\n", ping.curVal);
        System.out.printf("pong.curVal = %d\n", pong.curVal);
    }
}

class Baller{

    boolean bFinished = false;
    private Lock lock = new ReentrantLock();
    private Condition c[] = new Condition[2];

    private int id;
    private int val;

    public Baller(){
        c[0] = lock.newCondition();
        c[1] = lock.newCondition();
    }
    public void stop(){

        try {
            c[0].signalAll();
        }catch (Exception e){

        }
        try {
            c[1].signalAll();
        }catch (Exception e){

        }

    }
    public boolean doPlay(int curid, int delval){
     return  doPlay(curid,delval,Integer.MAX_VALUE); //
    }
    public boolean doPlay(int curid, int delval,int maxval){
        lock.lock();
        try {
            while( curid!= id && val <=maxval && !bFinished){
                c[curid].await(); //unlock 当前锁, 并进入wait状态。
            }
            if( val > maxval ){
                bFinished = true;
            }else if( !bFinished ){

                val += delval;
            }
            id = (id + 1) % 2;
            c[id].signalAll();


        }catch (InterruptedException e){

        }finally {
            lock.unlock();
        }

        return bFinished;
    }
}

/**
 *
 */
class Player implements Runnable{

    public int curVal = 0;

    protected Baller ball = null;

    protected int id = 0;

    public Player(Baller b,int id){
        this.ball = b;
        this.id = id;
    }

    @Override
    public void run() {
        while ( !ball.doPlay(id, 1)) {
            curVal++;
            //curVal = ball.doPlay(id, 1);
            System.out.println("Play: " + curVal);
        }
    }
}

class Pinger extends Player{
    public Pinger(Baller b,int id){
        super(b,id);
    }
}
class Ponger extends Player{
    int maxval = 0;
    public Ponger(Baller b, int id, int maxval){
        super(b,id);
        this.maxval = maxval;
    }
    @Override
    public void run() {
            //System.out.println("Play: " + curVal);

        while (ball.doPlay(id, 1,maxval) ){
            curVal++;
            System.out.println("Play: " + curVal);
        }

    }
}