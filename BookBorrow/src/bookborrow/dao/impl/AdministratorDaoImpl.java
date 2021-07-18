package bookborrow.dao.impl;
import java.sql.*;
import java.util.*;
import bookborrow.dao.*;
import bookborrow.entity.*;

public class AdministratorDaoImpl extends BaseDao implements AdministratorDao{
	private Connection conn=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	@Override
	public List<Administrator> getAllAdministrator() {
		List<Administrator> administratorList = new ArrayList<Administrator>();
		try {
			String preparedSql="select * from administrator";
			conn=getConn();
			pstmt=conn.prepareStatement(preparedSql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Administrator administrator=new Administrator();
				administrator.setName(rs.getString(1));
				administrator.setPassword(rs.getString(2));
				administrator.setPhone(rs.getString(3));
				administratorList.add(administrator);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(conn, pstmt, rs);
		}
		return administratorList;
	}

	@Override
	public Administrator getAdministrator(String sql, Object[] param) {
		Administrator administrator = new Administrator();
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
				administrator=new Administrator();
				administrator.setName(rs.getString(1));
				administrator.setPassword(rs.getString(2));
				administrator.setPhone(rs.getString(3));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(conn, pstmt, rs);
		}
		return administrator;
	}

	@Override
	public int updateAdministrator(String sql, Object[] param) {
		int count=super.executeSQL(sql,param);
		return count;
	}
}
