package akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Title:MyUntypedActor.java
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

public class HelloWorld extends UntypedActor {
	@Override
	public void preStart() {
		// create the greeter actor
		final ActorRef greeter = getContext().actorOf(
				Props.create(Greeter.class), "greeter");
		// tell it to perform the greeting
		greeter.tell("hello greeter,i am hello world-->", getSelf());
	}

	@Override
	public void onReceive(Object msg) {
		if (msg != null) {
			// when the greeter is done, stop this actor and with it the
			// application
			System.out.println("HelloWorld onReceive msg:" + msg);
			getContext().stop(getSelf());
		} else
			unhandled(msg);
	}
}
