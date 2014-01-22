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
package  example;

import static  example.Help.initMetaConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.extension.OrderedMessageSessionFactory;
import com.taobao.metamorphosis.client.extension.OrderedMetaMessageSessionFactory;
import com.taobao.metamorphosis.client.extension.producer.OrderedMessagePartitionSelector;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendResult;
import com.taobao.metamorphosis.cluster.Partition;


/**
 * �ϸ�˳������Ϣ
 * 
 * @author �޻�
 * @since 2012-2-22 ����4:28:11
 */

public class OrderedProducer {
    public static void main(final String[] args) throws Exception {

        final MetaClientConfig metaClientConfig = initMetaConfig();

        // ���÷���ֲ����,Ҫ�����˶�Ӧ
        final Properties partitionsInfo = new Properties();
        partitionsInfo.put("topic.num.exampleTopic1", "0:4;1:4");
        metaClientConfig.setPartitionsInfo(partitionsInfo);

        // New session factory,ǿ�ҽ���ʹ�õ���
        final OrderedMessageSessionFactory sessionFactory = new OrderedMetaMessageSessionFactory(metaClientConfig);

        // create producer,ǿ�ҽ���ʹ�õ���
        final MessageProducer producer = sessionFactory.createProducer(new CustomPartitionSelector());

        // publish topic
        final String topic = "meta-test";
        producer.publish(topic);

        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while ((line = readLine(reader)) != null) {
            // send message
            final SendResult sendResult = producer.sendMessage(new Message(topic, line.getBytes()));
            // check result
            if (!sendResult.isSuccess()) {
                System.err.println("Send message failed,error message:" + sendResult.getErrorMessage());
            }
            else {
                System.out.println("Send message successfully,sent to " + sendResult.getPartition());
            }
        }
    }


    private static String readLine(final BufferedReader reader) throws IOException {
        System.out.println("Type a message to send:");
        return reader.readLine();
    }

    static class CustomPartitionSelector extends OrderedMessagePartitionSelector {

        @Override
        protected Partition choosePartition(final String topic, final List<Partition> partitions, final Message message) {
            // ���һ���Ĺ������Ҫ����ľֲ���Ϣ·�ɵ�ͬһ������
            final int hashCode = new String(message.getData()).hashCode();
            final int partitionNo = hashCode % partitions.size();
            return partitions.get(partitionNo);
        }
    }
}