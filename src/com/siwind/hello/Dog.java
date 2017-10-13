package com.siwind.hello;

public class Dog implements ISay {

	@Override
	public void say() {
		// TODO Auto-generated method stub
		System.out.println("Hello world, I am Dog!");
		
		print();
		
		//now cat
		Cat cat = new Cat();
		cat.say();
		cat.print();
		//
	}

	public void print(){
		
		System.out.println("Dog's Class Loader is : ");
		
		ClassLoader cl = Dog.class.getClassLoader();
        while(cl != null){
            System.out.print(cl + "-->");
            cl = cl.getParent();
        }
        System.out.println(cl);
	}
}
