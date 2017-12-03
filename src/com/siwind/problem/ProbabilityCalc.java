package com.siwind.problem;

import java.util.Random;

public class ProbabilityCalc {

	public static void TestRandGenerator() {
		Random rand = new Random(); 
		 for (int j=0;j < 5;j++)
		 {
		   System.out.printf("%12d ",rand.nextInt());
		   System.out.print(", " + rand.nextLong());
		   System.out.print(", " + rand.nextFloat());
		   System.out.print(", " + rand.nextDouble());
		   System.out.println();
		 } 
	}
	
	public static void TestProbability() {
		int count = 10000000;
		System.out.println("Method1: " + TriangleProbability.Method1(count) + ", count=" + count);
		System.out.println("Method2: " + TriangleProbability.Method2(count) + ", count=" + count);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//TestRandGenerator();
		TestProbability();
	}

}

/**
 * 给定一条直线段, 随机将线段分成三段, 求这三段构成三角形的概率。
 * Solution:
 * 1) 从0-1上均匀的取一段X, 然后从剩下的 1-X中均匀的取一段Y, 剩下为Z.
 *    X,Y,Z构成三角形的条件为: 0<X<0.5, 0<Y<0.5, 0.5<X+Y<1
 *    此时概率为： P=ln(2)-1/2 = 0.193
 * 2) 在0-1上均匀的取2点X,Y, 三角形的三边为A=min{X,Y}, B=|X-Y|, C=1-max{X,Y}
 *    A,B,C构成三角形的条件为: i) 0<X<0.5, 0.5<Y<1, Y-X<0.5, ii) 0<Y<0.5, 0.5<X<1, X-Y<0.5,
 *    此时概率为： P=1/4=0.25 
 * @author admin
 *
 */
class TriangleProbability{
	
	private static boolean isOK(float a, float b, float c) {
		return (a>0) && (b>0) && (c>0) &&(a+b>c) && (b+c>a) && (c+a>b);
	}
	
	public static float Method1(int count) {
		float p = 0.0f;
		Random rand = new Random();
		int num = 0;
		for(int i=0;i<count;i++) {
			float x = rand.nextFloat();
			float y = rand.nextFloat()*(1-x);
			if( isOK(x,y,1-x-y)) num ++;
		}
		p = num/(float)count;
		return p;
	}
	
	public static float Method2(int count) {
		float p = 0.0f;
		Random rand = new Random();
		int num = 0;
		for(int i=0;i<count;i++) {
			float x = rand.nextFloat();
			float y = rand.nextFloat();
			if( isOK(Math.min(x, y),Math.abs(x-y), 1- Math.max(x, y))) num ++;
		}
		p = num/(float)count;
		return p;
	}
}
