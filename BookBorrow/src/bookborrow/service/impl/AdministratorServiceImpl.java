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
	public static int num=1;

	public Administrator login() {
		Scanner input = new Scanner(System.in);
		System.out.println("请登录，请输入管理员名称:");
		String name=input.nextLine().trim();
		System.out.println("请输入密码:");
		String password=input.nextLine().trim();
		AdministratorDao administratordao=new AdministratorDaoImpl();
		String sql="select * from administrator where name=? and password=?";
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
			String preparedSql="select * from user where uname like ?";
			String [] param = {name};
			conn=getConn();
			pstmt=conn.prepareStatement(preparedSql);
			pstmt.setString(1, "%"+name+"%");
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

		return userList;
	}


	public boolean add_book() {
		String bookName;
		Scanner input = new Scanner(System.in);
		System.out.println("请输入添加的书名：");
		bookName=input.next();
		Book book=new Book(num,bookName,"Free");
		String sql="insert into book(id,name,state) values(?,?,?)";
		Object[] param={book.getId(),book.getName(),book.getState()};
		BookDao bookDao=new BookDaoImpl();
		int count=bookDao.addBook(sql,param);
		if(count>0) {
			System.out.println("成功添加！");
			return true;
		}
		else
			return false;
	}

	public boolean add_user() {
		String userName;
		String password;
		User user=new User();
		Scanner input = new Scanner(System.in);
		System.out.println("请输入添加的用户名：");
		userName=input.next();
		user.setName(userName);
		System.out.println("请输入添加的用户密码：");
		password=input.next();
		user.setPassword(password);
		user.setLevel(10);
		String sql="insert into user(uname,password,level) values(?,?,?)";
		Object[] param={user.getName(),user.getPassword(),user.getLevel()};
		UserDao userDao=new UserDaoImpl();
		int count=userDao.addUser(sql,param);
		if(count>0) {
			System.out.println("成功添加！");
			return true;
		}
		else {
			System.out.println("添加失败！");
			return false;
		}
	}
	public List<History> list_history() {
		List<History> historyList = new ArrayList<History>();
		HistoryDao historyDao=new HistoryDaoImpl();
		historyList=historyDao.getAllHistory();
		return historyList;
	}
	public boolean delete_book() {
		int bid;
		Scanner input = new Scanner(System.in);
		System.out.println("请输入想删除的书籍id：");
		bid=input.nextInt();
		String sql="delete form book where bid=?";
		Object[] param={bid};
		BookDao bookDao=new BookDaoImpl();
		int count=bookDao.deleteBook(sql,param);
		if(count>0) {
			System.out.println("成功删除！");
			return true;
		}
		else {
			System.out.println("未查找到此书！");
			return false;
		}
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
		BookDao bookdao=new BookDaoImpl();
		Book book=new Book();
		String sql="select * from book where name=?";
		String[] param= {bookname};
		book=bookdao.getBook(sql, param);//查询book
		return book;
	}


}
