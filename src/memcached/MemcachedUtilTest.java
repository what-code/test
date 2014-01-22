package memcached;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Title:MemcachedUtilTest.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-5-31
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class MemcachedUtilTest {

	public static void main(String[] args) {
	
		int len = 200;
		ExecutorService pools = Executors.newFixedThreadPool(len);
		
		List nlist = (List)MemcachedUtil.get("data");
		for(int i = 0;i < nlist.size();i++){
			nlist.get(i);
			System.out.println("nlist--->" + nlist.get(i));
		}
		
	}
}
