package akka;

import java.io.Serializable;

/**
 * Title:Task.java
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

public class Task implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1880406295891720196L;

	private String name;
	
	public Task(){}
	
	public Task(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

