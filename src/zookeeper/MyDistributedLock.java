package zookeeper;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * Title:MyDistributedLock.java
 * 
 * Description:MyDistributedLock.java
 * 
 * Copyright: Copyright (c) 2014-8-21
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class MyDistributedLock implements Watcher{
	private static final byte[] data = {0};

	private String root;

	private String prefix;

	private String zkServer;

	private ZooKeeper zookeeper = null;
	
	private Integer mutex = 0;
	
	//当前客户端的path
	private volatile String curPath = null;
	
	//当前占有锁的path
	private volatile String lowerPath = null;
	
	public String getRoot() {
		return this.root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public ZooKeeper getZookeeper() {
		return zookeeper;
	}

	public String getCurPath() {
		return curPath;
	}

	public String getLowerPath() {
		return lowerPath;
	}

	public void setZookeeper(ZooKeeper zookeeper) {
		this.zookeeper = zookeeper;
	}

	public void setCurPath(String curPath) {
		this.curPath = curPath;
	}

	public void setLowerPath(String lowerPath) {
		this.lowerPath = lowerPath;
	}

	public MyDistributedLock() {
		this.root = "/dl_root";
		this.prefix = "/lock_";
		this.zkServer = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
		init();
	}

	public MyDistributedLock(String root,String prefix,String zkServer) {
		this.root = root;
		this.prefix = prefix;
		this.zkServer = zkServer;
		init();
	}
	
	private void init(){
		/*Watcher watcher = new Watcher() {
			public void process(WatchedEvent event) {
				System.out.println("Receive watched event：" + event);
			}
		};*/

		try {
			zookeeper = new ZooKeeper(this.zkServer, 500000, this);
			try {
				//create root
				Stat stat = zookeeper.exists(root, false);
				if(stat == null){
					zookeeper.create(root, data, Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * create node
	 * 
	 * @param zk
	 * @param path
	 */
	private String createNode() {
		try {
			String finalPath = root + prefix;
			Stat stat = zookeeper.exists(finalPath, true);
			if (stat == null) {
				String path0 = zookeeper.create(finalPath, data, Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
				System.out.println("----create--node is null---->" + path0);
				return path0;
			} else {
				System.out.println("----create--node is not null----");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}
	
	/**
	 * 
	 * @param curNode
	 * @param allNode
	 * @return
	 */
	private boolean isMinNode(String curNode,List<String> allNode){
		int currId = Integer.parseInt(curNode.substring(curNode.lastIndexOf("/") + 1).split("_")[1]);
		if(allNode.size() == 1){
			return true;
		}
		for(String str : allNode){
			int tempId = Integer.parseInt(str.split("_")[1]);
			if(currId > tempId){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取比当前节点小的最近的一个节点
	 * @param allNode
	 * @return
	 */
	private String getLowerNode(String curNode,List<String> allNode){
		Collections.sort(allNode, new Comparator<String>(){
			public int compare(String o1, String o2) {
				int temp0 = Integer.parseInt(o1.substring(o1.lastIndexOf("/") + 1).split("_")[1]);
				int temp1 = Integer.parseInt(o2.substring(o2.lastIndexOf("/") + 1).split("_")[1]);
				return temp0 - temp1;
			}
		});
		for(int i = 0;i < allNode.size();i++){
			if(curNode.equals(root + "/" + allNode.get(i))){
				if(i == 0){
					return null;
				}
				return root + "/" + allNode.get(i-1);
			}
		}
		return null;
	}

	/**
	 * 分布式 加锁
	 */
	public void lock() {
		curPath = createNode();
		if(curPath != null){
			try {
				List<String> list = zookeeper.getChildren(root, false);
				lowerPath = getLowerNode(curPath,list);
				if(isMinNode(curPath,list)){
					//TODO getLock and do nothing
				}else{
					//TODO not getLock and blocked
					waitForLock(lowerPath);
				}
				System.out.println("--------------get lock--------------------");
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

	/**
	 * 分布式 释放锁
	 * @throws KeeperException 
	 * @throws InterruptedException 
	 */
	public void unlock(){
		if(curPath != null){
			Stat stat;
			try {
				stat = zookeeper.exists(curPath, false);
				if(stat != null){
					zookeeper.delete(curPath, -1);
					System.out.println("--------------release lock--------------------");
				}
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * watcher 的回调
	 */
	@Override
	public void process(WatchedEvent event) {
		if (event.getType() == Event.EventType.NodeDeleted) {
			System.out.println("-----get watcher notify------");
			//某个节点(/dl_root/lock_i)被删除之后,通过watcher唤醒下一个比自己大的节点
			synchronized (mutex) {
				System.out.println("-----client---->"+ curPath + "-------is notify by-->" + event.getPath());
				mutex.notify();
			}
		}
	}
	
	/**
	 * 等待获取锁
	 * @param lowerNode
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	private void waitForLock(String lowerNode) throws KeeperException, InterruptedException{
		if(lowerNode != null){
			//重点——注册wather(比自己小的离自己最近的节点),唤醒自己
			Stat stat = zookeeper.exists(lowerNode, true);
			if (stat != null) {
				synchronized (mutex) {
					System.out.println("-----client---->"+ curPath + " is blocked and watch--->" + lowerNode);
					mutex.wait();
				}
			} else {
				lock();
			}
		}
	}
	
	public static void main(String[] args){
		String curNode = "/dl_root/lock_9";
		System.out.println(curNode.substring(curNode.lastIndexOf("/") + 1).split("_")[1]);
	}
}
