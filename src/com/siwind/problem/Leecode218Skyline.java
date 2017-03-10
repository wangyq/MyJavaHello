package com.siwind.problem;

import java.util.*;

/**
 * Created by wang on 17-3-10.
 */
public class Leecode218Skyline {


    public static void main(String[] args){
        testSkyline();
    }

    public static void testSkyline(){
        //int[][]  buildings = new int[][]{{2,9,10},{3,7,15},{5,12,12},{15,20,10},{19,24,8}};
        //int[][]  buildings = new int[][]{{2,9,10},{3,7,10},{5,12,10},{15,20,10},{19,24,8}};
        int[][] buildings = new int[][]{{1,2,1},{1,2,2},{1,2,3}};
        //int[][] buildings = new int[][]{{0,2,3},{2,5,3}};

        //[[0,7],[5,12],[10,7],[15,12],[20,7],[25,0]]
        //int[][] buildings = new int[][]{{0,5,7},{5,10,7},{5,10,12},{10,15,7},{15,20,7},{15,20,12},{20,25,7}};

        List<int[]> skyline = new Leecode218Skyline().getSkyline(buildings);

        System.out.print("[ ");
        //for(int[] v: skyline){
        //System.out.print("[" + v[0] + " " + v[1] + "], " );
        //System.out.println("\b\b ]");
        int i = 0;
        for(  i=0;i<skyline.size()-1;i++){
            System.out.print("[" + skyline.get(i)[0] + " " + skyline.get(i)[1] + "], " );
        }
        if( skyline.size()>=1 ){
            System.out.print("[" + skyline.get(i)[0] + " " + skyline.get(i)[1] + "] " );
        }
        System.out.println("]");
    }


    /**
     *
     * @param buildings
     * @return
     */
    public List<int[]> getSkyline(int[][] buildings) {

        ArrayList<int[]> skyline = new ArrayList<>();

        // Priority for end location
        Queue<Node> processing = new PriorityQueue<Node>(new Comparator<Node>(){
            public int compare(Node first,Node second) {
                return first.end - second.end;
            }

        });

        // Priority for height desc
        Queue<Node> fall = new PriorityQueue<Node>(new Comparator<Node>(){
            public int compare(Node first,Node second) {
                if( (first.height == second.height) && (first.end == second.end) ){
                    return  -(first.start - second.start);
                } else if( first.height == second.height ){
                    return -(first.end - second.end);  // same height, lower weight first!
                }
                return -(first.height - second.height);
            }

        });

        //Queue<Node> ready = new LinkedList<Node>(); //ready queue!
        // Priority for height desc
        Queue<Node> ready = new PriorityQueue<Node>(new Comparator<Node>(){
            public int compare(Node first,Node second) {

                int f,s;
                if( first.isReady() ) f = first.start;  //
                else f = first.end;

                if( second.isReady() ) s = second.start;
                else s = second.end;


                if( f != s ) { // lower location first!
                    return f - s;
                }else { //same location!
                    if( first.isReady() && second.isReady() ){
                        return -(first.height - second.height);  //higher first!
                    } else if( first.isReady() ) {  // ready node first!
                        return -1;
                    } else if ( second.isReady() ){
                        return 1;
                    } else {
                        return -(first.height - second.height);  //higher first
                    }
                }

            }

        });

        for(int i=0;i<buildings.length;i++){
            Node node = new Node(i,buildings[i][0],buildings[i][1],buildings[i][2]);
            ready.offer(node);  //enqueue!
        }

        //
        Node current = null;

        while(!ready.isEmpty() ){
            Node node = ready.poll();
            if( node.isFinished() ){
                continue;
            }else if( current == null ){ //first node!

                current = node;

                int[] v = new int[2];
                v[0] = node.start;
                v[1] = node.height;
                skyline.add(v);

                node.setProcess();
                ready.offer(node);   //enqueue!
            } else if( current == node ){ // current end!
                node.setFinished();  //finished now!

                Node f = null;
                while( !fall.isEmpty() ){
                    f = fall.poll();
                    if( f.isFinished() || (f.end == node.end) ) {
                        f.setFinished();
                        f = null;   //be careful!
                    } else {
                        break;
                    }
                } //end of fall node!

                if( f == null ){
                    int[] v = new int[2];
                    v[0] = node.end;
                    v[1] = 0;
                    skyline.add(v);
                } else if( f.height < node.height ){
                    int[] v = new int[2];
                    v[0] = node.end;
                    v[1] = f.height;
                    skyline.add(v);
                } else { //f.height == node.height, nothing to do!

                }

                current = f; //next process node!

            } else if( node.isReady()){// READY process

                if( current.start == node.start ){ // must lower height!
                    node.setProcess();
                    ready.offer(node);
                    fall.offer(node);

                    continue;  //continue
                } else if( current.height < node.height ) {
                    //add new node
                    int[] v = new int[2];
                    v[0] = node.start;
                    v[1] = node.height;
                    skyline.add(v);

                    fall.offer(current);  // fall

                    node.setProcess();
                    ready.offer(node);   //enqueue!

                    current = node;  //update scanline's height.
                } else { //cover, current.height >= node.height

                    node.setProcess();
                    ready.offer(node);   //enqueue!
                    fall.offer(node);   //enqueue!
                }


            } else { // PROCESSING status
                node.setFinished();
            }
        } //end of while!

        return skyline;
    }
}


/**
 *
 */
class Node{
    private  enum Status{
        READY, PROCESSING,FINISHED
    };

    public Status status = Status.READY;  //inital status
    public int id, start, end, height;
    public Node(int id, int start, int end, int height){
        this.id = id;
        this.start = start;
        this.end = end;
        this.height = height;
        setReady();  //init status, ready
    }

    public boolean isReady(){
        return this.status == Status.READY;
    }
    public boolean isProcessing(){
        return this.status == Status.PROCESSING;
    }
    public boolean isFinished(){
        return this.status == Status.FINISHED;
    }

    public void setReady(){
        this.status = Status.READY;
    }
    public void setProcess(){
        this.status = Status.PROCESSING;
    }
    public void setFinished(){
        this.status = Status.FINISHED;
    }

}
