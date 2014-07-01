package nio_reactor;

import java.io.IOException;

/**
 * Title:Test.java
 * 
 * Description:Test.java
 * 
 * Copyright: Copyright (c) 2014-6-30
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 Reactor reactor;
		try {
			reactor = new Reactor();
			new Thread(reactor).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
