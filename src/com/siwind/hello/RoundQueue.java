package com.siwind.hello;

/**
 * Created by admin on 2015/11/2.
 */
public class RoundQueue<T> {
    private T[] queue;
    private int head=0;
    private int tail=0;
    private  int capacity=32;

    /**
     * ��ʼ��
     * @param capacity
     */
    public RoundQueue(int capacity ){
        this.capacity = (capacity<=0) ?this.capacity:capacity;
        this.queue = (T[]) new Object[this.capacity];
    }

    public RoundQueue(){
        this(-1);    //
    }

    public boolean isEmpty(){
        return head == tail;
    }

    public boolean isFull(){
        return ( tail + capacity - head  + 1 ) % capacity == 0;
    }


    public boolean push(T element){
        boolean isOK = false;
        if( !isFull() ){
            queue[tail] = (T) element;
            tail = (tail +1) % capacity; //
            isOK = true;  //push ok
        }
        return isOK;
    }
    public T pop(){
        T element = null;
        if( !isEmpty() ){
            element = queue[head];
            queue[head] = null;   // dereference , clear now
            head = (head+1) %capacity; //pop ok
        }
        return element;
    }

    public void clear(){
        head = tail = 0;
    }
}
