package com.siwind.problem;

import java.util.*;

/**
 * Created by wang on 17-3-13.
 */
public class SegmentCover {

    public static void main(String[] args){

        testLineOutline();
    }

    public static void testLineOutline(){
        //int[][] lines = {{1,2},{1,2},{2,4},{3,7},{4,6},{8,10}};
        int[][] lines = {{1,2},{1,3}, {3,8},{4,6},{5,8},{16,20},{20,22}};


        List<int[]> cover = new SegmentCover().findOutline(lines);
        for( int[] v: cover){
            System.out.print("[" + v[0] + "," + v[1] + "] ");
        }
        System.out.println();
    }

    /**
     * find outline of  all given lines.
     * @param lines
     * @return
     */
    public List<int[]> LineOutline00(int[][] lines){

        class Node{
            public int start, end, x;
            public Node(int start, int end){
                this.start = start;
                this.end = end;
                this.x = start;  //initial !
            }
        }

        List<int[]>  cover = new ArrayList<>(lines.length);

        Queue<Node> readyQueue = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if( o1.x != o2.x ) {
                    return o1.x - o2.x;  //lower location first!
                } else if( (o1.x == o1.end) && (o2.x == o2.start)){
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        for( int i=0;i<lines.length;i++){
            readyQueue.offer(new Node(lines[i][0],lines[i][1])); //add node
        }

        Queue<Integer>  queue = new PriorityQueue<>();

        while( !readyQueue.isEmpty() ){
            Node node = readyQueue.poll();
            node.x = node.end;

            int x = node.start;

            readyQueue.offer(node);
            queue.offer(node.end);
            while (!queue.isEmpty() ){
                node = readyQueue.poll();
                if( node.x == node.start ){
                    node.x = node.end;
                    readyQueue.offer(node);
                    queue.offer(node.end);
                } else {
                    queue.remove(node.end);
                }
            } //end of queue isEmpty()

            cover.add(new int[]{x,node.end});

        }

        return cover;
    }

    /**
     *
     * @param lines
     * @return
     */
    public List<int[]> findOutline(int[][] lines){
        class Node{
            public int start, end;
            public Node(int start, int end){
                this.start = start;
                this.end = end;
            }
        }

        List<Node> data = new ArrayList<Node>(lines.length);
        for( int i=0;i<lines.length;i++){
            data.add(new Node(lines[i][0],lines[i][1]));
        }
        data.sort(new Comparator<Node>() { //sort all input!
            @Override
            public int compare(Node o1, Node o2) {
                if( o1.start != o2.start ) {
                    return o1.start - o2.start;
                } else {
                    return o1.end - o2.end;
                }
            }
        });

        List<int[]>  cover = new ArrayList<>(lines.length);

        int start = 0, end = 0;
        for( Node node : data){
            if( end ==0 ){
                start = node.start;
                end = node.end;
            } else if( node.start<= end ){
                if( node.end > end ){
                    end = node.end; //update end!
                }
            } else {//begin new line!
                cover.add(new int[]{start,end});
                start = node.start;
                end = node.end;
            }
        }

        cover.add(new int[]{start,end}); //last segment line

        return cover;
    }
}
