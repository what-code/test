package zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.*;

/**
 * Title:TestZookeeper.java
 * 
 * Description:TestZookeeper.java
 * 
 * Copyright: Copyright (c) 2014-7-22
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class TestZookeeper {
	public ZooKeeper zk;
	
	public static void createNode(ZooKeeper zk,String path, String data) {
		try {
			Stat stat = zk.exists(path, true);
			if(stat == null){
				String path0 = zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE,
						CreateMode.EPHEMERAL_SEQUENTIAL);
				System.out.println("----create--node is null---->" + path0);
			}else{
				System.out.println("----create--node is not null----");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	public static void isExists(ZooKeeper zk,String path){
		try {
			Stat stat = zk.exists(path, true);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void setData(ZooKeeper zk,String path,byte[] data){
		try {
			zk.setData(path, data, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void queryNode(ZooKeeper zk,String path) {
		try {
			System.out.println("----------start query-----------");
			List<String> result = zk.getChildren(path, true);
			/*for(String str : result){
				System.out.println(path + "--children is -->" + str);
			}*/
			
			System.out.println(path + "--node_data-->" + new String(zk.getData(path, true, null)) + "----result--->" + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("--zk query error-->");
		} finally {
			System.out.println("----------finish query-----------");
		}
	}

	public static void deleteNode(ZooKeeper zk,String path) {
		try {
			Stat stat = zk.exists(path, true);
			if(stat != null){
				zk.delete(path, -1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("--zk delete error-->");
		} finally {
			System.out.println("----------finish delete-----------");
		}
	}
	
	public void test(){
		//watcher,watcher要达到的效果,client c1 创建/删除/修改 /root/r1,会通知到 client c2,c3...,cn(这些client 注册了/root/r1的watcher);
		//其中client c1 到 cn的定义为每个client对应一个Zookeeper实例
		Watcher watcher = new Watcher() {
			public void process(WatchedEvent event) {
				System.out.println("Receive watched event：" + event);
				if (KeeperState.SyncConnected == event.getState()) {
					if (EventType.None == event.getType() && null == event.getPath()) {
						System.out.println("----------event type is null-----------");
					} else if (event.getType() == EventType.NodeChildrenChanged) {
						try {
							//重新注册watcher————————重点(在于watcher被触发以后重新注册watcher)
							//NodeChildrenChanged
							List<String> list = zk.getChildren(event.getPath(), true);
							System.out.println(event.getPath() + "---children--->" + list);
						} catch (Exception e) {
						
						}
					}else if(event.getType() == EventType.NodeDataChanged){
						//EventType.NodeDataChanged
						try {
							byte[] bytes = zk.getData(event.getPath(), true, null);
							System.out.println(event.getPath() + "---data--->" + new String(bytes));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else if(event.getType() == EventType.NodeCreated || event.getType() == EventType.NodeDeleted){
						try {
							//EventType.NodeCreated
							zk.exists(event.getPath(), true);
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
				}
			}
		};
		
		String root = "/dl_root";
		
		try {
			zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 500000,watcher);
		
			//createNode(zk,root + "/n","this is root_test!!");
			//createNode(zk,root + "/n2","this is root/n2 data");
			//setData(zk,root + "/n1","this is root/n1 data new".getBytes());
			//createNode(zk,root + "/n4","this is root_test_n1!!");
			//queryNode(zk,root);
			
			//isExists(zk,"/root_test/n0000000020");
			
			deleteNode(zk,root);
			//deleteNode(zk,"/get_children_test/c1");
			//deleteNode(zk,"/get_children_test/c2");
			//deleteNode(zk,"/get_children_test/c3");
			//deleteNode(zk,"/get_children_test");
			
			Thread.sleep(10000000);
			// 关闭session
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestZookeeper tz = new TestZookeeper();
		tz.test();
	}

}
