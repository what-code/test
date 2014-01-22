package jms.metaq;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.MessageListener;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendResult;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;
public class AsyncConsumer {

	public static void main(String[] args) throws Exception {
        final MetaClientConfig metaClientConfig = new MetaClientConfig();
        final ZKConfig zkConfig = new ZKConfig();
        //设置zookeeper地址
        zkConfig.zkConnect = "10.10.99.167:2181";
        metaClientConfig.setZkConfig(zkConfig);
        // New session factory,强烈建议使用单例
        MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(metaClientConfig);
        // subscribed topic
        final String topic = "data_from_b5mpluginspider";
        // consumer group
        final String group = "group_test001";
        // create consumer,强烈建议使用单例
        final ConsumerConfig cc = new ConsumerConfig(group);
        cc.setConsumeFromMaxOffset();
        MessageConsumer consumer = sessionFactory.createConsumer(cc);
        final AtomicInteger counter = new AtomicInteger();
        // subscribe topic
        consumer.subscribe(topic, 1024 * 1024, new MessageListener() {
            public void recieveMessages(Message message) {
            	//String msg = new String(message.getData());
            	counter.incrementAndGet();
            	//System.out.println(cc.getOffset() +"--Receive message:" + msg + "---" + message.getId() +"-->" + message.getPartition());
            }

            public Executor getExecutor() {
                // Thread pool to process messages,maybe null.
                return null;
            }
        });
        // complete subscribe
        consumer.completeSubscribe();
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("--->" + counter.get());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
