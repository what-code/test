package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Title:TestMain.java
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

public class TestMain {

	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("Hello");
		ActorRef executorTaskRef = actorSystem.actorOf(
				Props.create(ExecuteTaskActor.class), "executorTaskRef");
		for (int i = 0; i < 5; i++) {
			executorTaskRef.tell(new Task("[task:" + i + "]"), null);
		}
	}

}
