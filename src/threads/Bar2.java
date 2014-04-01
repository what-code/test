package threads;

/**
 * Title:Bar1.java
 * 
 * Description:Bar1.java
 * 
 * Copyright: Copyright (c) 2014-3-31
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class Bar2 extends Bar {

	public void execute() {
		synchronized (this) {
			if (status1.equals("A")) {
				try {
					Thread.sleep(900);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("--B--");
				status1 = "B";
			}
		}
	}
}
