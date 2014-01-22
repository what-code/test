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

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.producer.MessageProducer;


/**
 * �����߱�������ļ�����
 * 
 * @author boyan(boyan@taobao.com)
 * @date 2011-8-26
 * 
 */
public class TransactionProducer {
    public static void main(final String[] args) throws Exception {
        // New session factory,ǿ�ҽ���ʹ�õ���
        final MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(initMetaConfig());
        // create producer,ǿ�ҽ���ʹ�õ���
        final MessageProducer producer = sessionFactory.createProducer();
        // publish topic
        final String topic = "meta-test";
        producer.publish(topic);

        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while ((line = readLine(reader)) != null) {
            try {
                // ��ʼ����
                producer.beginTransaction();
                // �������ڷ���������Ϣ
                if (!producer.sendMessage(new Message(topic, line.getBytes())).isSuccess()) {
                    // ����ʧ�ܣ������ع�
                    producer.rollback();
                    continue;
                }
                if (!producer.sendMessage(new Message(topic, line.getBytes())).isSuccess()) {
                    producer.rollback();
                    continue;
                }
                // �ύ
                producer.commit();

            }
            catch (final Exception e) {
                producer.rollback();
            }
        }
    }


    private static String readLine(final BufferedReader reader) throws IOException {
        System.out.println("Type message to send:");
        return reader.readLine();
    }
}