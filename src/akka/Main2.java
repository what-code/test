package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Main2 {

	public static void main(String[] args) {
		/**
		 * 逻辑:
		 * 1.HelloWorld--msg-->Greeter
		 * 2.Greeter--msg-->HelloWorld
		 * 3.HelloWorld 结束---->Terminator(监听到HelloWorld结束,打印消息)
		 */
		ActorSystem actorSystem = ActorSystem.create("Hello");
		ActorRef helloWorld = actorSystem.actorOf(Props.create(HelloWorld.class),"helloWorld");
		//第一个参数表示Actor的class，第二个参数是传递给Actor构造函数的参数:Props.create(Terminator.class, helloWorld)
		actorSystem.actorOf(Props.create(Terminator.class, helloWorld), "terminator");
	}

	public static class Terminator extends UntypedActor {

		private final LoggingAdapter log = Logging.getLogger(getContext()
				.system(), this);
		private final ActorRef ref;

		public Terminator(ActorRef ref) {
			this.ref = ref;
			getContext().watch(ref);
		}

		@Override
		public void onReceive(Object msg) {
			if (msg instanceof Terminated) {
				System.out.println("refPath-->" + ref.path());
				log.info("{} has terminated, shutting down system", ref.path());
				getContext().system().shutdown();
			} else {
				unhandled(msg);
			}
		}

	}
}
