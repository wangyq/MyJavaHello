/**
 * 
 */
package com.siwind.hello;

import java.lang.reflect.Method;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


/**
 * @author wang
 *
 */
public class Hello {
	
	static double log3=Math.log(3);

	/**
	 * get the level of node n. level is begin from 0,1,...
	 * 
	 * @param n
	 * @return
	 */
	static int getLevel(int n){
		int l = 0;
		while(n>0){
			n = (n-1)/3;
			l++;
		}
		return l;
	}
	
	static int change(int n){
		int sum = 0, l;
		l = getLevel(n);
		
		sum = 1;
		for(int i=1;i<=l;i++){
			sum *= 3;
		}
		sum = 2*sum -2;
		return sum - n;
	}
	
	
	static int exchange(int n){
		int level = (int)((Math.log(2*n+1)/log3)-1.0);
		int sum = 1;
		
		level += 1;
		for(int i=1;i<=level;i++){
			sum *= 3;
		}
		sum = 2*sum - 2;
		
		return sum - n;
	}

	static int parent(int n){
		int p = (n-1)/3;
		return change(p);
	}
	
	static int comm_parent(int n, int m){
		if( n>m ){//make sure n <= m
			int t = n;
			n = m;
			m = t;
		}
		if( n == m ) return n;
		return comm_parent(n, parent(m));
	}
	
	static void test(){
		int n = 13, m = 9;
		int p = comm_parent(n,m);
		System.out.println(p);
	}
	
	public static void testPackage(){
		char[] c = "1234567890".toCharArray();  
        String s = new String(c,0,10); 
        System.out.println("testPackage: " + s);
	}
	
	public static void testForName(){
		try {          
            //printing ClassLoader of this class
            System.out.println("Hello.getClass().getClassLoader() : "
                                 + Hello.class.getClassLoader());

          
            //trying to explicitly load this class again using Extension class loader
            Class.forName("com.siwind.hello.Hello", true 
                            ,  Hello.class.getClassLoader().getParent());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


	}
	public static void showClassLoader(){
		
        System.out.println("System绫荤殑鍔犺浇鍣ㄧ殑鍚嶇О:"+System.class.getClassLoader());
        System.out.println("List绫荤殑鍔犺浇鍣ㄧ殑鍚嶇О:"+List.class.getClassLoader());
        System.out.println("绯荤粺绫荤殑鍔犺浇鍣ㄧ殑鍚嶇О:"+ClassLoader.getSystemClassLoader().getClass().getName());
        System.out.println("褰撳墠绾跨▼鐨勫姞杞藉櫒鐨勫悕绉�:"+Thread.currentThread().getContextClassLoader().getClass().getName());
        
        System.out.println("Hello绫荤殑鍔犺浇鍣ㄧ殑鍚嶇О:"+Hello.class.getClassLoader().getClass().getName());
        System.out.println("Dog绫荤殑鍔犺浇鍣ㄧ殑鍚嶇О:"+Dog.class.getClassLoader().getClass().getName()); 
        System.out.println("Cat绫荤殑鍔犺浇鍣ㄧ殑鍚嶇О:"+Cat.class.getClassLoader().getClass().getName());
        
        ClassLoader cl = Hello.class.getClassLoader();
         
        while(cl != null){
            System.out.print(cl + "-->");
            
            cl = cl.getParent();
        }
        System.out.println(cl);
	}
	/**
	 * 
	 */
	public static void testSay(){
		try {
			System.out.println("==Begin testSay()==");
			Dog dog = new Dog();
			dog.say();			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public static void initClassLoader() {
		try{
			Class<?> cl = null;
			ClassLoader cloader = new MyClassLoader();
			cl = cloader.loadClass("com.siwind.hello.Dog");
			Object obj = cl.newInstance();
			Method method = cl.getMethod("say", (Class []) null);
			method.invoke(obj, (Object []) null);
			
			Thread.currentThread().setContextClassLoader(cloader); //add here!
			
			//load not exist class!
			cl = cloader.loadClass("haha.HelloWorld");  //test the number of calling MyClassLoader.findClass()
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Test class loader now!
	 */
	public static void testClassLoader(){
		
		//
		initClassLoader();
		
		showClassLoader();
		
		testSay();
	}
	
	public static void testEnumMemory(){
		String strColor = null;
		int maxlen = 1000000;
		IColor[] colors = new IColor[maxlen];
		for(int i=0; i<maxlen; i++){
			//colors[i] = new A();
			colors[i] = new B();
			strColor = colors[i].getColorName();
			
		}
	}
	public static void test_gooddog(){
		ISay dog = new GoodDog();
		dog.say();
	}
	public static void testQueue1(){
		RoundQueue<Integer> queue = new RoundQueue<Integer>();

		queue.push(2);queue.push(5);queue.push(8);

		while (!queue.isEmpty() ){
			System.out.print(queue.pop() + " ");
		}

		System.out.println();
	}

	public static void testQueue2(){
		Queue<Integer> queue = new PriorityQueue<>();
		queue.offer(2);
		queue.offer(3);
		queue.offer(2);
		queue.offer(3);
		for( Integer v: queue){
			System.out.print(v + " ");
		}
		System.out.println();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		testQueue2();
		//testPackage();
		
		//testForName();
		
		//showClassLoader();
		
		//test();
		
		//testClassLoader();
		
		//testEnumMemory();
		
		//test_gooddog();
		
		System.out.println("Hello, world!");
	}

}
