package com.siwind.hello;

import java.math.BigDecimal;

public class Inequality {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Test1();
		Test2();
	}

	public static void  Test2() {
		TestFuncName(10);
		TestFuncName(100);
		TestFuncName(200);
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
		TestInequality(10, 0, 1.0, 0.001);
	}
	/**
	 * sqrt(x^2 + 2x*cos[k*PI/2n] + 1
	 * 
	 * @param x
	 * @param n
	 * @param k
	 * @return
	 */
	public static double TrigoFunc(double x, int n , int k){
		double v = 0;
		double alfa = k*Math.PI/(2*n);
		v = Math.sqrt(x*x + 2*x*Math.cos(alfa) + 1);
		return v;
	}
	/**
	 * 
	 * @param n
	 * @param x
	 * @return
	 */
	public static double InEqualityValue(int n, double x) {
		double v = 0;
		int k=0;
		for(k=1;k<=2*n-1; k+=2){
			v += TrigoFunc(x, n, k);
		}
		for(k=2;k<2*n-1; k+=2){
			v -= TrigoFunc(x, n, k);
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
		for(x=low;x<=hight;x+=dx){
			y = InEqualityValue(n, x);
			System.out.println("(" +x+",     "+y+") ");
		}
		System.out.println("Done");
	}
}
