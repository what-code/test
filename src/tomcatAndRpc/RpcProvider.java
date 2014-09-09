package tomcatAndRpc;
/**
 * Title:RpcProvider.java
 * 
 * Description:RpcProvider.java
 * 
 * Copyright: Copyright (c) 2014-9-9
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class RpcProvider {

	public static void main(String[] args) throws Exception {  
        HelloService service = new HelloServiceImpl();  
        RpcFramework.export(service, 1234);  
    } 

}
