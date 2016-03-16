package com.siwind.hello;

public class Cat implements ISay {

	@Override
	public void say() {
		// TODO Auto-generated method stub
		System.out.println("Hello world, I am cat!");
	}

	public void print(){
		
		System.out.println("Cat's Class Loader is : ");
		
		ClassLoader cl = Cat.class.getClassLoader();
        while(cl != null){
            System.out.print(cl + "-->");
            cl = cl.getParent();
        }
        System.out.println(cl);
	}
}
