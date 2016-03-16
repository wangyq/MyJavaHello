package com.siwind.hello;

import java.util.Scanner;

public class Storage {

	static int m = 0;
	static int n = 0;
	static int matric[][] = null;
	static int foots[][] = null;
	
	static int m_x = 0;
	static int m_y = 0;
	
	public static void input(){
		Scanner sc = new Scanner(System.in);
		m = sc.nextInt();
		n = sc.nextInt();
		matric = new int[m][n];
		
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				matric[i][j] = sc.nextInt();
			}
		}
	}
	public static void doSearch(){
		int x = 0, y = 0;
		int left_top = 0, right_top = 0;
		int left_down = 0, right_down = 0;
		int left = 0, right = 0;
		
		
		//int direction = 0 ; //1,2,3
		
		foots[0][0] = 1;   //start from (0,0)
		do{
			if( x-1 >=0 ){//test (x-1,y)
				if( foots[x-1][y] ==0 ){ //not go througt it!
					
				}
			}
			if( x+1 >=0 ){
				
			}
			if( y-1 >=0 ){
				
			}
			if( y+1 >=0 ){
				
			}
		}while(true);
		
	}
	public static void test(){
		input();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class node{
	public int num;
	public node parent = null;
	public node left=null, mid=null, right=null;
	
	/**
	 * 
	 * @param n
	 */
	public node(int n){
		num = n;
	}
	
	public int getLevel(){
		int l = 0;
		//...
		return l;
	}
}
