package tomcatAndRpc;

/**
 * Title:RpcConsumer.java
 * 
 * Description:RpcConsumer.java
 * 
 * Copyright: Copyright (c) 2014-9-9
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class RpcConsumer {

	public static void main(String[] args) throws Exception {
		HelloService service = RpcFramework.refer(HelloService.class,
				"127.0.0.1", 1234);
		for (int i = 0; i < 9; i++) {
			String hello = service.hello("World" + i);
			System.out.println(hello);
			Thread.sleep(1000);
		}
	}

}
