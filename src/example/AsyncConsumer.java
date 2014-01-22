/*
 * (C) 2007-2012 Alibaba Group Holding Limited.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Authors:
 *   wuhua <wq163@163.com> , boyan <killme2008@gmail.com>
 */
package example;


import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.MessageListener;


/**
 * 
 * @author boyan
 * @Date 2011-5-17
 * 
 */
public class AsyncConsumer {
    public static void main(final String[] args) throws Exception {
        // New session factory
        final MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(Help.initMetaConfig());

        // subscribed topic
        final String topic = "data_from_b5mpluginspider";
        // consumer group
        final String group = "meta-example-001";
        // create consumer
        ConsumerConfig consumerConfig = new ConsumerConfig(group);
        //
        consumerConfig.setMaxDelayFetchTimeInMills(100);
        final ConsumerConfig cc = new ConsumerConfig(group);
        final MessageConsumer consumer = sessionFactory.createConsumer(cc);
        final AtomicInteger counter = new AtomicInteger(0);
        // subscribe topic
        consumer.subscribe(topic, 1024 * 1024, new MessageListener() {
            @Override
            public void recieveMessages(final Message message) {
                System.out.println("Receive message-->" + new String(message.getData()) + "-----" + cc.getOffset());
                counter.incrementAndGet();
            }

            @Override
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