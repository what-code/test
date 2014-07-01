package nio_reactor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.*;

/**
 * Title:Reactor.java
 * 
 * Description:Reactor.java
 * 
 * Copyright: Copyright (c) 2014-6-30
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class Reactor implements Runnable {

	public final Selector selector;
	public final ServerSocketChannel serverSocketChannel;
	int port = 8899;

	public Reactor() throws IOException {
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
		serverSocketChannel.socket().bind(inetSocketAddress);
		serverSocketChannel.configureBlocking(false);

		// 向selector注册该channel
		SelectionKey selectionKey = serverSocketChannel.register(selector,
				SelectionKey.OP_ACCEPT);

		// 利用selectionKey的attache功能绑定Acceptor 如果有事情，触发Acceptor
		selectionKey.attach(new Acceptor(this));
		System.out.println("--this is Reactor init()--");
	}

	public void run() {
		System.out.println("--this is Reactor run(0)--");
		try {
			while (!Thread.interrupted()) {
				selector.select();
				System.out.println("--this is Reactor run(1)--");
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				// Selector如果发现channel有OP_ACCEPT或READ事件发生，下列遍历就会进行。
				while (it.hasNext()) {
					System.out.println("--this is Reactor run(2)--");
					// 来一个事件 第一次触发一个accepter线程
					// 以后触发SocketReadHandler
					SelectionKey selectionKey = it.next();
					dispatch(selectionKey);
					selectionKeys.clear();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void dispatch(SelectionKey key) {
		Runnable r = (Runnable) (key.attachment());
		if (r != null) {
			System.out.println("--this is Reactor dispatch(0)--");
			r.run();
		}
	}

	public static void main(String[] args) {
		try {
			Runnable rac = new Reactor();
			rac.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
