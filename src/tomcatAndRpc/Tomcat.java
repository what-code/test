package tomcatAndRpc;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;


/**
 * Title:TestTomcat.java
 * 
 * Description:TestTomcat.java
 * 
 * Copyright: Copyright (c) 2014-9-1
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class Tomcat {
	private Object obj = new Object();
	private AtomicInteger count = new AtomicInteger(0);
	private Logger logger = Logger.getLogger(Tomcat.class);
	private Random r = new Random();
	
	public void acceptRequest(){
		for(int i = 0;i < 12;i++){
			new Thread(new Runnable(){
				public void run() {
					count.incrementAndGet();
					logger.info(Thread.currentThread()+ "------>sss");
					try {
						Thread.sleep(20000 + r.nextInt(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized(obj){
						obj.notify();
					}
					count.decrementAndGet();
					logger.info(Thread.currentThread()+ "------>eee");
				}
			},"---worker---" + i).start();
		}
	}
	
	protected class Acceptor implements Runnable {
		public void run() {
			acceptRequest();
			while(true){
				if(count.get() >= 10){
					synchronized(obj){
						try {
							logger.info("-----------------Max Thread-------------" + count.get());
							obj.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Tomcat tomcat = new Tomcat();
		new Thread(tomcat.new Acceptor()).start();
		
	}

}
