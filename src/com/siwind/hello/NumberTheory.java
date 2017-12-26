package com.siwind.hello;

public class NumberTheory {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ProblemEgg();
	}

	/**
	 * 
一筐鸡蛋：
1个1个拿，正好拿完。
2个2个拿，还剩1个。
3个3个拿，正好拿完。
4个4个拿，还剩1个。
5个5个拿，还差1个。
6个6个拿，还剩3个。
7个7个拿，正好拿完。
8个8个拿，还剩1个。
9个9个拿，正好拿完。

问筐里最少有多少鸡蛋？
Answer is: 2520K+441, where k=0,1,2,3...
	 */
	public static void ProblemEgg() {
		int max = 10000000;
		int eggs = 1;
		int i = 1;
		do {
			i += 2;
			eggs = 63*i;
			if( eggs % 2 != 1 ) continue;
			if( eggs % 3 != 0 ) continue;
			if( eggs % 4 != 1 ) continue;
			if( eggs % 5 != 1 ) continue;
			if( eggs % 6 != 3 ) continue;
			if( eggs % 7 != 0 ) continue;
			if( eggs % 8 != 1 ) continue;
			if( eggs % 9 != 0 ) continue;
			System.out.println(eggs);
		}while(eggs < max);
	}
}
