package akka;

import akka.actor.UntypedActor;

/**
 * Title:Greeter.java
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

public class Greeter extends UntypedActor {

	  public static enum Msg {
	    GREET, DONE;
	  }

	  @Override
	  public void onReceive(Object msg) {
	    if (msg != null) {
	      System.out.println("hi,i am greeter,your msg is:" + msg);
	      getSender().tell(Msg.DONE, getSelf());
	    } else
	      unhandled(msg);
	  }

	}

