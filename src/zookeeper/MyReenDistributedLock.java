package zookeeper;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.zookeeper.KeeperException;

/**
 * Title:MyReenDistributedLock.java
 * 
 * Description:MyReenDistributedLock.java
 * 
 * Copyright: Copyright (c) 2014-8-25
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class MyReenDistributedLock extends MyDistributedLock {
	private ReentrantLock lock;

	public MyReenDistributedLock(){
		
	}
	
	public void lock() {
		lock.lock();
		super.lock();
	}
	
	public void unlock(){
		super.unlock();
		lock.unlock();
	}
	
	public boolean tryLock(){  
        //多线程竞争时，先拿到第一层锁  
        return lock.tryLock() && super.tryLock();  
    } 
	
	protected boolean isHeldByCurrentClient(){
		return lock.isHeldByCurrentThread() && super.isHeldByCurrentClient();
	}
}
