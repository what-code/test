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
	public static void doAction(ZooKeeper zookeeper){
		System.out.println("-----doAction() begin------" + zookeeper.getSessionId());
		try {
			System.out.println("------------is doAction()...");
			Thread.sleep(100000 + RandomUtils.nextInt(2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("-----doAction() end------" + zookeeper.getSessionId());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyDistributedLock lock = new MyDistributedLock();
		lock.lock();
		doAction(lock.getZookeeper());
		lock.unlock();
	}

}
