package com.siwind.hello;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class B implements IColor {

	private static final int GREEN = 0;
	private static final int RED = 1;
	private static final int YELLOW = 2;
	
	private static final String[] names = new String[]{"GREEN","RED","YELLOW"};
	
	private int color = GREEN;
	
	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public void say() {
		// TODO Auto-generated method stub
		System.out.print("Color is: " + color);
	}

	@Override
	public String getColorName() {
		// TODO Auto-generated method stub
		return names[color];
		//return null;
	}

}
