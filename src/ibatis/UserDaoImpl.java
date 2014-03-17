package ibatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2014-3-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author shengjie.guo
 * 
 * @version 1.0
 */
public class UserDaoImpl implements UserDao {

	private static SqlMapClient sqlMapClient = null;

	// 读取配置文件
	static {
		try {
			Reader reader = Resources
					.getResourceAsReader("ibatis/SqlMapConfig.xml");
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean addUser(User student) {
		Object object = null;
		boolean flag = false;
		try {
			object = sqlMapClient.insert("addStudent", student);
			System.out.println("添加学生信息的返回值：" + object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (object != null) {
			flag = true;
		}
		return flag;
	}

	public boolean deleteUserById(int id) {
		boolean flag = false;
		Object object = null;
		try {
			object = sqlMapClient.delete("deleteStudentById", id);
			System.out.println("删除学生信息的返回值：" + object + "，这里返回的是影响的行数");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (object != null) {
			flag = true;

		}
		return flag;
	}

	public List<User> selectAllUser() {
		List<User> students = null;
		try {
			students = sqlMapClient.queryForList("selectAllUser");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	public User selectUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> selectUserByName(String name) {
		return null;
	}

	public boolean updateUser(User student) {
		// TODO Auto-generated method stub
		return false;
	}

}
