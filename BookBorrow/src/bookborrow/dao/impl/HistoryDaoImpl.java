package bookborrow.dao.impl;
import java.sql.*;
import java.util.*;
import bookborrow.dao.*;
import bookborrow.entity.*;
public class HistoryDaoImpl extends BaseDao implements HistoryDao{
	private Connection conn=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	@Override
	public List<History> getAllHistory() {
		List<History> historyList = new ArrayList<History>();
		try {
			String preparedSql="select * from history";
			conn=getConn();
			pstmt=conn.prepareStatement(preparedSql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				History history=new History();
				history.setUname(rs.getString(1));
				history.setBname(rs.getString(2));
				history.setLendtime(rs.getDate(3).toString());
				history.setDdl(rs.getDate(4).toString());
				history.setReturntime(rs.getDate(5).toString());
				historyList.add(history);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(conn, pstmt, rs);
		}
		return historyList;
	}

	@Override
	public History getHistory(String sql, Object[] param) {
		History history = new History();
		try {
			conn=getConn();
			pstmt=conn.prepareStatement(sql);
			if(param!=null) {
				for(int i=0;i<param.length;i++) {
					pstmt.setObject(i+1, param[i]);
				}
			}
			rs=pstmt.executeQuery();
			while(rs.next()) {
				history=new History();
				history.setUname(rs.getString(1));
				history.setBname(rs.getString(2));
				history.setLendtime(rs.getDate(3).toString());
				history.setDdl(rs.getDate(4).toString());
				history.setReturntime(rs.getDate(5).toString());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(conn, pstmt, rs);
		}
		return history;
	}

	@Override
	public int updateHistory(String sql, Object[] param) {
		int count=super.executeSQL(sql,param);
		return count;
	}

	@Override
	public List<History> getSomeHistory(String sql, Object[] param) {
		// TODO Auto-generated method stub
		List<History> historyList = new ArrayList<History>();
		try {
			conn=getConn();
			pstmt=conn.prepareStatement(sql);
			if(param != null) {
				for(int i=0;i<param.length;i++) {
					pstmt.setObject(i+1, param[i]);
				}
			}
			rs=pstmt.executeQuery();
			while(rs.next()) {
				History history=new History();
				history.setUname(rs.getString(1));
				history.setBname(rs.getString(2));
				history.setLendtime(rs.getDate(3).toString());
				history.setDdl(rs.getDate(4).toString());
				history.setReturntime(rs.getDate(5).toString());
				historyList.add(history);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(conn, pstmt, rs);
		}
		return historyList;
	}

}
