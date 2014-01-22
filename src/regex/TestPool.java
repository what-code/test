package regex;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestPool {
	private static ThreadPoolExecutor executor;
	private static int cpus;

	public TestPool() {
		cpus = Runtime.getRuntime().availableProcessors();
		this.executor = new ThreadPoolExecutor(cpus * 2, cpus * 3, 1L,
				TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(1000),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public static void main(String[] args) {
		final Counter count = new Counter();
		TestPool tp = new TestPool();
		System.out.println("o1--->" + Thread.activeCount() + "---" + cpus);
		for (int i = 0; i < 100; i++) {
			executor.execute(new Runnable() {
				public void run() {
					for (int j = 0; j < 200; j++) {
						count.add();
						/*try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}*/
						System.out.println("---->" + count.getId());
					}
				}
			});
		}
		System.out.println("o2--->" + Thread.activeCount() + "-t-" + (new Date().getTime() - 1368358650503l)/(1000*60*60*24));
	}
}
