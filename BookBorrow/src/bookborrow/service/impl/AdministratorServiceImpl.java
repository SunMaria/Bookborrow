package bookborrow.service.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import bookborrow.service.*;
import bookborrow.dao.*;
import bookborrow.dao.impl.*;
import bookborrow.entity.*;


public class AdministratorServiceImpl extends BaseDao implements AdministratorService {
	private Connection conn=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;
	
	
	public Administrator login() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("请登录，请输入管理员名称:");
		String name=input.nextLine().trim();
		System.out.println("请输入密码:");
		String password=input.nextLine().trim();
		AdministratorDao administratordao=new AdministratorDaoImpl();
		String sql="select * from administrsator where name=? and password=?";
		String[] param= {name,password};
		Administrator administrator=administratordao.getAdministrator(sql, param);
		if(null != administrator) {
			System.out.println("登录成功");
		}
		return administrator;
	}
	public List<User> list_user() {
        List<User> u1=new ArrayList<User>();
        UserDao userDao=new UserDaoImpl();
        u1= userDao.getAllUser();
        return u1;
    }
	public List<User> select_user(String name) {
		List<User> userList = new ArrayList<User>();
		try {
			String preparedSql="select * from user where uname = ?";
			String [] param = {name};
			conn=getConn();
			pstmt=conn.prepareStatement(preparedSql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				User user=new User();
				user.setName(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setLevel(rs.getInt(4));
				userList.add(user);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			this.closeAll(conn, pstmt, rs);
		}

		return null;		
	}
	public void add_book() {
		
	}
	public void add_user() {
		
	}
	public void list_history() {
		
	}
	public void delete_book() {
		
	}
	@Override
	 public List<Book> list() {
        BookDao bookdao=new BookDaoImpl();
        List<Book> booklist = new ArrayList<Book>();
        booklist=bookdao.getAllBook();
        return booklist;
    }
	@Override
	public Book select(String bookname) {
		// TODO Auto-generated method stub
		return null;
	}

}
