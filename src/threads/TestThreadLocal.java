package threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.*;

/**
 * Title:TestThreadLocal.java
 * 
 * Description:TestThreadLocal.java
 * 
 * Copyright: Copyright (c) 2014-1-22
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TestThreadLocal {
	//cpu数量
	private static int cpus = 1;
	//任务数量
	private static final int TASKS_NUM = 80;
	private static ThreadPoolExecutor tpe = null;
	//结果列表
	List<Future> list = new ArrayList<Future>();
	static {
		cpus = Runtime.getRuntime().availableProcessors();
		tpe = new ThreadPoolExecutor(cpus, cpus + 1, 60, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(100),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	private static ThreadLocal<Integer> tl = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return 0;
		}
	};

	/**
	 * test
	 */
	public void test() {
		System.out.println("--cpus-->" + cpus);
		for (int i = 0; i < TASKS_NUM; i++) {
			Future future = tpe.submit(new Runnable() {
				public void run() {
					for (int i = 0; i < 200; i++) {
						tl.set(tl.get() + 1);
						System.out.println(Thread.currentThread().getName()
								+ "---->" + tl.get() + "----->" + tpe.getMaximumPoolSize());
						/*try {
							Thread.sleep((int)Math.rint(Math.random()*10));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}*/
					}
				}
			});
			list.add(future);
		}
		
		/*for(int i = 0;i < list.size();i++){
			Future f = list.get(i);
			try {
				f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("--begin to shutdown thread pool-->" + tpe.isShutdown());
		tpe.shutdown();
		System.out.println("--is thread pool shutdown?-->" + tpe.isShutdown());*/
		shutDownPool(list,tpe);
	}
	
	/**
	 * shutdown pool
	 * @param list
	 * @param pool
	 */
	public static void shutDownPool(List<Future> list,ThreadPoolExecutor pool){
		for(int i = 0;i < list.size();i++){
			Future f = list.get(i);
			try {
				f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("--begin to shutdown thread pool-->" + pool.isShutdown());
		pool.shutdown();
		System.out.println("--is thread pool shutdown?-->" + pool.isShutdown());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TestThreadLocal().test();
		/*new Thread(new Runnable(){
			public void run(){
				while(true){
					try {
						Thread.sleep(1000);
						System.out.println("--left-->" + tpe.getCompletedTaskCount() + "---->" + tpe.isTerminated());
						if(tpe.getCompletedTaskCount() == TASKS_NUM && !tpe.isShutdown()){
							tpe.shutdown();
							System.exit(0);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();*/
	}

}
