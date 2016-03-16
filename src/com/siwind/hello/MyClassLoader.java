/**
 * 
 */
package com.siwind.hello;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author wang
 * 
 */
public class MyClassLoader extends ClassLoader {

	private String path = "D:\\000\\java\\classes\\";
	private String fileType = ".class";

	public MyClassLoader() {
		// super(); //here is not needed!
	}

	/**
	 * 这个方法最好不实现，除非需要改变ClassLoader的向上委托机制
	 */
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
		//default implemntation!
		//return super.loadClass(name);
		
		Class<?> cl = null;
		
		cl = loadClassInternal(name); // Here is not parent ClassLoader first
										// now!
		if (cl == null) {
			
			cl = super.loadClass(name); //进入父类的调用后, loadClass()失败后还会进入子类的findClass()方法一次(重复调用)！
			
		}
		
		// return now!
		return cl;
	}

	/**
	 * 当调用到这里时, class未找到, 因此直接读取".class"文件的内容返回!
	 * 
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// TODO Auto-generated method stub

		byte[] data = this.loadClassData(name);// 获得类文件的字节数组
		if (data != null) {
			return this.defineClass(name, data, 0, data.length);
		} else {
			return null;
		}

		// return super.findClass(name);
	}

	/**
	 * 此类的loadClass方法， 不包含委托机制。
	 * 委托机制在重载的loadClass()方法中，可以调整向上委托/或者下级委托机制优先等。
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 */
	private Class<?> loadClassInternal(String name) throws ClassNotFoundException {
		Class<?> cl = null;
		synchronized (getClassLoadingLock(name)) { // 这里是一个可以重入的this对象锁
			//避免重复加载
			cl = findLoadedClass(name); // first , find if class is loaded!
			if (cl == null) {
				cl = findClass(name);
			}
		}
		return cl;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	private byte[] loadClassData(String name) {
		String strClassName = null;
		InputStream is = null;
		byte[] data = null;
		ByteArrayOutputStream baos = null;
		try {
			strClassName = name.replace(".", File.separator);
			String fileName = path + strClassName + fileType;

			File file = new File(fileName);
			if(file.isFile()) {
				is = new FileInputStream(new File(fileName));
				baos = new ByteArrayOutputStream();
				int ch = 0;
				while (-1 != (ch = is.read())) {
					baos.write(ch);
				}
				data = baos.toByteArray();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if( is!= null ){
					is.close();
				}
				if( baos != null ){
					baos.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * 
	 */
	public void print() {
		ClassLoader cl = this;
		while (cl != null) {
			System.out.print(cl + "-->");
			cl = cl.getParent();
		}
		System.out.println(cl);
	}
}
