package threads;
/**
 * Title:BarThread.java
 * 
 * Description:BarThread.java
 * 
 * Copyright: Copyright (c) 2014-3-31
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class BarThread extends Thread {
	private Bar bar;
	
	public BarThread(Bar bar){
		this.bar = bar;
	}
	
	public void run(){
		while(true){
			bar.execute();
		}
	}
}
