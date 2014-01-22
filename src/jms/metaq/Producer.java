package jms.metaq;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendResult;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;

public class Producer {
	public static void main(String[] args) throws Exception {
        final MetaClientConfig metaClientConfig = new MetaClientConfig();
        final ZKConfig zkConfig = new ZKConfig();
        //设置zookeeper地址
        zkConfig.zkConnect = "10.10.99.167:2181";
        metaClientConfig.setZkConfig(zkConfig);
        // New session factory,强烈建议使用单例
        MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(metaClientConfig);
        // create producer,强烈建议使用单例
        MessageProducer producer = sessionFactory.createProducer();
        // publish topic
        final String topic = "test0";
        producer.publish(topic);
        long mis = System.currentTimeMillis();
        
        String str1 = "new0dd,nn0000,100010";
    	Message msg1 = new Message(topic,str1.getBytes());
    	//producer.sendMessage(msg1);
        
        //信息格式oid,nid,chn
        /*for(int i = 0;i < 30;i++){
        	String str = "gsj" + i + ",new" + i + "dd,100010";
        	Message msg = new Message(topic,str.getBytes());
        	SendResult result = producer.sendMessage(msg);
        	System.out.println("send result0-->" + result.isSuccess() + "----" + i + "----" + msg.getId());
        }*/
        
        /*for(int i = 0;i < 51;i++){
        	String str = "t" + i + ",new" + i + "dd,100020";
        	Message msg = new Message(topic,str.getBytes());
        	SendResult result = producer.sendMessage(msg);
        	System.out.println("send result-->" + result.isSuccess() + "---" + i + "----" + msg.getId());
        }*/
        
       for(int i = 0;i < 30;i++){
        	String str2 = "gsj02___668866______" + i + ",new" + i + "dd,100010";
        	Message msg2 = new Message(topic,str2.getBytes());
        	producer.sendMessage(msg2);
        }
        System.out.println("send msg cost-->" + (System.currentTimeMillis() - mis));
    }
}
