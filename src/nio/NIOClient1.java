package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-1-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author shengjie.guo
 * 
 * @version 1.0
 */
public class NIOClient1 {
	// 通道管理器
	private Selector selector;
	
	private int bufferSize = 1024;
	
	private String message = "==Hello Server,It is from client,time:";

	/**
	 * 获得一个Socket通道，并对该通道做一些初始化的工作
	 * 
	 * @param ip
	 *            连接的服务器的ip
	 * @param port
	 *            连接的服务器的端口号
	 * @throws IOException
	 */
	public void initClient(String ip, int port) throws IOException {
		// 获得一个Socket通道
		SocketChannel channel = SocketChannel.open();
		// 设置通道为非阻塞
		channel.configureBlocking(false);
		// 获得一个通道管理器
		this.selector = Selector.open();

		// 客户端连接服务器,其实方法执行并没有实现连接，需要在listen（）方法中调
		// 用channel.finishConnect();才能完成连接
		channel.connect(new InetSocketAddress(ip, port));
		// 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。
		channel.register(selector, SelectionKey.OP_CONNECT);
	}

	/**
	 * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void listen() throws IOException {
		// 轮询访问selector
		while (true) {
			selector.select();
			// 获得selector中选中的项的迭代器
			Iterator ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				// 删除已选的key,以防重复处理
				ite.remove();
				// 连接事件发生
				if (key.isConnectable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					// 如果正在连接，则完成连接
					if (channel.isConnectionPending()) {
						channel.finishConnect();
					}
					// 设置成非阻塞
					channel.configureBlocking(false);
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					// 在这里可以给服务端发送信息哦
					String fullMessage = (message + new Date()) + "--ss-->";
					channel.write(ByteBuffer.wrap(fullMessage.getBytes()));
					System.out.println("CC客户端发送00信息：" + fullMessage);
					// 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
					channel.register(this.selector, SelectionKey.OP_READ);

					// 获得了可读的事件
				} else if (key.isReadable()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					read(key);
				}
			}
		}
	}

	/**
	 * 处理读取服务端发来的信息 的事件
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void read(SelectionKey key) throws IOException {
		// 和服务端的read方法一样
		 // 服务器可读取消息:得到事件发生的Socket通道  
        SocketChannel channel = (SocketChannel) key.channel();  
        // 创建读取的缓冲区  
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);  
        channel.read(buffer);  
        byte[] data = buffer.array();  
        String msg = new String(data).trim();  
        System.out.println("CC客户端收到11信息：" + msg + "-%%%%%end%%%%%-");  
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());  
        channel.write(outBuffer);// 将消息回送给客户端  
	}

	/**
	 * 启动客户端测试
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		NIOClient1 client = new NIOClient1();
		client.initClient("localhost", 8888);
		client.listen();
	}
}
