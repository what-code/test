package zookeeper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.zookeeper.KeeperException;

/**
 * Title:TestDistributedLock.java
 * 
 * Description:TestDistributedLock.java
 * 
 * Copyright: Copyright (c) 2014-8-21
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TestDistributedLock {

	public void test_lock() {
		ExecutorService exeucotr = Executors.newCachedThreadPool();
		final int count = 50;
		final CountDownLatch latch = new CountDownLatch(count);

		final DistributedReentrantLock lock = new DistributedReentrantLock(
				"/test_distributed_lock"); // 单个锁
		for (int i = 0; i < count; i++) {
			exeucotr.submit(new Runnable() {
				public void run() {
					try {
						Thread.sleep(1000);
						lock.lock();
						Thread.sleep(100 + RandomUtils.nextInt(100));
						System.out.println("id: " + lock.getId()
								+ " is leader: " + lock.isOwner());
					} catch (Exception e) {
					} finally {
						latch.countDown();
						try {
							lock.unlock();
						} catch (KeeperException e) {
						}
					}

				}
			});
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			// want.fail();
		}

		exeucotr.shutdown();
	}

}
