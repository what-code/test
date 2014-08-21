package zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * Title:ZooKeeper_GetChildren_API_Sync_Usage.java
 * 
 * Description:ZooKeeper_GetChildren_API_Sync_Usage.java
 * 
 * Copyright: Copyright (c) 2014-8-20
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class ZooKeeper_GetChildren_API_Sync_Usage implements Watcher {

	private CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static CountDownLatch _semaphore = new CountDownLatch(2);
	private ZooKeeper zk;

	ZooKeeper createSession(String connectString, int sessionTimeout,
			Watcher watcher) throws IOException {
		ZooKeeper zookeeper = new ZooKeeper(connectString, sessionTimeout,
				watcher);
		try {
			connectedSemaphore.await();
		} catch (InterruptedException e) {
		}
		return zookeeper;
	}

	/** create path by sync */
	void createPath_sync(String path, String data, CreateMode createMode)
			throws IOException, KeeperException, InterruptedException {
		if (zk == null) {
			zk = this.createSession(
					"127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 5000, this);
		}
		zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, createMode);
	}
	
	void deletePath_sync(String path) throws IOException, KeeperException, InterruptedException{
		if (zk == null) {
			zk = this.createSession(
					"127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 5000, this);
		}
		zk.delete(path, -1);
	}

	/** Get children znodes of path and set watches */
	List getChildren(String path) throws KeeperException, InterruptedException,
			IOException {
		System.out.println("===Start to get children znodes.===");
		if (zk == null) {
			zk = this.createSession(
					"127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 5000, this);
		}
	
		return zk.getChildren(path, true);
	}

	/**
	 * Process when receive watched event
	 */
	public void process(WatchedEvent event) {
		System.out.println("Receive watched event：" + event);
		if (KeeperState.SyncConnected == event.getState()) {
			if (EventType.None == event.getType() && null == event.getPath()) {
				connectedSemaphore.countDown();
			} else if (event.getType() == EventType.NodeChildrenChanged) {
				// children list changed
				try {
					System.out.println(this.getChildren(event.getPath()));
					//_semaphore.countDown();
				} catch (Exception e) {
				}
			}
		}
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		ZooKeeper_GetChildren_API_Sync_Usage sample = new ZooKeeper_GetChildren_API_Sync_Usage();
		String path = "/get_children_test";
		try {
			sample.createPath_sync(path, "", CreateMode.PERSISTENT);
			sample.createPath_sync(path + "/c1", "", CreateMode.PERSISTENT);

			List childrenList = sample.getChildren(path);
			System.out.println(childrenList);

			// Add a new child znode to test watches event notify.
			sample.createPath_sync(path + "/c2", "", CreateMode.PERSISTENT);
			
			sample.createPath_sync(path + "/c3", "", CreateMode.PERSISTENT);
			//_semaphore.await();
			
			Thread.sleep(1000000);
		} catch (Exception e) {
			System.err.println("error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
