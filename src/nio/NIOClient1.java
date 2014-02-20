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
	// ͨ��������
	private Selector selector;
	
	private int bufferSize = 1024;
	
	private String message = "==Hello Server,It is from client,time:";

	/**
	 * ���һ��Socketͨ�������Ը�ͨ����һЩ��ʼ���Ĺ���
	 * 
	 * @param ip
	 *            ���ӵķ�������ip
	 * @param port
	 *            ���ӵķ������Ķ˿ں�
	 * @throws IOException
	 */
	public void initClient(String ip, int port) throws IOException {
		// ���һ��Socketͨ��
		SocketChannel channel = SocketChannel.open();
		// ����ͨ��Ϊ������
		channel.configureBlocking(false);
		// ���һ��ͨ��������
		this.selector = Selector.open();

		// �ͻ������ӷ�����,��ʵ����ִ�в�û��ʵ�����ӣ���Ҫ��listen���������е�
		// ��channel.finishConnect();�����������
		channel.connect(new InetSocketAddress(ip, port));
		// ��ͨ���������͸�ͨ���󶨣���Ϊ��ͨ��ע��SelectionKey.OP_CONNECT�¼���
		channel.register(selector, SelectionKey.OP_CONNECT);
	}

	/**
	 * ������ѯ�ķ�ʽ����selector���Ƿ�����Ҫ������¼�������У�����д���
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void listen() throws IOException {
		// ��ѯ����selector
		while (true) {
			selector.select();
			// ���selector��ѡ�е���ĵ�����
			Iterator ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				// ɾ����ѡ��key,�Է��ظ�����
				ite.remove();
				// �����¼�����
				if (key.isConnectable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					// ����������ӣ����������
					if (channel.isConnectionPending()) {
						channel.finishConnect();
					}
					// ���óɷ�����
					channel.configureBlocking(false);
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					// ��������Ը�����˷�����ϢŶ
					String fullMessage = (message + new Date()) + "--ss-->";
					channel.write(ByteBuffer.wrap(fullMessage.getBytes()));
					System.out.println("CC�ͻ��˷���00��Ϣ��" + fullMessage);
					// �ںͷ�������ӳɹ�֮��Ϊ�˿��Խ��յ�����˵���Ϣ����Ҫ��ͨ�����ö���Ȩ�ޡ�
					channel.register(this.selector, SelectionKey.OP_READ);

					// ����˿ɶ����¼�
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
	 * �����ȡ����˷�������Ϣ ���¼�
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void read(SelectionKey key) throws IOException {
		// �ͷ���˵�read����һ��
		 // �������ɶ�ȡ��Ϣ:�õ��¼�������Socketͨ��  
        SocketChannel channel = (SocketChannel) key.channel();  
        // ������ȡ�Ļ�����  
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);  
        channel.read(buffer);  
        byte[] data = buffer.array();  
        String msg = new String(data).trim();  
        System.out.println("CC�ͻ����յ�11��Ϣ��" + msg + "-%%%%%end%%%%%-");  
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());  
        channel.write(outBuffer);// ����Ϣ���͸��ͻ���  
	}

	/**
	 * �����ͻ��˲���
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		NIOClient1 client = new NIOClient1();
		client.initClient("localhost", 8888);
		client.listen();
	}
}
