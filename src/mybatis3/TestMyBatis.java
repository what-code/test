package mybatis3;

import java.io.*;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.ibatis.common.resources.Resources;

/**
 * Title:TestMyBatis.java
 * 
 * Description:TestMyBatis.java
 * 
 * Copyright: Copyright (c) 2014-10-14
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author duanyu@b5m.com
 * 
 * @version 1.0
 */
public class TestMyBatis {

	private static SqlSessionFactory sqlSessionFactory = null;
	// 读取配置文件
	static {
		try {
			String resource = "mybatis3/config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Activity> selectAllStudent() {
		List<Activity> students = null;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			students = session.selectList("selectAllAct");
		} finally {
		  session.close();
		}
		return students;
	}

	public static void main(String[] args){
		TestMyBatis tm = new TestMyBatis();
		List<Activity> list = tm.selectAllStudent();
		for(Activity s : list){
			System.out.println("----s--->" + s.getActivityId() + "----->" + s.getActivityName() + "----->" + s.getActivityLargeImg());
		}
	}
}
