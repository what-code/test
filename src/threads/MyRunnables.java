package threads;

import java.util.concurrent.locks.*;

/**
 * Title:MyRunnables.java
 * 
 * Description:MyRunnables.java
 * 
 * Copyright: Copyright (c) 2014-1-23
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class MyRunnables implements Runnable {
	private MyFoo1 foo = new MyFoo1();
	//lock
	private ReentrantLock lock = new ReentrantLock();
	
	//read and write lock
	private ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
	
	private Lock readLock = rrwl.readLock();
	
	private Lock writeLock = rrwl.writeLock();
	
	private int count = 0;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public MyFoo1 getFoo() {
		return foo;
	}

	public void setFoo(MyFoo1 foo) {
		this.foo = foo;
	}

	//请注意count 和 foo.id在相同多线程环境下的最终值有何不同
	@Override
	public void run() {
		for (int i = 0; ; i++) {
			//----------------------------------method1(多个同步操作getId和setId组成的组合操作：foo.setId(foo.getId() + 1)，若不进行同步，则结果是不正确的)
			//lock.lock();
			//foo.setId(foo.getId() + 1);
			//lock.unlock();
			
			//----------------------------------method2
//			lock.lock();
//			count++;
//			lock.unlock();
			
			//----------------------------------method3
			//lock.lock();
			//foo.add();
			//lock.unlock();
			
			//----------------------------------method4
			foo.getId();
			
//			System.out.println("--result2-->"
//					+ Thread.currentThread().getName() + "--->"
//					+ foo.getId());
			
			/*try {
				//Thread.sleep((int)Math.rint(Math.random()*10));
				Thread.sleep((int)Math.rint(Math.random()*10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
	}
}
