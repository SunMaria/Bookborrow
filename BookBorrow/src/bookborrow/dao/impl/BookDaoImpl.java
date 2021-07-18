package bookborrow.dao.impl;
import java.sql.*;
import java.util.*;
import bookborrow.dao.*;
import bookborrow.entity.*;
public class BookDaoImpl extends BaseDao implements BookDao{
	private Connection conn=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;

	public List<Book> getAllBook() {
		List<Book> bookList = new ArrayList<Book>();
		try {
			String preparedSql="select * from book";
			conn=getConn();
			pstmt=conn.prepareStatement(preparedSql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Book book=new Book();
				book.setId(rs.getInt(1));
				book.setPrice(rs.getInt(2));
				book.setName(rs.getString(3));
				book.setType(rs.getString(4));
				book.setState(rs.getString(5));
				bookList.add(book);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(conn, pstmt, rs);
		}
		return bookList;
	}


	public Book getBook(String sql, Object[] param) {
		Book book = new Book();
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
				book=new Book();
				book.setId(rs.getInt(1));
				book.setPrice(rs.getInt(2));
				book.setName(rs.getString(3));
				book.setType(rs.getString(4));
				book.setState(rs.getString(5));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(conn, pstmt, rs);
		}
		return book;
	}
	
	@Override
	public List<Book> getSomeBook(String sql, Object[] param) {
		// TODO Auto-generated method stub
		List<Book> bookList = new ArrayList<Book>();
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
				Book book=new Book();
				book.setId(rs.getInt(1));
				book.setPrice(rs.getInt(2));
				book.setName(rs.getString(3));
				book.setType(rs.getString(4));
				book.setState(rs.getString(5));
				bookList.add(book);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(conn, pstmt, rs);
		}
		return bookList;
	}


	public int updateBook(String sql, Object[] param) {
		int count=executeSQL(sql,param);
		return count;
	}
	
	public int addBook(String sql, Object[] param) {
		int count=executeSQL(sql,param);
		return count;
	}

	public int deleteBook(String sql, Object[] param) {
		int count=executeSQL(sql,param);
		return count;
	}
}