package akka;

import java.util.Random;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import org.apache.log4j.Logger;

/**
 * Title:ExecuteTaskActor.java
 *
 * Description:
 *
 * Copyright: Copyright (c) 2015年3月2日
 *
 * Company: VipShop (Shanghai) Co., Ltd.
 *
 * @author Jack Guo
 *
 * @version 1.0
 */

public class ExecuteTaskActor extends UntypedActor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(ExecuteTaskActor.class);
	ActorRef taskReturnActor = getContext().actorOf(
			Props.create(TaskReturnActor.class));

	private Random rnd = new Random();

	@Override
	public void preStart() throws Exception {
		// 这里一般进行初始化工作
		System.out.println("[ExecuteTaskActor]初始化角色。。。。。。。");
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Task) {
			// 接收到前面发送过来的任务
			Task task = (Task) message;
			System.out.println("[ExecuteTaskActor]将会执行 任务 " + task.getName());
			Result result = handler(task);
			// 这里我们要往统计任务结果的actor发送消息，有2中方式 一种是 前面发送task时候 把统计结果的TaskReturnActor
			// 作为sender 2，方式二 是 在此类中初始化taskReturnActor
			// 第一种很简单 不说了 这里我们第二种
			// 我吧结果全部返回吧 不加result判断了 if(result.isSuccess()){} 我们在后面taskReturn 加吧
			taskReturnActor.tell(result, self());
			System.out.println("[ExecuteTaskActor]执行async任务完毕--------");
		} else if (message instanceof Result) {
			taskReturnActor.tell("getResult", null);
		} else {
			unhandled(message);
		}
	}

	private Result handler(Task task) {
		System.out.println("[ExecuteTaskActor]" + task.getName() + " 任务正在处理中");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 这里我们执行任务 成功返回success 失败 返回 fail 为了演示 我们随机产生正确失败结果 模拟 可能会出现失败成功
		if (rnd.nextInt(20) > 10) {
			return new Result(task.getName(), "success");
		} else {
			return new Result(task.getName(), "fail");
		}
	}
}
