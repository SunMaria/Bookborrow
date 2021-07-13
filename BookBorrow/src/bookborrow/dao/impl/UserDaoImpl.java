package bookborrow.dao.impl;
import java.sql.*;
import java.util.*;
import bookborrow.dao.*;
import bookborrow.entity.*;
public class UserDaoImpl extends BaseDao implements UserDao {
	private Connection conn=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	
	@Override
	public List<User> getAllUser() {
		List<User> userList = new ArrayList<User>();
		try {
			String preparedSql="select * from customer";
			conn=getConn();
			pstmt=conn.prepareStatement(preparedSql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				User user=new User();
				user.setName(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setLevel(rs.getInt(3));
				userList.add(user);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(conn, pstmt, rs);
		}
		return userList;
	}

	@Override
	public User getUser(String sql, Object[] param) {
		User user = new User();
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
				user=new User();
				user.setName(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setLevel(rs.getInt(3));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(conn, pstmt, rs);
		}
		return user;
	}

	@Override
	public int updateUser(String sql, Object[] param) {
		int count=super.executeSQL(sql,param);
		return count;
	}

}
