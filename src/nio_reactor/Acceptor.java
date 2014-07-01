package nio_reactor;

import java.io.IOException;
import java.nio.channels.*;

/**
 * Title:Acceptor.java
 * 
 * Description:Acceptor.java
 * 
 * Copyright: Copyright (c) 2014-7-1
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class Acceptor implements Runnable{  
    private Reactor reactor;  
    public Acceptor(Reactor reactor){  
        this.reactor=reactor;  
    }  
    @Override  
    public void run() {  
    	System.out.println("--this is Acceptor run()--");
        try {  
            SocketChannel socketChannel=reactor.serverSocketChannel.accept();  
            if(socketChannel!=null)//调用Handler来处理channel  
                new SocketReadHandler(reactor.selector, socketChannel);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  
