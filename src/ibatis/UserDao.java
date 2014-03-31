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
     * ���ѧ����Ϣ 
     *  
     * @param student 
     *            ѧ��ʵ�� 
     * @return �����Ƿ���ӳɹ� 
     */  
    public boolean addUser(User student);  
  
    /** 
     * ����ѧ��idɾ��ѧ����Ϣ 
     *  
     * @param id 
     *            ѧ��id 
     * @return ɾ���Ƿ�ɹ� 
     */  
    public boolean deleteUserById(int id);  
  
    /** 
     * ����ѧ����Ϣ 
     *  
     * @param student 
     *            ѧ��ʵ�� 
     * @return �����Ƿ�ɹ� 
     */  
    public boolean updateUser(User student);  
  
    /** 
     * ��ѯȫ��ѧ����Ϣ 
     *  
     * @return ����ѧ���б� 
     */  
    public List<User> selectAllUser();  
  
    /** 
     * ����ѧ������ģ����ѯѧ����Ϣ 
     *  
     * @param name 
     *            ѧ������ 
     * @return ѧ����Ϣ�б� 
     */  
    public List<User> selectUserByName(String name);  
  
    /** 
     * ����ѧ��id��ѯѧ����Ϣ 
     *  
     * @param id 
     *            ѧ��id 
     * @return ѧ������ 
     */  
    public User selectUserById(int id);  
}
