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
public class Bar1 extends Bar {
	// private static volatile String status;

	public void execute() {
		synchronized (this) {
			if (status1.equals("C") || status1.equals("")) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println("--A--");
				status1 = "A";
			}
		}
	}
}
