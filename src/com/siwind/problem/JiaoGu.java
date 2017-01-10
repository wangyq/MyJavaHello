package com.siwind.problem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class JiaoGu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testJiaoGu();
	}

	public static void testJiaoGu() {
		
		//boolean b = Util.isOdd( BigInteger.valueOf(13) );
		//System.out.println(b);
		
		//JGNode.run(2, 1);
		JGNodeBig.run(2, 1);
	}
}

class JGNode {
	
	public int exp = 0;
	public long a ;
	public long b ;

	public JGNode(JGNode node) {
		this.a = node.a;
		this.b = node.b;
		this.exp = node.exp;
	}

	public JGNode(long a, long b) {
		// TODO Auto-generated constructor stub
		this.a = a;
		this.b = b;;
		this.exp = 0;
	}
	public JGNode(long a, long b, int exp) {
		// TODO Auto-generated constructor stub
		this.a = a;
		this.b = b;
		this.exp = exp;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isEven() {
		return (a%2 == 0) && ( b%2 ==0);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isOdd() {
		return (a%2 ==0) && ( b%2==1);
	}

	public boolean isUnCertain() {
		return a%2 ==1;
	}

	boolean isLessThan( JGNode node){
		if ( a < node.a  ) return true;
		if( a == node.a && b < node.b ) return true;
		return false;
	}
	/**
	 * 
	 * @return
	 */
	public boolean transform() {
		boolean bOK = true;
		while (true) {
			if (isOdd()) {
				a = a * 3;
				b = b * 3 + 1;
			} else if (isEven()) {
				a = a / 2;
				b = b / 2;
			} else if (isUnCertain()) {
				bOK = false; // uncertain
				break;
			}
			print();
		}
		return bOK;
	}

	public void  print() {
		System.out.println("(" + a + "," + b + ") ^ " + exp);
	}
	
	public List<JGNode> makeBranch(int k) {
		ArrayList<JGNode>  arr = new ArrayList<>();
		
		// ax+b ==> a*(kx+i) + b = akx + ai +b
		
		long aa = a*k,  bb = b;
		int expp = exp+1;
		
		if( aa > 0 ){
			for( int i=0;i<k;i++){
				JGNode node = new JGNode(aa,bb, expp);
				arr.add(node);
				bb += a;   //next value
			}
		} else {// end of if
			System.out.println("Exceed limit. a = " + a);
		}
		return arr;
	}
	
	public JGNode createEven(){
		JGNode node = new JGNode(this);
		node.a  = this.a * 2;  // ak + b ==> a*(2k) + b = 2ak + b;
		//node.b = 2;
		node.exp ++;
		return node;
	}
	public JGNode createOdd(){
		JGNode node = new JGNode(this);
		node.a  = this.a * 2;
		node.b = this.a  + this.b ;  // ak+b ==> a*(2k+1) + b = 2ak + a + b
		node.exp ++;
		return node;
	}
	/**
	 * 
	 * @param node
	 */
	public static void run(int a, int b) {
		int count = 1;
		Queue<JGNode> queue = new LinkedList<JGNode>(); 
		
		queue.add(new JGNode(a,b)); //
		
		while (!queue.isEmpty()) {
			count ++;
			
			System.out.println("Begin: ==>");
			
			JGNode old = queue.remove();
			JGNode node = new JGNode(old);

			old.print();
			
			if (!node.transform()) { //uncertain!
				if( old.isLessThan(node) ){
					List<JGNode>  arr = node.makeBranch(2);
					queue.addAll(arr);
					//queue.add(node.createEven());
					//queue.add(node.createOdd());
				}
			}
			//if( count >=30) break;
		}
	}
}

/**
 * ak + b
 * 
 * @author admin
 *
 */
class JGNodeBig {
	public static BigInteger THREE = BigInteger.valueOf(3);
	public static BigInteger TWO = BigInteger.valueOf(2);
	public static BigInteger ONE = BigInteger.ONE;
	
	public long exp = 0;
	public BigInteger a ;
	public BigInteger b ;

	public JGNodeBig(JGNodeBig node) {
		this.a = node.a;
		this.b = node.b;
		this.exp = node.exp;
	}

	public JGNodeBig(long a, long b) {
		// TODO Auto-generated constructor stub
		this.a = BigInteger.valueOf(a);
		this.b = BigInteger.valueOf(b);;
		this.exp = 0;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isEven() {
		return Util.isEven(a) && Util.isEven(b);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isOdd() {
		return Util.isEven(a) && Util.isOdd(b);
	}

	public boolean isUnCertain() {
		return Util.isOdd(a);
	}

	boolean isLessThan( JGNodeBig node){
		if ( a.compareTo(node.a) < 0  ) return true;
		if( a.compareTo(node.a) ==0 && b.compareTo( node.b)<=0 ) return true;
		return false;
	}
	/**
	 * 
	 * @return
	 */
	public boolean transform() {
		boolean bOK = true;
		while (true) {
			if (isOdd()) {
				a = a.multiply( THREE ); //a = a * 3;
				b = b.multiply(THREE).add(ONE); //b = b * 3 + 1;
			} else if (isEven()) {
				a = a.divide(TWO); //a = a / 2;
				b = b.divide(TWO); //b = b / 2;
			} else if (isUnCertain()) {
				bOK = false; // uncertain
				break;
			}
			print();
		}
		return bOK;
	}

	public void print(){
		System.out.println("(" + a + "," + b + ") ^ " + exp);
	}
	public JGNodeBig createEven(){
		JGNodeBig node = new JGNodeBig(this);
		node.a = a.multiply(TWO); // ak + b ==> a*(2k) + b = 2ak + b;
		//node.b = 2;
		node.exp  = exp +1;
		return node;
	}
	public JGNodeBig createOdd(){
		JGNodeBig node = new JGNodeBig(this);
		node.a = a.multiply(TWO);    // ak+b ==> a*(2k+1) + b = 2ak + a + b
		node.b = a.add(b); 
		node.exp  = exp +1;
		return node;
	}
	/**
	 * 
	 * @param node
	 */
	public static void run(int a, int b) {
		int count = 0;
		Queue<JGNodeBig> queue = new LinkedList<JGNodeBig>(); 

		queue.add(new JGNodeBig(a,b) ); //
		
		while (!queue.isEmpty()) {
			count++;
			
			JGNodeBig old = queue.remove();
			JGNodeBig node = new JGNodeBig(old);

			System.out.println("Begin: ==>");
			node.print();
			
			if (!node.transform()) { //uncertain!
				if( old.isLessThan(node) ){
					queue.add(node.createEven());
					queue.add(node.createOdd());
				}
			}//end of if
			
			if( count >50) break;
		}
	}
}

class Util{
	public static BigInteger TWO = BigInteger.valueOf(2);
	/**
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isEven(BigInteger val){
		return val.remainder(TWO) == BigInteger.ZERO;
	}
	
	public static boolean isOdd(BigInteger val){
		return ! isEven(val);
	}
}