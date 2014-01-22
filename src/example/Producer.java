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

import static example.Help.initMetaConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendResult;
import com.taobao.metamorphosis.exception.MetaClientException;

/**
 * 
 * @author boyan
 * @Date 2011-5-17
 * 
 */
public class Producer {
	private static final ExecutorService pool = Executors
			.newFixedThreadPool(10);
	private static final Executor opool = Executors.newFixedThreadPool(10);
	final static AtomicInteger counter = new AtomicInteger(0);

	public static void main(final String[] args) throws Exception {
		// New session factory
		final MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(
				initMetaConfig());
		// create producer
		final MessageProducer producer = sessionFactory.createProducer();
		// publish topic
		final String topic = "test0";
		producer.publish(topic);

		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String line = null;

		startPool(producer);
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(2000);
						System.out.println("pro--->" + counter.get());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public static void startPool(final MessageProducer producer) {
		System.out.println("server started!!!");
		// while (true) {
		Runnable task = new Runnable() {
			public void run() {
				try {
					for (int i = 0; i < 150; i++) {
						Message mes = new Message("test0",
								(new String("pool-->"
										+ System.currentTimeMillis()))
										.getBytes());
						SendResult sr = producer.sendMessage(mes);
						counter.incrementAndGet();
						System.out.println("--->" + mes.getId() + "---"
								+ sr.isSuccess());
					}
				} catch (MetaClientException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		try {
			// pool.execute(task);
			new Thread(task).start();
			new Thread(task).start();
			new Thread(task).start();
			new Thread(task).start();
			new Thread(task).start();
			// System.out.println("---------finish 10 clients----");
		} catch (RejectedExecutionException e) {
			if (!pool.isShutdown()) {
				System.out.println("the pool is full,your task rejected!");
			}
		}

		// }
	}
}