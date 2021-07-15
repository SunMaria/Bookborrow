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
				history.setBid(rs.getInt(1));
				history.setUname(rs.getString(2));
				history.setBname(rs.getString(3));
				history.setLendtime(rs.getDate(4).toString());
				history.setDdl(rs.getDate(5).toString());
				if(rs.getDate(6)==null){//判断null法1
					history.setReturntime("null");
				}else {
					history.setReturntime(rs.getDate(6).toString());
				}
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
				history.setBid(rs.getInt(1));
				history.setUname(rs.getString(2));
				history.setBname(rs.getString(3));
				history.setLendtime(rs.getDate(4).toString());
				history.setDdl(rs.getDate(5).toString());
				java.sql.Date date=rs.getDate(6);//判断null法2
				if(rs.wasNull()) {
					history.setReturntime("null");
				}else {
					history.setReturntime(rs.getDate(6).toString());
				}
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
				history.setBid(rs.getInt(1));
				history.setUname(rs.getString(2));
				history.setBname(rs.getString(3));
				history.setLendtime(rs.getDate(4).toString());
				history.setDdl(rs.getDate(5).toString());
				if(rs.getDate(6)==null){
					history.setReturntime("null");
				}else {
					history.setReturntime(rs.getDate(6).toString());
				}
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
