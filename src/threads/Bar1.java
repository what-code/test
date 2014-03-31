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
		if (status1.equals("C") || status1.equals("")) {
			//type1
			/*try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			synchronized (this) {
				System.out.println("--A--");
				status1 = "A";
			}
			
			//type2
		}
	}
}
