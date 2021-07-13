package bookborrow.dao;
import java.util.*;
import bookborrow.entity.*;

public interface AdministratorDao {
	public List<Administrator> getAllAdministrator(); //查询所有Administrator
	public Administrator getAdministrator(String sql, Object[]param);//条件查询
	public int updateAdministrator(String sql, Object[]param);//更新
}
