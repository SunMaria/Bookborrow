package bookborrow.dao;
import java.util.*;
import bookborrow.entity.*;
public interface UserDao {
	public List<User> getAllUser(); //查询所有用户
	public User getUser(String sql, Object[]param);//条件查询
	public int updateUser(String sql, Object[]param);//更新
}
