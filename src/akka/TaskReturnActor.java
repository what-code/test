package akka;

import java.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import akka.actor.UntypedActor;

/**
 * Title:TaskReturnActor.java
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

public class TaskReturnActor extends UntypedActor{
	/**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TaskReturnActor.class);
 
    private Map<String,Object> results = new HashMap<String,Object>();
     
    @Autowired(required = false)
	@Override
    public void onReceive(Object message) throws Exception {
         
        if(message instanceof Result){
            Result rs=(Result) message;
            System.out.println("[TaskReturnActor]接收到 被处理的结果 信息  任务 "+rs.getTaskName()+" 该任务执行的成功失败情况 :"+rs.getResult());
            if(rs.getResult().equals("success")){
                results.put(rs.getTaskName(), rs.getResult());
                 
            }
            if(results.size()==5){
                //这里我只是简单的写一下 实际上我们可以传递参数 获取任务数目 为了回顾上面讲的知识 我们
                getSender().tell(new Result(), null); //这里是我注定 告诉 已经处理完了 ok 可以返回结果了 ，如果有些任务等不及 ，你使用 ExecuteTaskActorRef.tell(new Result(),null)同样可以获得中间的结果
            }
        }else if(message instanceof String){
            //获得结果 我们就输出算了
            System.out.println("[TaskReturnActor]执行任务成功的任务名 有 "+results.keySet().toString());
        }else{
        	unhandled(message);
        }
    }
}

