package bookborrow.service.impl;//还可以添加续借，预借
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
		int k=0;
		if(user.getLevel()<=0) {
			System.out.println("信用过低，请退出重新登录");
		}
		else {
			Scanner input = new Scanner(System.in);
			System.out.println("请输入要借阅的书本ID:");
			int bid=input.nextInt();
			
			BookDao bookDao = new BookDaoImpl();
			List<Book> bookList = bookDao.getAllBook();
			for (int i = 0; i < bookList.size(); i++) {
				Book book1 = bookList.get(i);
				if(bid==book1.getId()) {
					k=1;
					BookDao bookdao1=new BookDaoImpl();
					Book book=new Book();
					String sql1="select * from book where id=?";
					Object[] param1= {bid};
					book=bookdao1.getBook(sql1, param1);//查询book
					if(book.getState().equals("Lend")){
						System.out.println(bid+" 已经被借阅");
					}else {
						//更新book状态
						String sql2="update book set state=? where id=?";
					    Object[] param2= {"Lend",bid};
					    BookDao bookdao2=new BookDaoImpl();
					    int count1=bookdao2.updateBook(sql2, param2);
						//插入history
					    SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
					    sdf.applyPattern("yyyy-MM-dd");// a为am/pm的标记
					    Date date = new Date();// 获取系统当前时间
					    String lendtime=sdf.format(date); // 输出已经格式化的现在时间(24小时制)
					    String ddl=sdf.format(new Date((date.getTime()/1000+user.getLevel()*2* 24 * 60 * 60 )*1000));
					    String returntime=null;
					    String sql3="insert into history(bid,uname,bname,lendtime,ddl,returntime) values(?,?,?,?,?,?)";
					    Object[] param3= {bid,user.getName(),book.getName(),lendtime,ddl,returntime};
					    HistoryDao historydao=new HistoryDaoImpl();
					    int count=historydao.updateHistory(sql3, param3);
					    if(count>0 && count1>0 ) {
						    System.out.println("成功借阅:"+bid+"号书籍");
					    }
					}
					break;
				}
			}
			if(k==0) {
				System.out.println("输入ID不存在，请重新输入");
			}
		}
	}

	@Override
	public void returnbook(User user) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("请输入要归还的书ID:");
		int bid=input.nextInt();
		BookDao bookDao = new BookDaoImpl();
		List<Book> bookList = bookDao.getAllBook();
		int k=0;
		for (int i = 0; i < bookList.size(); i++) {
			Book book1 = bookList.get(i);
			if(bid==book1.getId()) {
				k=1;
				BookDao bookdao1=new BookDaoImpl();
				Book book=new Book();
				String sql1="select * from book where id=?";
				Object[] param1= {bid};
				book=bookdao1.getBook(sql1, param1);//查询book
				if(book.getState().equals("Free")){
					System.out.println("已经归还");
				}
				else if(book.getName().equals(null)) {
					System.out.println("输入错误书名不存在");
				}else {
					//更新book状态
					String sql2="update book set state=? where id=?";
				    Object[] param2= {"Free",bid};
				    BookDao bookdao2=new BookDaoImpl();
				    int count1=bookdao2.updateBook(sql2, param2);
				    
				    SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
				    sdf.applyPattern("yyyy-MM-dd");
				    Date date = new Date();// 获取系统当前时间
				    String returntime=sdf.format(date); // 输出已经格式化的现在时间(24小时制)
				    
				    
					//更新history
				    String sql3="update history set returntime=? where bid=? and uname=?";
				    Object[] param3= {returntime,bid,user.getName()};
				    HistoryDao historydao=new HistoryDaoImpl();
				    int count=historydao.updateHistory(sql3, param3);
				    if(count>0 && count1>0) {
					    System.out.println("成功归还:"+bid+" "+book.getName());
				    }
				    
				    //更新level
				    String sql="select * from history where uname=? and bid=? and returntime=?";
				    Object[] param= {user.getName(),bid,returntime};
					HistoryDao hdao=new HistoryDaoImpl();
					History history=new History();
					history=hdao.getHistory(sql, param);
					int level=user.getLevel();
				    if(returntime.compareTo(history.getDdl())>0) {
				    	level=level-5;
				    	user.setLevel(level);	
				    }
				    else {
				    	if(level<30) {
				    		level=level+1;
				    	    user.setLevel(level);
				    	}
				    }
				    String sql4="update users set level=? where name=?";
				    Object[] param4= {level,user.getName()};
				    UserDao udao=new UserDaoImpl();
				    int count2=udao.updateUser(sql4, param4);
				    if(count2>0) {
					    System.out.println("信用等级变化:"+user.getLevel());
				    }
				    
				    String t=sdf.format(new Date((date.getTime()/1000-14* 24 * 60 * 60 )*1000));
				    if(t.compareTo(history.getDdl())>0) {
				    	String sql5="update users set money=? where name=?";
				    	int money=user.getMoney()-book.getPrice();
					    Object[] param5= {money,user.getName()};
					    UserDao udao2=new UserDaoImpl();
					    int count3=udao2.updateUser(sql5, param5);
					    if(count3>0) {
						    System.out.println("书籍归还超时过久，自动购买，保证金剩余:"+money);
					    }
					    user.setMoney(money);
					    String sql6="delete from book where id=?";
						Object[] param6={bid};
						int count4=bookDao.deleteBook(sql6,param6);
						if(count4>0) {
							System.out.println("成功删除该书！");
						}
					}
				}
				break;
			}
		}
		if(k==0) {
			System.out.println("输入ID不存在，请重新输入");
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
	public List<Book> select(String bookname) {
		// TODO Auto-generated method stub
		BookDao bookdao=new BookDaoImpl();
		List<Book> booklist=new ArrayList<Book>();
		String sql="select * from book where name=?";
		String[] param= {bookname};
		booklist=bookdao.getSomeBook(sql, param);//查询book
		return booklist;
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
		String sql="select * from users where name=? and password=?";
		String[] param= {name,password};
		User user=userdao.getUser(sql, param);
		if(user.getName() != null) {
			System.out.println("登录成功");
			System.out.println("信用等级为:"+user.getLevel());
			System.out.println("保证金为:"+user.getMoney());
			if(user.getLevel()<=0) {
				System.out.println("信用过低，请找管理员恢复信用");
				//修改money
				String sql2="update users set money=? where name=?";
				int m=user.getMoney()-100;
			    Object[] param2= {m,user.getName()};
			    UserDao udao=new UserDaoImpl();
			    int count2=udao.updateUser(sql2, param2);
			    user.setMoney(m);
			    //修改level
				String sql1="update users set level=? where name=?";
				int level=5;
			    Object[] param1= {level,user.getName()};
			    UserDao udao1=new UserDaoImpl();
			    int count=udao1.updateUser(sql1, param1);
			    if(count>0 && count2>0) {
			    	System.out.println("信用等级已恢复为5");
			    	System.out.println("当前保证金剩余："+user.getMoney());
			    	if(user.getMoney()<=0) {
			    		System.out.println("请尽快充值！");
			    	}
			    }
			    user.setLevel(level);
			}
		}
		return user;
	}

	@Override
	public void passwordchange(User user,String password) {
		// TODO Auto-generated method stub
		if(!user.getPassword().equals(password)) {
			System.out.println("密码错误");
		}else {
			Scanner input = new Scanner(System.in);
			System.out.println("请输入要更改的密码");
			String p=input.nextLine();
			String sql="update users set password=? where name=?";
		    Object[] param= {p,user.getName()};
		    UserDao udao=new UserDaoImpl();
		    int count=udao.updateUser(sql, param);
		    if(count>0) {
		    	System.out.println("密码修改成功！");
		    }    
		}
	}

	@Override
	public void recharge(User user) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("请输入要充值的金额");
		int m=input.nextInt();
		user.setMoney(m+user.getMoney());
		String sql="update users set money=? where name=?";
	    Object[] param= {m,user.getName()};
	    UserDao udao=new UserDaoImpl();
	    int count=udao.updateUser(sql, param);
	    if(count>0) {
	    	System.out.println("充值成功！当前金额为："+user.getMoney());
	    }    
	}

	@Override
	public List<Book> selecttype(String booktype) {
		// TODO Auto-generated method stub
		BookDao bookdao=new BookDaoImpl();
		List<Book> booklist=new ArrayList<Book>();
		String sql="select * from book where type=?";
		String[] param= {booktype};
		booklist=bookdao.getSomeBook(sql, param);//查询book
		return booklist;
	}

	@Override
	public Book selectid(int id) {
		// TODO Auto-generated method stub
		BookDao bookdao=new BookDaoImpl();
		Book book=new Book();
		String sql="select * from book where id=?";
		Object[] param= {id};
		book=bookdao.getBook(sql, param);//查询book
		return book;
	}

}
