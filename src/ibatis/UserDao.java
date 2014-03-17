package ibatis;
import java.util.*;
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
public interface UserDao {
	 /** 
     * 添加学生信息 
     *  
     * @param student 
     *            学生实体 
     * @return 返回是否添加成功 
     */  
    public boolean addUser(User student);  
  
    /** 
     * 根据学生id删除学生信息 
     *  
     * @param id 
     *            学生id 
     * @return 删除是否成功 
     */  
    public boolean deleteUserById(int id);  
  
    /** 
     * 更新学生信息 
     *  
     * @param student 
     *            学生实体 
     * @return 更新是否成功 
     */  
    public boolean updateUser(User student);  
  
    /** 
     * 查询全部学生信息 
     *  
     * @return 返回学生列表 
     */  
    public List<User> selectAllUser();  
  
    /** 
     * 根据学生姓名模糊查询学生信息 
     *  
     * @param name 
     *            学生姓名 
     * @return 学生信息列表 
     */  
    public List<User> selectUserByName(String name);  
  
    /** 
     * 根据学生id查询学生信息 
     *  
     * @param id 
     *            学生id 
     * @return 学生对象 
     */  
    public User selectUserById(int id);  
}
