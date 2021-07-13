package bookborrow.dao;
import java.util.*;
import bookborrow.entity.*;
public interface HistoryDao {
	public List<History> getAllHistory(); //查询所有History
	public History getHistory(String sql, Object[]param);//条件查询
	public List<History> getSomeHistory(String sql, Object[]param);//条件查询2
	public int updateHistory(String sql, Object[]param);//更新
}
