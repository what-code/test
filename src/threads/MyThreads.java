package threads;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Title:MyThreads.java
 * 
 * Description:MyThreads.java
 * 
 * Copyright: Copyright (c) 2014-1-23
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class MyThreads extends Thread {

	private MyFoo1 foo = new MyFoo1();
	
	private int count = 0;
	
	//lock
	private ReentrantLock lock = new ReentrantLock();
	
	public MyFoo1 getFoo() {
		return foo;
	}

	public int getCount() {
		return count;
	}

	public void setFoo(MyFoo1 foo) {
		this.foo = foo;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void run(){
		for (int i = 0; i < 200; i++) {
			//----------------------------------method1
			lock.lock();
			foo.setId(foo.getId() + 1);
			lock.unlock();
			
			//----------------------------------method2
//			lock.lock();
//			count++;
//			lock.unlock();
			
			//----------------------------------method3
			lock.lock();
			foo.add();
			lock.unlock();
			
//			System.out.println("--result2-->"
//					+ Thread.currentThread().getName() + "--->"
//					+ foo.getId());
			
//			try {
//				Thread.sleep((int)Math.rint(Math.random()*10));
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
}
