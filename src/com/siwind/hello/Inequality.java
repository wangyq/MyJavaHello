package com.siwind.hello;

import java.math.BigDecimal;

public class Inequality {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test1();
		Test2();
	}

	public static void  Test2() {
		TestFuncName(4);
		//TestFuncName(10);
		//TestFuncName(100);
		//TestFuncName(400);
		//TestFuncName1(10);
		//TestFuncName1(200);
		//TestFuncName2(10);
		//TestFuncName2(200);
	}
	
	public static void TestFuncName2(int n) {
		int  i=0;
		StringBuilder str = new StringBuilder("");
		
		for(i=1;i<n;i+=2){
			str.append("Sqrt[x^2 + 1 + 2*x*Cos[" + i +"Pi/"+n+"]] " );  //Sqrt[x^2 + 1 + 2*x*Cos[Pi/10]]
			if( i<n-1){
				str.append("+");
			}
		}
		str.append(" + x - (");
		for(i=0;i<n-1;i+=2){
			str.append("Sqrt[x^2 + 1 + 2*x*Cos[" + i +"Pi/"+n+"]] " );  //Sqrt[x^2 + 1 + 2*x*Cos[Pi/10]]
			if( i <n-2){
				str.append("+");
			}
		}
		str.append(")");
		
		System.out.println(str);
	}
	
	/**
	 * 
	 * @param n
	 */
	public static void TestFuncName1(int n) {
		int  i=0;
		StringBuilder str = new StringBuilder("");
		
		for(i=1;i<n;i+=2){
			str.append("Sqrt[x^2 + 1 - 2*x*Cos[" + i +"Pi/"+n+"]] " );  //Sqrt[x^2 + 1 + 2*x*Cos[Pi/10]]
			if( i<n-1){
				str.append("+");
			}
		}
		str.append("-( 1+ ");
		for(i=2;i<n;i+=2){
			str.append("Sqrt[x^2 + 1 - 2*x*Cos[" + i +"Pi/"+n+"]] " );  //Sqrt[x^2 + 1 + 2*x*Cos[Pi/10]]
			if( i <n-2){
				str.append("+");
			}
		}
		str.append("  )");
		
		System.out.println(str);
	}
	
	public static void TestFuncName(int n) {
		int  i=0;
		StringBuilder str = new StringBuilder("");
		
		for(i=1;i<n;i+=2){
			str.append("Sqrt[x^2 + 1 + 2*x*Cos[" + i +"Pi/"+n+"]] " );  //Sqrt[x^2 + 1 + 2*x*Cos[Pi/10]]
			if( i<n-1){
				str.append("+");
			}
		}
		str.append("-(");
		for(i=2;i<n-1;i+=2){
			str.append("Sqrt[x^2 + 1 + 2*x*Cos[" + i +"Pi/"+n+"]] " );  //Sqrt[x^2 + 1 + 2*x*Cos[Pi/10]]
			if( i <n-2){
				str.append("+");
			}
		}
		str.append(")");
		
		System.out.println(str);
	}
	
	public static void Test1() {
		TestInequality(2, 0, 1.0, 0.01);
	}
	/**
	 * sqrt(x^2 + 2x*cos[k*PI/2n] + 1
	 * 
	 * @param x
	 * @param n
	 * @param k
	 * @return
	 */
	public static double TrigoFunc(double x, int n , int k, int sig){
		double v = 0;
		double alfa = k*Math.PI/(2*n);
		v = Math.sqrt(x*x + 2*sig*x*Math.cos(alfa) + 1);
		return v;
	}
	/**
	 * 
	 * @param n
	 * @param x
	 * @return
	 */
	public static double InEqualityValue(int n, double x, int sig) {
		double v = 0;
		int k=0;
//		for(k=1;k<=n; k++){
//			v += TrigoFunc(x, n, 2*k-1);
//		}
		v+= x;
		for(k=1;k<=n; k++){
			v += TrigoFunc(x, n, 2*k, sig);
		}
		for(k=1;k<=n; k++){
			v -= TrigoFunc(x, n, 2*k-1,sig);
		}
			
		return v ;
	}
	
	/**
	 * 
	 * @param n
	 * @param low
	 * @param hight
	 */
	public static void TestInequality(int n, double low, double hight, double dx) {
		double x = 0, y=0;
		int sig=1;
		for(x=low;x<=hight;x+=dx){
			y = InEqualityValue(n, x,sig);
			System.out.println("(" +x+",     "+y+") ");
		}
		System.out.println("Done");
	}
}
