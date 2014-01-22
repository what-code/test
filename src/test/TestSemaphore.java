package test;

import java.util.concurrent.*;

/**
 * Title:TestSemaphore.java
 * 
 * Description:TestSemaphore.java
 * 
 * Copyright: Copyright (c) 2014-1-6
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TestSemaphore {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		
		// 只能5个线程同时访问
		final Semaphore semp = new Semaphore(5,true);
		// 模拟20个客户端访问
		for (int index = 0; index < 20; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					try {
						// 获取许可
						semp.acquire();
						System.out.println("Accessing: " + NO);
						Thread.sleep((long) (Math.random() * 10000));
						// 访问完后，释放
						semp.release();
						System.out.println("left places-----" + NO + "------->"
								+ semp.availablePermits() + "-----" + semp.getQueueLength() + "-----" + semp.isFair());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			exec.execute(run);
		}
		// 退出线程池
		exec.shutdown();
	}
}
