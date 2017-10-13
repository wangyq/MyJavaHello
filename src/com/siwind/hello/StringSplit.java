package com.siwind.hello;

public class StringSplit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestSplit();
	}

	public static void TestSplit() {
		String str = "192.168.1.2/24 3";
		String ss[] = str.split("\\s+|\\/");
		for (String s : ss) {
			System.out.println(s);
		}
	}
}
