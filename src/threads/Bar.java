package threads;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Title:Bar.java
 * 
 * Description:Bar.java
 * 
 * Copyright: Copyright (c) 2014-3-31
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class Bar {
	//type1
	public static volatile String status1 = "";
	
	//type2,abc/012
	public volatile int status = 2;
	public Object obj1;
	public Object obj2;
	public Object obj3;
	
	public ReentrantLock lock1 = new ReentrantLock();
	public ReentrantLock lock2 = new ReentrantLock();
	public ReentrantLock lock3 = new ReentrantLock();
	private AtomicInteger a1 = new AtomicInteger(0);
	private AtomicInteger a2 = new AtomicInteger(0);
	private AtomicInteger a3 = new AtomicInteger(0);
	
	Bar(){
		obj1 = new Object();
		obj2 = new Object();
		obj3 = new Object();
	}

	public void printA(){
		synchronized(obj1){
			if(status == 2){
				//此时不再重新检查status的状态-----s
				//System.out.println("AA%%%%---->" + status);
				//此时不再重新检查status的状态-----e
				
				//此时不再重新检查status的状态,阻塞在此处的线程会继续执行，所以先同步，再检查status的值
				//synchronized(obj1){
					System.out.println("--A--");
					status = 0;
				//}
			}
		}
	}
	
	public void printB(){
		synchronized(obj2){
			if(status == 0){
				//synchronized(obj2){
					System.out.println("--B--");
					status = 1;
				//}
			}
		}
	}
	
	public void printC(){
		//调用wait() 或 notify()时，必须已获取了改对象的锁，否则出现IllegalMonitorStateException
		//比如在此处调用obj3.notifyAll();
		synchronized(obj3){
			//等待队列长度大于8，则唤醒obj3的所有的等待线程
			if(a3.get() >= 8){
				obj3.notifyAll();
				a3.set(0);
				System.out.println("--C--#######---clear wait()----");
			}
			
			if(status == 1){
				//synchronized(obj3){
					System.out.println("%%--C--%%");
					status = 2;
				//}
				obj3.notifyAll();
			}else{
				//System.out.println("--C--#######---wait()----");
				try {
					a3.incrementAndGet();
					obj3.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void printS(){
		System.out.println("----------------------------------------------status-->" + status);
	}

	protected void execute(){
		
	}
	
	public static void main(String[] args){
		//type1(有问题)-------------------------------------------->
		Bar bar1 = new Bar1();
		Bar bar2 = new Bar2();
		Bar bar3 = new Bar3();
		
		/*for(int i = 0;i < 10;i++){
			new BarThread(bar1).start();
		}
		
		for(int i = 0;i < 10;i++){
			new BarThread(bar2).start();
		}
		
		for(int i = 0;i < 10;i++){
			new BarThread(bar3).start();
		}*/
		
		//type2-------------------------------------------->
		final Bar bar = new Bar();
		for(int i = 0;i < 10;i++){
			new Thread(new Runnable(){
				public void run(){
					while(true){
						bar.printA();
					}
				}
			}).start();
		}
		
		for(int i = 0;i < 10;i++){
			new Thread(new Runnable(){
				public void run(){
					while(true){
						bar.printB();
					}
				}
			}).start();
		}
		
		for(int i = 0;i < 10;i++){
			new Thread(new Runnable(){
				public void run(){
					while(true){
						bar.printC();
					}
				}
			}).start();
		}/**/
	}
}
