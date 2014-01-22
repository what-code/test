package test;

import java.util.Date;
import java.util.concurrent.*;

/**
 * Title:TestSemaphore1.java
 * 
 * Description:TestSemaphore1.java
 * 
 * Copyright: Copyright (c) 2014-1-6
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TestSemaphore1 {
	final static int MAX_QPS = 10;

	final static Semaphore semaphore = new Semaphore(MAX_QPS);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if(semaphore.availablePermits() <= MAX_QPS){
					semaphore.release(5);
				}
				System.out.println("---->" + semaphore.availablePermits());
			}

		}, 1000, 500, TimeUnit.MILLISECONDS);

		// lots of concurrent calls:100 * 1000
		ExecutorService pool = Executors.newFixedThreadPool(100);
		//for (int i = 100; i > 0; i--) {
			//final int x = i;
			pool.submit(new Runnable() {
				@Override
				public void run() {
					for (int j = 100; j > 0; j--) {
						semaphore.acquireUninterruptibly(1);
						//remoteCall(x, j);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		//}

		pool.shutdown();

		try {
			pool.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("------------>DONE");
	}

	private static void remoteCall(int i, int j) {
		/*System.out.println(String.format("%s - %s: %d %d", new Date(),
				Thread.currentThread(), i, j));*/
	}

}
