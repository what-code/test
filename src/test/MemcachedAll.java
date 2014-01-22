package test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.CyclicBarrier;

//import java.util.concurrent.Phaser;

/**
 * Title:MemcachedClientTest.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-7-16
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class MemcachedAll {

	public MemcachedClient test() {
		InetSocketAddress in1 = new InetSocketAddress("172.16.11.208", 11211);
		List list = new ArrayList();
		list.add(in1);
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(list);
		// 使用二进制文件
		builder.setCommandFactory(new BinaryCommandFactory());
		MemcachedClient memcachedClient = null;

		try {
			memcachedClient = builder.build();
			try {
				//clear all--------------------------------------
				clearAllCache(memcachedClient);
				//memcachedClient.set("v_version",24*60*60*1000 ,"20131206143321");
				//clear all--------------------------------------
				System.out.println("--->" + "---" + memcachedClient.get("v_version"));
				return memcachedClient;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (memcachedClient != null) {
				try {
					memcachedClient.shutdown();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return memcachedClient;
	}

	public void clearAllCache(MemcachedClient memcachedClient){
		try {
			memcachedClient.flushAll();
			System.out.println("--------------->clear All Cache---------------");
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void shutDownClient(MemcachedClient memcachedClient) {
		if (memcachedClient != null) {
			try {
				memcachedClient.shutdown();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void testCas(MemcachedClient memcachedClient) {
		String key = "Test_Key_gsj";
		boolean flag = false;
		try {
			// 1 set
			// memcachedClient.set(key, 100, "0000008888");
			// memcachedClient.set(key, 100, "0000022222");
			// GetsResponse<String> casValue = memcachedClient.gets(key);
			// long testCas = casValue.getCas();
			//
			// //2 set,此时此key对应的版本号已变更
			// memcachedClient.set(key, 100, "0000033333");
			// memcachedClient.set(key, 100, "0000077777");
			// // GetsResponse<String> casValue1 = memcachedClient.gets(key);
			// // long testCas1 = casValue.getCas();
			// // memcachedClient.set(key, 10, "0000011111");
			//
			// //3 cas会失败并返回false
			// flag = memcachedClient.cas(key, 100,"0000066666",testCas);
			// System.out.println("flag--->" + flag + "----" +
			// memcachedClient.get(key) + "----" + testCas);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.shutDownClient(memcachedClient);
		}
	}

	public static void main(String[] args) {
		MemcachedAll mc = new MemcachedAll();
		mc.testCas(mc.test());
		//String pageNo = "速度快计划12qweqwe";
		//pageNo = pageNo.replaceAll("\\D", "");
		//System.out.println("--pno--->" + pageNo);
	}
}
