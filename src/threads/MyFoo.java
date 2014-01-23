package threads;

import java.util.concurrent.locks.ReentrantLock;

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
public class MyFoo {

	private ReentrantLock lock = new ReentrantLock();

	private int num = 0;

	public int getId() {
		//synchronized (this) {
			return num;
		//}
	}

	public  void setId(int num) {
		//synchronized (this) {
			this.num = num;
		//}
	}
	
	public void add(){
		synchronized (this) {
			num++;
		}
	}

}
