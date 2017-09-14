package com.siwind.hello;

public class PrimeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TestPrime();
	}
	public static void TestPrime() {
		
		int n = 2<<30-1;
		int k=0;
		for( int i=5;i<=n;i+=2){
			if( isPrime( i )){
				//k++;
				//System.out.print(i + ", ");
				//if( k%30 == 0 ) System.out.println();
				
				//DoTest(i);
			}
		}
		
	}
 
	public static void DoTest(int n) {
		int[] remain = new int[]{1,5,7,11,13,17};
		int i, k = n %18;
		
		for(  i=0;i<remain.length;i++){
			if( k == remain[i] ) break;
		}
		if( i>=remain.length ){
			System.out.println(n + " - " + k);
		}
	}
	public static boolean  isPrime(int n) {
		boolean bOK = true;
		int m = (int) Math.sqrt((double)n) + 1;
		if( n%2 != 0 ){
			for( int i=3; i<=m;i+=2){
				if( n%i == 0 ) {
					bOK = false;
					break;
				}
			}
		}
		
		return bOK;
		
	}
}
