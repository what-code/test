package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

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
	private static ThreadPoolExecutor tpe = null;
	//结果列表
	List<Future> list = new ArrayList<Future>();
		
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
		for (int i = 0; i < 100; i++) {
			Future f = tpe.submit(new Runnable() {
				public void run() {
					for (int j = 0; j < 200; j++) {
						//synchronized(foo){
							foo.setId(foo.getId() + 1);
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

	class MyFoo {
		private ReentrantLock lock = new ReentrantLock();
		
		private int num = 0;

		public synchronized int getId() {
			return num;
		}

		public synchronized void setId(int id) {
			/*lock.lock();
			try{
				this.num = id;
			}finally{
				lock.unlock();
			}*/
			this.num = id;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TestReentrantLock().test();
	}

}
