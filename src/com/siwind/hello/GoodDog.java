/**
 * 
 */
package com.siwind.hello;

/**
 * @author wang
 *
 */
//public class GoodDog extends Dog {   //It is the same !
public class GoodDog extends Dog implements ISay {

	/* (non-Javadoc)
	 * @see com.siwind.hello.Dog#say()
	 */
	@Override
	public void say() {
		// TODO Auto-generated method stub
		//super.say();
		System.out.println("Hello world, I am GoodDog!");
	}

}
