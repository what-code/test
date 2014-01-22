package test;

import java.text.SimpleDateFormat;
import java.util.*;

import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.http.processor.MetamorphosisOnJettyProcessor;
import com.taobao.metamorphosis.server.assembly.MetaMorphosisBroker;
import com.taobao.metamorphosis.server.utils.MetaConfig;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class Test extends TimerTask {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static void test(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 5);
		calendar.set(Calendar.SECOND, 0);
		Date time = calendar.getTime();
		System.out.println("---time-01--" + time);
		Date d = new Date();
		if (d.getTime() > time.getTime()) {
			System.out.println("--passed--");
			time = new Date(time.getTime() + 24 * 60 * 60 * 1000);
		}
		System.out.println("---time-02--" + time);
		// new Timer().schedule(new Test(),time, 24*60*60*1000);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		// sms.sendTextMail(mailInfo);// 发送文体格式
		List list = new ArrayList();
		for (int i = 1; i < 28; i++) {
			list.add(i);
		}
		list.remove(0);
		list.add(88);
		list.remove(0);
		list.add(99);
		System.out.println(list);
		Map m = new HashMap();
		m.put("10001", 69);
		m.put("10002", 30);
		Set<String> key = m.keySet();
		Iterator it = key.iterator();
		while (it.hasNext()) {
			String s = (String) it.next();
			int value = (Integer) m.get(s);
			System.out.println(s + ":" + value);
		}
	}
	
	@Override
	public void run() {
		Date curr = new Date();
		long mis = curr.getTime() - 24 * 60 * 60 * 1000;
		Date yesterday = new Date(mis);
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String temp = sd.format(yesterday);
		try {
			// 同步昨天的数据至DB，最好每天凌晨2：00左右做此定时任务
			// initToDb(getDaySum(temp));
			System.out.println("----timer-----" + new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		MetaConfig mc = new MetaConfig();
		mc.setHostName("");
		// mc.setTopics(topics);
		MetaMorphosisBroker mmb = new MetaMorphosisBroker(mc);
		MetamorphosisOnJettyProcessor mjp = new MetamorphosisOnJettyProcessor(
				mmb);
		// mjp.handle("p", "", "", "");
	}
}
