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
		Scanner input = new Scanner(System.in);
		System.out.println("请登录，请输入管理员名称:");
		String name=input.nextLine().trim();
		System.out.println("请输入密码:");
		String password=input.nextLine().trim();
		AdministratorDao administratordao=new AdministratorDaoImpl();
		String sql="select * from administrator where name=? and password=?";
		String[] param= {name,password};
		Administrator administrator=administratordao.getAdministrator(sql, param);
		return administrator;
	}
	public List<User> list_user() {
        List<User> u1=new ArrayList<User>();
        UserDao userDao=new UserDaoImpl();
        u1= userDao.getAllUser();
        return u1;
    }

	public List<User> select_user(String name) {
		UserDao userdao=new UserDaoImpl();
		List<User> userlist=new ArrayList<User>();
		String sql="select * from users where name=?";
		String[] param= {name};
		userlist=userdao.getSomeUser(sql, param);//查询book
		return userlist;
	}


	public boolean add_book() {
		String bookName;
		Scanner input = new Scanner(System.in);
		System.out.println("请输入添加的书名：");
		bookName=input.next();
		System.out.println("请输入添加的书类型：");
		String type=input.next();
		System.out.println("请输入添加的书价：");
		int price=input.nextInt();
		BookDao bookdao=new BookDaoImpl();
		List<Book> booklist= new ArrayList<Book>();
		
		int num=0;
		booklist=bookdao.getAllBook();//查询book
		for (int i = 0; i < booklist.size()-1; i++) {
			Book book1 = booklist.get(i);
			Book book2 = booklist.get(i+1);
			if((book1.getId()+1)!=book2.getId()) {
				num=book1.getId()+1;
			}
		}
		if(num==0) {
			Book book = booklist.get(booklist.size()-1);
			num=book.getId()+1;
		}
		Book book=new Book(num,price,bookName,type,"Free");
		String sql="insert into book(id,price,name,type,state) values(?,?,?,?,?)";
		Object[] param={book.getId(),book.getPrice(),book.getName(),book.getType(),book.getState()};
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
		UserDao uDao = new UserDaoImpl();
		List<User> ulist = uDao.getAllUser();
		for (int i = 0; i < ulist.size(); i++) {
			User users = ulist.get(i);
			if(users.getName().equals(userName)) {
				System.out.println("用户名重复");
				return false;
			}
		}
		user.setName(userName);
		System.out.println("请输入添加的用户密码：");
		password=input.next();
		System.out.println("请输入添加的用户电话：");
		String phone=input.next();
		user.setPassword(password);
		user.setPhone(phone);
		user.setLevel(10);
		user.setMoney(100);
		String sql="insert into users(name,password,phone,level,money) values(?,?,?,?,?)";
		Object[] param={user.getName(),user.getPassword(),phone,user.getLevel(),100};
		UserDao userDao=new UserDaoImpl();
		int count=userDao.addUser(sql,param);
		if(count>0) {
			System.out.println("成功添加！");
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean change_user() {
		Scanner input = new Scanner(System.in);
		System.out.println("请输入用户名");
		String username=input.nextLine();
		System.out.println("请输入要更改的密码");
		String p=input.nextLine();
		String sql="update users set password=? where name=?";
		Object[] param= {p,username};
		UserDao udao=new UserDaoImpl();
		int count=udao.updateUser(sql, param);
		if(count>0) {
			System.out.println("密码修改成功！");
			return true;
		}
		else {
			System.out.println("修改错误！");
			return false;
		}
	}

	@Override
	public boolean change_book() {
		Scanner input =new Scanner(System.in);
		System.out.println("请输入要修改的书籍id:");
		int id=input.nextInt();
		UserService us = new UserServiceImpl();
		Book book= us.selectid(id);
		if(book.getName().equals(null)) {
			System.out.println("书籍不存在");
		}else {
			System.out.println("序号\t"+ "价格\t"+ "书籍名称\t"+"书籍类型\t"+"书籍状态\t");
			System.out.println(book.getId()+"\t"+ book.getPrice()+"\t"+book.getName()+"\t"+
					book.getType()+"\t"+book.getState()+"\t");
		}
		System.out.println("请输入修改后的书籍名称");
		String bname=input.next();
		System.out.println("请输入修改后的书籍类型");
		String btype=input.next();
		System.out.println("请输入修改后的书籍价格");
		int bprice=input.nextInt();
		String sql="update book set name=? , type=? , price=? where bid=?";
		Object[] param= {bname,btype,bprice,id};
		BookDao bookDao=new BookDaoImpl();
		int count=bookDao.updateBook(sql, param);
		if(count>0) {
			System.out.println("修改成功,信息改为：");
			System.out.println("序号\t"+ "价格\t"+ "书籍名称\t"+"书籍类型\t"+"书籍状态\t");
			System.out.println(book.getId()+"\t"+ book.getPrice()+"\t"+book.getName()+"\t"+
					book.getType()+"\t"+book.getState()+"\t");
			return true;
		}
		else {
			System.out.println("修改错误！");
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
		String sql="delete from book where id=?";
		Object[] param={bid};
		BookDao bookDao=new BookDaoImpl();
		int count=bookDao.deleteBook(sql,param);
		if(count>0) {
			System.out.println("成功删除！");
			System.out.println("输入 1 确定是否清理该书籍的历史记录");
			int i=input.nextInt(),count1=0;
			if(i==1) {
				HistoryDao hdao=new HistoryDaoImpl();
				String sql1="delete from history where bid=? and returntime is not null";
				Object[] param1={bid};
				count1=hdao.updateHistory(sql1, param1);
				if(count1>0) {
					System.out.println("成功清除历史记录！");
				}
				else {
					System.out.println("历史记录清除失败或没有历史记录！");
				}
			 }
			 return true;
		}
		else {
			System.out.println("未查找到此书！");
			return false;
		}
	}
	
	public boolean delete_user() {
		String name;
		Scanner input = new Scanner(System.in);
		System.out.println("请输入想删除用户名：");
		name=input.next();
		String sql="delete from users where name=?";
		Object[] param={name};
		UserDao udao=new UserDaoImpl();
		int count=udao.updateUser(sql,param);
		if(count>0) {
			System.out.println("成功删除！");
			System.out.println("输入 1 确定是否清理该用户的历史记录");
			int i=input.nextInt(),count1=0;
			if(i==1) {
				HistoryDao hdao=new HistoryDaoImpl();
				String sql1="delete from history where uname=? and returntime is not null";
				Object[] param1={name};
				count1=hdao.updateHistory(sql1, param1);
				if(count1>0) {
					System.out.println("成功清除历史记录！");
				}
				else {
					System.out.println("历史记录清除失败或没有历史记录！");
				}
			}
			return true;
		}
		else {
			System.out.println("未查找到此用户！");
			return false;
		}
	}
	
	public boolean delete_history() {
		Scanner input = new Scanner(System.in);
		System.out.println("是否清理一个月前的历史记录");
		System.out.println("输入 1 确定清理");
		int i=input.nextInt(),count=0,s=0;
		if(i==1) {
			SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
			sdf.applyPattern("yyyy-MM-dd");// a为am/pm的标记
			Date date = new Date();// 获取系统当前时间
			HistoryDao hdao=new HistoryDaoImpl();
			List<History> hlist=hdao.getAllHistory();
			String time=sdf.format(new Date((date.getTime()/1000-30* 24 * 60 * 60 )*1000));
			String sql="delete from history where returntime=? and returntime is not null";
			int j = 0;
			for (j=0; j < hlist.size(); j++) {
				History h = hlist.get(j);
				if(h.getReturntime().compareTo(time)<0) {
					Object[] param={h.getReturntime()};
					count=hdao.updateHistory(sql,param);
					s=s+count;
				}
			}
			if(s>0) {
				return true;
			}
		}
	    return false;	
	}
	
	@Override
	 public List<Book> list() {
        BookDao bookdao=new BookDaoImpl();
        List<Book> booklist = new ArrayList<Book>();
        booklist=bookdao.getAllBook();
        return booklist;
    }
	@Override
	public List<Book> select(String bookname) {
		BookDao bookdao=new BookDaoImpl();
		List<Book> book= new ArrayList<Book>();
		String sql="select * from book where name=?";
		String[] param= {bookname};
		book=bookdao.getSomeBook(sql, param);//查询book
		return book;
	}
	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		int k=0;
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
	    sdf.applyPattern("yyyy-MM-dd");
	    Date d=new Date();
	    String t=sdf.format(new Date((d.getTime()/1000-14* 24 * 60 * 60 )*1000));
		HistoryDao hd=new HistoryDaoImpl();
		List<History> hl=hd.getAllHistory();
		for (int i=0;i<hl.size();i++) {
        	History h = hl.get(i);
	        if(h.getReturntime().equals("null") && t.compareTo(h.getDdl())>0) {
	        	String sql="select * from users where name=?";
	        	Object[] param= {h.getUname()};
	        	UserDao ud=new UserDaoImpl();
	        	User u=ud.getUser(sql, param);
	        	
	        	String sql1="select * from book where id=?";
	        	Object[] param1= {h.getBid()};
	        	BookDao bd=new BookDaoImpl();
	        	Book b=bd.getBook(sql1, param1);
	        	
	        	String sql2="update users set money=? where name=?";
		    	int money=u.getMoney()-b.getPrice();
			    Object[] param2= {money,u.getName()};
			    int count=ud.updateUser(sql2, param2);
			    if(count>0) {
				    System.out.println("书籍归还超时过久，自动购买，用户:"+u.getName()+"保证金剩余:"+money);
			    }
			    
			    String sql3="delete from book where id=?";
				Object[] param3={h.getBid()};
				int count1=bd.deleteBook(sql3,param3);
				if(count1>0) {
					System.out.println("成功删除书籍:"+"书id "+b.getId()+"书名 "+b.getName());
				}
				k=1;
	        }
        }
		if(k==1) {
			return true;
		}else {return false;}
	}
	
}