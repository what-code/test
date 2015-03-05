package akka;

import java.io.Serializable;

/**
 * Title:Result.java
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

public class Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1783710209599421843L;

	private String taskName;
	
	private String result;

	public Result(){}
	
	public Result(String taskName,String result){
		this.taskName = taskName;
		this.result = result;
	}
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}

