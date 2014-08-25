package zookeeper;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.zookeeper.ZooKeeper;

/**
 * Title:TestMyDLock.java
 * 
 * Description:TestMyDLock.java
 * 
 * Copyright: Copyright (c) 2014-8-22
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TestMyDLock {
	/**
	 * 处理业务逻辑
	 */
	public static void doAction(MyDistributedLock lock){
		System.out.println("-----doAction() begin------" + lock.getZookeeper().getSessionId());
		try {
			System.out.println("------------is doAction()...");
			Thread.sleep(10000 + RandomUtils.nextInt(2000));
			
			//测试可重入锁
			if("/dl_root/lock_0000000075".equals(lock.getCurPath())){
				lock.lock();
				System.out.println("------------test Reentrant lock...");
				Thread.sleep(5000 + RandomUtils.nextInt(2000));
				lock.unlock();
				
				Thread.sleep(8000 + RandomUtils.nextInt(2000));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("-----doAction() end------" + lock.getZookeeper().getSessionId());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyDistributedLock lock = new MyDistributedLock();
		lock.lock();
		doAction(lock);
		lock.unlock();
	}

}
