package nio_reactor;

import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.io.*;

/**
 * Title:Handler.java
 * 
 * Description:Handler.java
 * 
 * Copyright: Copyright (c) 2014-6-30
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class SocketReadHandler implements Runnable{  
    private SocketChannel socketChannel;  
    public SocketReadHandler(Selector selector,SocketChannel socketChannel) throws IOException{  
        this.socketChannel=socketChannel;  
        socketChannel.configureBlocking(false);  
          
        SelectionKey selectionKey=socketChannel.register(selector, 0);  
          
        //将SelectionKey绑定为本Handler 下一步有事件触发时，将调用本类的run方法。    
        //参看dispatch(SelectionKey key)    
        selectionKey.attach(this);  
          
        //同时将SelectionKey标记为可读，以便读取。    
        selectionKey.interestOps(SelectionKey.OP_READ);    
        selector.wakeup();  
        System.out.println("--this is SocketReadHandler init()--");
    }  
      
    /** 
     * 处理读取数据 
     */  
    @Override  
    public void run() {
    	System.out.println("--this is SocketReadHandler run()--");
        ByteBuffer inputBuffer=ByteBuffer.allocate(1024);  
        inputBuffer.clear();  
        try {  
        	int readBytes =  socketChannel.read(inputBuffer); 
        	if (readBytes > 0) {
        		inputBuffer.flip();
				byte[] bytes = new byte[inputBuffer.remaining()];
				inputBuffer.get(bytes);
				String body = new String(bytes, "UTF-8");
				System.out.println("---->The time server receive order(server) : "
						+ body);
				String currentTime = "QUERY_TIME_ORDER_" + System.currentTimeMillis() + "(msg from server)";
				doWrite(socketChannel, currentTime);
			} else if (readBytes < 0) {
				// 对端链路关闭
				//key.cancel();
				socketChannel.close();
			}
            //激活线程池 处理这些request  
            //requestHandle(new Request(socket,btt));
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    
    private void doWrite(SocketChannel channel, String response)
			throws IOException {
		if (response != null && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);
		}
	}
}  
