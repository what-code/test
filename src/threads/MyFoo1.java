package threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Title:MyFoo.java
 * 
 * Description:MyFoo.java
 * 
 * Copyright: Copyright (c) 2014-1-23
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class MyFoo1 {
	//lock
	private ReentrantLock lock = new ReentrantLock();
	
	//read and write lock
	private ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
		
	private Lock readLock = rrwl.readLock();
		
	private Lock writeLock = rrwl.writeLock();

	private int num = 0;

	public int getId() {
		//synchronized (this) {
		readLock.lock();
		if(num % 1000 == 0){
			System.out.println(Thread.currentThread().getName() + "--max-->" + num);
			//num = -1;
		}
		readLock.unlock();
		return num;
		//}
	}

	public void setId(int num) {
		//synchronized (this) {
			this.num = num;
		//}
	}
	
	//synchronized 或 lock皆可同步
	public void add(){
		synchronized (this) {
			num++;
		}
	}
	
	public void write(){
		writeLock.lock();
		num++;
		writeLock.unlock();
	}
}
