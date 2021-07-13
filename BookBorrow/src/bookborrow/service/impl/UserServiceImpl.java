package bookborrow.service.impl;
import java.text.SimpleDateFormat;
import java.util.*;
import bookborrow.service.*;
import bookborrow.dao.*;
import bookborrow.dao.impl.*;
import bookborrow.entity.*;

public class UserServiceImpl implements UserService{
	
	@Override
	public void lend(User user) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("请输入要借阅的书名:");
		String bname=input.nextLine().trim();
		BookDao bookdao1=new BookDaoImpl();
		Book book=new Book();
		String sql1="select * from book where name=?";
		String[] param1= {bname};
		book=bookdao1.getBook(sql1, param1);//查询book
		if(book.getState().equals("True")){
			System.out.println(bname+" 已经被借阅");
		}
		else {
			//更新book状态
			String sql2="update book set state=? where name=?";
		    String[] param2= {"True",bname};
		    BookDao bookdao2=new BookDaoImpl();
		    int count1=bookdao2.updateBook(sql2, param2);
			//插入history
		    SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		    sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
		    Date date = new Date();// 获取系统当前时间
		    String lendtime=sdf.format(date); // 输出已经格式化的现在时间(24小时制)
		    String ddl=sdf.format(new Date(date.getTime()+user.getLevel()*2* 24 * 60 * 60 * 1000));
		    String returntime=null;
		    String sql3="insert into history('uname','bname','lendtime','ddl','returntime') values(?,?,?,?,?)";
		    String[] param3= {user.getName(),bname,lendtime,ddl,returntime};
		    HistoryDao historydao=new HistoryDaoImpl();
		    int count=historydao.updateHistory(sql3, param3);
		    if(count>0 && count1>0) {
			    System.out.println("成功借阅:"+bname);
		    }
		}
		
	}

	@Override
	public void returnbook(User user) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("请输入要归还的书名:");
		String bname=input.nextLine().trim();
		BookDao bookdao1=new BookDaoImpl();
		Book book=new Book();
		String sql1="select * from book where name=?";
		String[] param1= {bname};
		book=bookdao1.getBook(sql1, param1);//查询book
		if(book.getState().equals("False")){
			System.out.println("已经归还");
		}
		else if(book.getName().equals(null)) {
			System.out.println("输入错误书名不存在");
		}
		else {
			//更新book状态
			String sql2="update book set state=? where name=?";
		    String[] param2= {"False",bname};
		    BookDao bookdao2=new BookDaoImpl();
		    int count1=bookdao2.updateBook(sql2, param2);
			//更新history
		    SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		    sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
		    Date date = new Date();// 获取系统当前时间
		    String returntime=sdf.format(date); // 输出已经格式化的现在时间(24小时制)
		    String sql3="update history set returntime=? where uname=? and bname=? and returntime=?";
		    String[] param3= {returntime,user.getName(),bname,null};
		    HistoryDao historydao=new HistoryDaoImpl();
		    int count=historydao.updateHistory(sql3, param3);
		    if(count>0 && count1>0) {
			    System.out.println("成功归还:"+bname);
		    }
		    String sql="where uname=? and bname=? and returntime=?";
			String[] param= {user.getName(),bname,returntime};
			HistoryDao hdao=new HistoryDaoImpl();
			History history=new History();
			history=hdao.getHistory(sql, param);
		    if(returntime.compareTo(history.getDdl())>0) {
		    	int level=user.getLevel();
		    	level=level-5;
		    	user.setLevel(level);
		    }
		    else {
		    	int level=user.getLevel();
		    	level=level+1;
		    	user.setLevel(level);
		    }
		}
	}

	@Override
	public List<Book> list() {
		// TODO Auto-generated method stub
		BookDao bookdao=new BookDaoImpl();
		List<Book> booklist = new ArrayList<Book>();
		booklist=bookdao.getAllBook();
		return booklist;
	}

	@Override
	public Book select(String bookname) {
		// TODO Auto-generated method stub
		BookDao bookdao=new BookDaoImpl();
		Book book=new Book();
		String sql="select * from book where name=?";
		String[] param= {bookname};
		book=bookdao.getBook(sql, param);//查询book
		return book;
	}

	@Override
	public List<History> mybook(String uname) {
		// TODO Auto-generated method stub
		List<History> hl=new ArrayList<History>();
		String sql="select * from history where uname=?";
		String[] param= {uname};
		HistoryDao hdao=new HistoryDaoImpl();
		hl=hdao.getSomeHistory(sql, param);
		return hl;
	}

	@Override
	public User login() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("请登录，先输入用户名:");
		String name=input.nextLine().trim();
		System.out.println("请输入密码:");
		String password=input.nextLine().trim();
		UserDao userdao=new UserDaoImpl();
		String sql="select * from user where name=? and password=?";
		String[] param= {name,password};
		User user=userdao.getUser(sql, param);
		if(null != user) {
			System.out.println("登录成功");
		}
		return user;
	}

}
