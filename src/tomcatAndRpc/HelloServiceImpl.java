package tomcatAndRpc;

/**
 * Title:HelloServiceImpl.java
 * 
 * Description:HelloServiceImpl.java
 * 
 * Copyright: Copyright (c) 2014-9-9
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class HelloServiceImpl implements HelloService {

	public String hello(String name) {
		return "Hello " + name;
	}

}
