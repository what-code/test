package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Title:TestReentrantLock.java
 * 
 * Description:TestReentrantLock.java
 * 
 * Copyright: Copyright (c) 2014-1-22
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TestReentrantLock {
	private  MyFoo foo = new MyFoo();

	// cpu数量
	private static int cpus = 1;
	//task num
	private static final int tasks = 100;
	//pool
	private static ThreadPoolExecutor tpe = null;
	//结果列表
	private List<Future> list = new ArrayList<Future>();
	//countDown
	private CountDownLatch cdt = new CountDownLatch(tasks);
	//lock
	private ReentrantLock lock = new ReentrantLock();
	
	//read and write lock
	private ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
		
	private Lock readLock = rrwl.readLock();
		
	private Lock writeLock = rrwl.writeLock();
	
	static {
		cpus = Runtime.getRuntime().availableProcessors();
		tpe = new ThreadPoolExecutor(cpus, cpus * 5, 60, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(200),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	/**
	 * test
	 */
	public void test() {
		for (int i = 0; i < tasks; i++) {
			Future f = tpe.submit(new Runnable() {
				public void run() {
					for (int j = 0; j < 200; j++) {
						//synchronized(foo){
							//foo.setId(foo.getId() + 1);
							foo.add();
						//}
						System.out.println("--result-->"
								+ Thread.currentThread().getName() + "--->"
								+ foo.getId());
						try {
							Thread.sleep((int)Math.rint(Math.random()*10));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
				}
			});
			list.add(f);
		}
		TestThreadLocal.shutDownPool(list, tpe);
		System.out.println("--final result-->" + foo.getId());
	}

	/**
	 * test1
	 */
	public void test1() {
		for (int i = 0; i < tasks; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 200; j++) {
						//lock.lock();
						//synchronized(foo){
							foo.setId(foo.getId() + 1);
						//}
						//lock.unlock();
						System.out.println("--result-->"
								+ Thread.currentThread().getName() + "--->"
								+ foo.getId());
						try {
							Thread.sleep((int)Math.rint(Math.random()*10));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
					cdt.countDown();
				}
			}).start();
		}
		
		try {
			cdt.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("--final result-->" + foo.getId());
	}
	
	/**
	 * test2--请对比test1和test2的不同(提交多个任务与一个任务提交多次、Lock在runnable及Myfoo)
	 */
	public void test2() {
		list = new ArrayList<Future>();
		MyRunnables mr = new MyRunnables();
		for (int i = 0; i < tasks; i++) {
			Future f = tpe.submit(mr);
			list.add(f);
		}
		TestThreadLocal.shutDownPool(list, tpe);
		System.out.println("--final result-->" + mr.getFoo().getId());
	}

	/**
	 * test3--read & write lock
	 */
	public void test3() {
		list = new ArrayList<Future>();
		final MyFoo1 foo = new MyFoo1();
		MyRunnables mr = new MyRunnables();
		mr.setFoo(foo);
		for (int i = 0; i < tasks; i++) {
			Future f = tpe.submit(mr);
			list.add(f);
		}
		
		for(int i = 0;i < 5;i++){
			new Thread(new Runnable() {
				public void run(){
					for(int i = 0;i < 100000000;i++){
						foo.write();
						try {
							Thread.sleep((int)Math.rint(Math.random()*10));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		TestThreadLocal.shutDownPool(list, tpe);
		System.out.println("--final result-->" + mr.getFoo().getId());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TestReentrantLock().test3();
	}

}
