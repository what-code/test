package zookeeper;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

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
	
	private AtomicLong state = new AtomicLong(0);
	
	//当前客户端的path
	private volatile String curPath = null;
	
	//比当前客户端path小的最近的node
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
	
	/**
	 * 初始化root
	 */
	private void init(){
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
				return finalPath;
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
		if(this.curPath == null){
			curPath = createNode();
		}
		
		//处理重入锁的情况
		if(isHeldByCurrentClient()){
			//重入计数
			state.incrementAndGet();
			return;
		}
		
		if(!tryLock()){
			try {
				waitForLock(lowerPath);
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/*if(curPath != null){
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
		}*/
	}
	
	protected boolean tryLock(){
		if(curPath != null){
			try {
				List<String> list = zookeeper.getChildren(root, false);
				lowerPath = getLowerNode(curPath,list);
				if(isMinNode(curPath,list)){
					//重入计数
					state.incrementAndGet();
					System.out.println("--------------get lock--------------------" + state.get());
					return true;
				}else{
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return false;
	}
	
	/**
	 * 当前客户端是否是锁的持有者
	 * @return
	 */
	protected boolean isHeldByCurrentClient(){
		try {
			List<String> list = zookeeper.getChildren(root, false);
			if(curPath != null && isMinNode(curPath,list)){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
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
				System.out.println("--------------release lock--------------------" + state.get());
				if(tryUnLock()){
					stat = zookeeper.exists(curPath, false);
					if(stat != null){
						System.out.println("--------------release and delete lock --------------------");
						zookeeper.delete(curPath, -1);
					}
				}
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * tryUnLock
	 * @return
	 */
	private boolean tryUnLock(){
		return state.compareAndSet(0, state.decrementAndGet());
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
				System.out.println("-----client---->"+ curPath + "-------is notify by-->" + event.getPath() + "--->" + state.get());
				//重入计数
				state.incrementAndGet();
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
			//重点——注册wather(比自己小的离自己最近的节点),在前一个比自己小的节点释放锁之后唤醒自己
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
}
