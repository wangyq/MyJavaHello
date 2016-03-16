package com.siwind.hello;

public class A implements IColor{

	public enum Status{
		UNKOWN, RED, GREEN, YELLOW;
	}
	
	private Status status;
	
	public A(){
		status = Status.UNKOWN;
	}
	
	public int getColor(){
		return status.ordinal();
	}
	@Override
	public void say() {
		// TODO Auto-generated method stub
		System.out.print("Color is: " + status);
	}

	@Override
	public String getColorName() {
		// TODO Auto-generated method stub
		String str = String.valueOf(this.status.ordinal()) + ":" + status.name();
		return str;
	}
}
