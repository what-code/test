package nio;

/**
 * Title:TimeClient.java
 * 
 * Description:TimeClient.java
 * 
 * Copyright: Copyright (c) 2014-5-21
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TimeClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int port = 8089;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// 采用默认值
			}
		}
		//for(int i = 0;i < 5;i++){
			new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001")
			.start();
		//}
		
	}
}
