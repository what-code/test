package mybatis3;

import java.util.Date;

/**
 * Title:Student.java
 * 
 * Description:Student.java
 * 
 * Copyright: Copyright (c) 2014-10-14
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author duanyu@b5m.com
 * 
 * @version 1.0
 */
public class Activity {

	private int activityId;
	private String activityName;
	private String activityLargeImg;

	public String getActivityName() {
		return activityName;
	}

	public String getActivityLargeImg() {
		return activityLargeImg;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public void setActivityLargeImg(String activityLargeImg) {
		this.activityLargeImg = activityLargeImg;
	}
	
	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	@Override
	public String toString() {
		return "Student [activityId=" + activityId + ", activityName="
				+ activityName + ", activityLargeImg=" + activityLargeImg + "]";
	}

}
