package ibatis;

import java.util.List;

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
public class TestIbatis {
	public static void main(String[] args){
		UserDaoImpl studentDaoImpl = new UserDaoImpl(); 
		List<User> students = studentDaoImpl.selectAllUser();  
        for (User student : students) {  
            System.out.println(student.getId() + "_" + student.getName() + "_" + student.getAddr());  
        }  
	}
}
