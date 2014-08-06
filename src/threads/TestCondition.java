package threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Title:TestCondition.java
 * 
 * Description:TestCondition.java
 * 
 * Copyright: Copyright (c) 2014-8-6
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TestCondition {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//final Business business = new Business();
		final Business1 business = new Business1();

		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 50; i++) {
					business.sub2(i);
				}
			}

		}).start();

		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 50; i++) {
					business.sub3(i);
				}
			}

		}).start();

		new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 50; i++) {
					business.main(i);
				}
			}

		}).start();

	}

	static class Business {

		private int shouldSub = 1;

		private Lock lock = new ReentrantLock();

		Condition condition1 = lock.newCondition();

		Condition condition2 = lock.newCondition();

		Condition condition3 = lock.newCondition();

		public void sub2(int i) {
			try {
				lock.lock();
				while (shouldSub != 2) {
					try {
						// this.wait();
						condition2.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 2; j++) {
					System.out.println("sub2 thread sequence is " + j
							+ " loop of " + i);
				}
				shouldSub = 3;
				// this.notify();
				condition3.signal();
			} finally {
				lock.unlock();
			}
		}

		public void sub3(int i) {
			try {
				lock.lock();
				while (shouldSub != 3) {
					try {
						// this.wait();
						condition3.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 2; j++) {
					System.out.println("sub3 thread sequence is " + j
							+ " loop of " + i);
				}
				shouldSub = 1;
				// this.notify();
				condition1.signal();
				System.out
						.println("---------------------------------------------finish sub3--------------------------------");
			} finally {
				lock.unlock();
			}
		}

		public void main(int i) {
			try {
				lock.lock();
				while (shouldSub != 1) {
					try {
						// this.wait();
						condition1.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int j = 1; j <= 2; j++) {
					System.out.println("main thread sequence is " + j
							+ " loop of " + i);
				}
				shouldSub = 2;
				// this.notify();
				condition2.signal();
			} finally {
				lock.unlock();
			}
		}
	}

	static class Business1 {

		private volatile int shouldSub = 1;

		public void sub2(int i) {
			try {
				synchronized (this) {
					while (shouldSub != 2) {
						try {
							this.wait();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					for (int j = 1; j <= 2; j++) {
						System.out.println("sub2 thread sequence is " + j
								+ " loop of " + i);
					}
					shouldSub = 3;
					
					//注意此处要用notifyAll()而不是notify(),notify()不能达到与Business相同的效果,可能会发生死锁,
					//例如可能打印如下序列:main,sub2,sub3;main然后一直处于等待中;原因:最后打印完main之后，shouldSub=2，然后此时随机notify()的
					//又是sub3,而此时shouldSub != 3,所以进入this.wait();永远等待下去
					this.notifyAll();
					//System.out.println("------------sub2----->" + shouldSub);
				}
			} finally {
			}
		}

		public void sub3(int i) {
			try {
				synchronized (this) {
					while (shouldSub != 3) {
						try {
							this.wait();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					for (int j = 1; j <= 2; j++) {
						System.out.println("sub3 thread sequence is " + j
								+ " loop of " + i);
					}
					shouldSub = 1;
					this.notifyAll();
					System.out
							.println("---------------------------------------------finish sub3--------------------------------");
				}
			} finally {
			}
		}

		public void main(int i) {
			try {
				synchronized (this) {
					while (shouldSub != 1) {
						try {
							this.wait();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					for (int j = 1; j <= 2; j++) {
						System.out.println("main thread sequence is " + j
								+ " loop of " + i);
					}
					shouldSub = 2;
					this.notifyAll();
					//System.out.println("------------main----->" + shouldSub);
				}
			} finally {
			}
		}
	}
}
