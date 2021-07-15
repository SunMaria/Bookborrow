package bookborrow.service.impl;

import java.util.*;
import bookborrow.entity.*;
import bookborrow.dao.*;
import bookborrow.dao.impl.*;
import bookborrow.service.impl.*;
import bookborrow.service.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*UserServiceImpl u=new UserServiceImpl();
		User u1=new User();
		u1=u.login();
		System.out.println(u1.getName());
		System.out.println(u1.getLevel());
		//u.lend(u1);
		u.returnbook(u1);
		System.out.println(u1.getLevel());
		
		u.mybook("aa");
		u.returnbook(u1);
		List<Book> booklist = new ArrayList<Book>();
		booklist=u.list();
		for(int i=0;i<booklist.size();i++) {
			System.out.println(booklist.get(i).getName());
		}*/
		Main.start();
	}
	
	private static void start() {
		System.out.println("----------------------社区图书借阅管理系统启动-----------------");
		System.out.println("图书信息");
		System.out.println("****************************************************");
		BookDao bookDao = new BookDaoImpl();
		List<Book> bookList = bookDao.getAllBook();
		System.out.println("序号\t" + "图书名称\t"+"图书状态\t");
		for (int i = 0; i < bookList.size(); i++) {
			Book book = bookList.get(i);
			System.out.println(book.getId()+"\t"+ book.getName()+"\t"+book.getState()+"\t");
		   }
		System.out.println("****************************************************");
        System.out.print("\n");
        System.out.println("用户信息");
        UserDao userDao = new UserDaoImpl();
		List<User> userList = userDao.getAllUser();
		System.out.println("****************************************************");
		System.out.println("序号\t" + "用户名\t" +"用户等级\t");
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			System.out.println((i + 1) +"\t"+ user.getName()+"\t"+user.getLevel()+"\t");
		}
		System.out.println("****************************************************");
		System.out.print("\n");
		System.out.println("管理员信息");
        System.out.println("****************************************************");
        AdministratorDao administratorDao = new AdministratorDaoImpl();
        List<Administrator> administratorList = administratorDao.getAllAdministrator();
        System.out.println("序号\t" + "管理员名称\t");
        for (int i=0;i<administratorList.size();i++) {
        	Administrator administrator = administratorList.get(i);
	        System.out.println((i + 1) +"\t"+ administrator.getName()+"\t");
        }
        System.out.println("****************************************************");
        System.out.print("\n");
        Scanner input = new Scanner(System.in);
        System.out.println("请选择输入登录模式，输入1为用户登录，输入2为管理员登录");
    	boolean type = true;
		String num;
		while (type) {
			num = input.next();
			if ("1".equals(num)) {
				Main.userLogin();
				type = false;
			} else if ("2".equals(num)) {
				Main.administratorLogin();
				type = false;
			} else {
				System.out.println("输入有误，请按照指定规则输入");
				System.out.println("请选择登录模式，输入1为用户登录，输入2为管理员登录");
				type = true;
			}
		}
	}

	//用户登录
	private static User userLogin() {
		Scanner input = new Scanner(System.in);
		UserService userservice = new UserServiceImpl();
		User user = userservice.login();
			System.out.println("您已登录成功，可以进行如下操作");
			UserChoose(user);
		return user;	
	}

	//创建用户
	private static User createFlowerStore(User user) {
		UserFactory userFactory = new UserFactoryImpl();
		userFactory.createUser();
		IsUserLogOut(user);//是否继续操作
		return user;//返回
	}

	private static void IsUserLogOut(User user) {
		System.out.println("您是否继续其它操作若是请输入y,退出请按任意键");
		Scanner input = new Scanner(System.in);
		String code=input.next();
		if(code.equals("y")){
			UserChoose( user);
		}
		else{
			System.out.println("您已成功退出系统");
		}
	}

	private static void UserChoose(User user) {
		//System.out.println("1：查询所有书籍");
		System.out.println("1：查询书籍");
		System.out.println("2：借书");
		System.out.println("3：还书");
		System.out.println("4：查询历史借阅记录");
		System.out.println("请根据需要执行的操作，选择序号输入，退出请输入0");
		Scanner input = new Scanner(System.in);
		boolean type = true;
		while (type) {
			int num = input.nextInt();
			switch (num) {
			case 0:
				System.out.println("退出成功");
				type = false;
				break;
			case 1:
				Main.bookList(user);
				type = false;
				break;
			case 2:
				Main.lendBook(user);
				type = false;
				break;
			case 3:
				Main.returnBook(user);
				type = false;
				break;
			case 4:
				Main.historyList(user);
				type = false;
				break;
			default:
				System.out.println("输入有误,请重新输入");
				type = true;
				break;
			}
		}
	}
	
	private static void bookList(User user) {
		System.out.println("请输入要查询的书籍名");
		Scanner input = new Scanner(System.in);
		String bname=input.nextLine();
		UserService us = new UserServiceImpl();
		List<Book> bookList = us.select(bname);
		System.out.println("序号\t" + "图书名称\t"+"图书状态\t");
		for (int i = 0; i < bookList.size(); i++) {
			Book book = bookList.get(i);
			System.out.println(book.getId()+"\t"+ book.getName()+"\t"+book.getState()+"\t");
		   }
		IsUserLogOut(user);
	}
	
	private static void historyList(User user) {
		UserService us = new UserServiceImpl();
		List<History> historyList = us.mybook(user.getName());
		System.out.println("图书ID\t" + "用户名\t"+"图书名称\t"+"借阅时间\t"+"归还截止时间\t"+"归还时间\t");
		for (int i = 0; i < historyList.size(); i++) {
			History history = historyList.get(i);
			System.out.println(history.getBid()+"\t"+ history.getBname()+"\t"+history.getLendtime()+"\t"
					+history.getDdl()+"\t"+history.getReturntime()+"\t");
		   }
		IsUserLogOut(user);
	}
	
	private static void lendBook(User user) {
		Scanner input = new Scanner(System.in);
		UserService us = new UserServiceImpl();
		boolean type = true;
		while (type) {
			us.lend(user);
			System.out.println("是否继续借阅1.是输入'y'  2.否输入'n'");
			String code = input.next();
			if(code.equals("n")) {
				type = false;// 标识符更改为false，退出系统
			}
		}
		IsUserLogOut(user);
	}
	
	private static void returnBook(User user) {
		Scanner input = new Scanner(System.in);
		UserService us = new UserServiceImpl();
		boolean type = true;
		while (type) {
			us.returnbook(user);
			System.out.println("是否继续归还1.是输入'y'  2.否输入'n'");
			String code = input.next();
			if(code.equals("n")) {
				type = false;// 标识符更改为false，退出系统
			}
		}
		IsUserLogOut(user);
	}
	
	//管理员登录
	private static Administrator administratorLogin() {
		Scanner input = new Scanner(System.in);
		Administrator administrator=new Administrator();
		AdministratorService administratorService=new AdministratorServiceImpl();
		administrator=administratorService.login();
		if(administrator.getName()!=null)
			System.out.println("您已登录成功，可以进行如下操作");
			AdministratorChoose(administrator);
			return administrator;
		else
			return null;
		}

	//管理员菜单
	private static void AdministratorChoose(Administrator administrator) {
		System.out.println("1：查询所有用户");
		System.out.println("2：查询指定用户");
		System.out.println("3：书籍入库");
		System.out.println("4：书籍出库");
		System.out.println("5：查询历史借阅记录");
		System.out.println("6；增添用户");
		System.out.println("请根据需要执行的操作，选择序号输入，退出请输入0");
		Scanner input = new Scanner(System.in);
		boolean type = true;
		while (type) {
			int num = input.nextInt();
			switch (num) {
				case 0:
					System.out.println("退出成功");
					type = false;
					break;
				case 1:
					Main.listUser();
					type = false;
					break;
				case 2:
					Main.selectUser();
					type = false;
					break;
				case 3:
					Main.addBook();
					type = false;
					break;
				case 4:
					Main.delBook();
					type = false;
					break;
				case 5:
					Main.listHistory();
					type = false;
					break;
				case 6:
					Main.addUser();
					type = false;
					break;
				default:
					System.out.println("输入有误,请重新输入");
					type = true;
					break;
			}
		}
	}
	public static void listUser(){

	}
	public static void selectUser(){

	}
	public static void addBook(){

	}
	public static void delBook(){

	}
	public static void listHistory(){

	}
	public static void addUser(){

	}

}
