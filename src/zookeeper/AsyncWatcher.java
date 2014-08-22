package zookeeper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.taobao.api.internal.stream.NamedThreadFactory;

/**
 * Title:AsyncWatcher.java
 * 
 * Description:AsyncWatcher.java
 * 
 * Copyright: Copyright (c) 2014-8-21
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public abstract class AsyncWatcher implements Watcher {

	private static final int DEFAULT_POOL_SIZE = 30;
	private static final int DEFAULT_ACCEPT_COUNT = 60;

	private static ExecutorService executor = new ThreadPoolExecutor(1,
			DEFAULT_POOL_SIZE, 0L, TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue(DEFAULT_ACCEPT_COUNT),
			new ThreadPoolExecutor.CallerRunsPolicy());

	public void process(final WatchedEvent event) {
		executor.execute(new Runnable() {// 提交异步处理
			@Override
			public void run() {
				asyncProcess(event);
			}
		});

	}

	public abstract void asyncProcess(WatchedEvent event);

}
