/**
 * 
 */
package com.siwind.hello;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author admin
 *
 */
public class JavaThreadPool {

	public static void testThreadPool()	{
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.execute(new PrintMessage("Hello,world!"));
		executor.execute(new PrintMessage("你好!"));
		executor.execute(new PrintMessage("Good day!"));
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //
		
		// Shut down the executor
		executor.shutdown();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		testThreadPool();
	}

}


class PrintMessage implements Runnable{
	String strMessage = "PrintMessage";
	
	public PrintMessage(){
		this("");
	}
	public PrintMessage(String str ){
		strMessage = str;
	}
    public void run(){
    	System.out.println(strMessage);
    	
        System.out.println(Thread.currentThread().getName() + "线程被调用了。");
    }   
}