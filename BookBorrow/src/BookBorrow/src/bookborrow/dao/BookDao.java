package bookborrow.dao;
import java.util.*;
import bookborrow.entity.*;
public interface BookDao {
	public List<Book> getAllBook(); //查询所有书
	public Book getBook(String sql, Object[]param);//条件查询
	public int updateBook(String sql, Object[]param);//更新
}
