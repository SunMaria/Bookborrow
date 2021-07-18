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
		Main.startBookBorroew();
	}
	
	private static void startBookBorroew() {
		System.out.println("----------------------社区图书借阅管理系统启动-----------------");
		System.out.println("图书信息");
		System.out.println("****************************************************");
		BookDao bookDao = new BookDaoImpl();
		List<Book> bookList = bookDao.getAllBook();
		System.out.println("序号\t"+ "价格\t"+ "图书名称\t"+"图书类型\t"+"图书状态\t");
		for (int i = 0; i < bookList.size(); i++) {
			Book book = bookList.get(i);
			System.out.println(book.getId()+"\t"+ book.getPrice()+"\t"+book.getName()+"\t"+
					book.getType()+"\t"+book.getState()+"\t");
		   }
		System.out.println("****************************************************");
        System.out.print("\n");
        System.out.println("用户信息");
        UserDao userDao = new UserDaoImpl();
		List<User> userList = userDao.getAllUser();
		System.out.println("****************************************************");
		System.out.println("序号\t" + "用户名\t"+"用户电话\t" +"用户等级\t"+"用户保证金\t");
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			System.out.println((i + 1) +"\t"+ user.getName()+"\t"+user.getPhone()+"\t"+
			user.getLevel()+"\t"+user.getMoney()+"\t");
		}
		System.out.println("****************************************************");
		System.out.print("\n");
		System.out.println("管理员信息");
        System.out.println("****************************************************");
        AdministratorDao administratorDao = new AdministratorDaoImpl();
        List<Administrator> administratorList = administratorDao.getAllAdministrator();
        System.out.println("序号\t" + "管理员名称\t"+ "管理员电话\t");
        for (int i=0;i<administratorList.size();i++) {
        	Administrator administrator = administratorList.get(i);
	        System.out.println((i + 1) +"\t"+ administrator.getName()+"\t"+ administrator.getPhone()+"\t");
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

	private static User userLogin() {
		Scanner input = new Scanner(System.in);
		UserService userservice = new UserServiceImpl();
		User user = new User();
		boolean type = true;
		while (type) {
			User user1 = userservice.login();
			if (user1.getName() != null) {
				System.out.println("可进行如下操作:");
				user=user1;
			    UserChoose(user);
				type = false;
			} else {
				System.out.println("登录失败,请重新登录");
				type = true;
			}
		}
		return user;	
	}

	private static User createUser(User user) {
		UserFactory userFactory = new UserFactoryImpl();
		userFactory.createUser();
		IsUserLogOut(user);//是否继续操作
		return user;
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
		System.out.println("1：查询所有书籍");
		System.out.println("2：按名称查询书籍");
		System.out.println("3：按编号查询书籍");
		System.out.println("4：按类型查询书籍");
		System.out.println("5：借书");
		System.out.println("6：还书");
		System.out.println("7：查询历史借阅记录");
		System.out.println("8：修改密码");
		System.out.println("9：充值");
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
					Main.allList(user);
					type = false;
					break;
			case 2:
				Main.bookList(user);
				type = false;
				break;
			case 3:
				Main.idList(user);
				type = false;
				break;
			case 4:
				Main.typeList(user);
				type = false;
				break;
			case 5:
				Main.lendBook(user);
				type = false;
				break;
			case 6:
				Main.returnBook(user);
				type = false;
				break;
			case 7:
				Main.historyList(user);
				type = false;
				break;
			case 8:
				Main.change(user);
				type = false;
				break;
			case 9:
				Main.userrecharge(user);
				type = false;
				break;
			default:
				System.out.println("输入有误,请重新输入");
				type = true;
				break;
			}
		}
	}
	private static void userrecharge(User user) {
		UserService us = new UserServiceImpl();
		us.recharge(user);	
		IsUserLogOut(user);
	}
	private static void change(User user) {
		UserService us = new UserServiceImpl();
		Scanner input = new Scanner(System.in);
		System.out.println("请输入旧密码");
		String password=input.nextLine();
		us.passwordchange(user,password);	
		IsUserLogOut(user);
	}
	private static void allList(User user){
		BookDao bookDao = new BookDaoImpl();
		List<Book> bookList = bookDao.getAllBook();
		System.out.println("序号\t"+ "价格\t"+ "图书名称\t"+"图书类型\t"+"图书状态\t");
		for (int i = 0; i < bookList.size(); i++) {
			Book book = bookList.get(i);
			System.out.println(book.getId()+"\t"+ book.getPrice()+"\t"+book.getName()+"\t"+
					book.getType()+"\t"+book.getState()+"\t");
		}
		IsUserLogOut(user);
	}
	private static void idList(User user) {
		System.out.println("请输入要查询的书籍编号");
		Scanner input = new Scanner(System.in);
		int id=input.nextInt();
		UserService us = new UserServiceImpl();
		Book book= us.selectid(id);
		if(book.getName().equals(null)) {
			System.out.println("书籍不存在");
		}else {
			System.out.println("序号\t"+ "价格\t"+ "图书名称\t"+"图书类型\t"+"图书状态\t");
	        System.out.println(book.getId()+"\t"+ book.getPrice()+"\t"+book.getName()+"\t"+
					book.getType()+"\t"+book.getState()+"\t");
		}
		IsUserLogOut(user);
	}
	private static void typeList(User user) {
		System.out.println("请输入要查询的书籍类型");
		Scanner input = new Scanner(System.in);
		String btype=input.nextLine();
		UserService us = new UserServiceImpl();
		List<Book> bookList = us.selecttype(btype);
		System.out.println("序号\t"+ "价格\t"+ "图书名称\t"+"图书类型\t"+"图书状态\t");
		int i=0;
		for (i = 0; i < bookList.size(); i++) {
			Book book = bookList.get(i);
			System.out.println(book.getId()+"\t"+ book.getPrice()+"\t"+book.getName()+"\t"+
					book.getType()+"\t"+book.getState()+"\t");
		   }
		if(i==0) {
			System.out.println("类型输入错误或没有此类型");
		}
		IsUserLogOut(user);
	}
	
	private static void bookList(User user) {
		System.out.println("请输入要查询的书籍名");
		Scanner input = new Scanner(System.in);
		String bname=input.nextLine();
		UserService us = new UserServiceImpl();
		List<Book> bookList = us.select(bname);
		System.out.println("序号\t"+ "价格\t"+ "图书名称\t"+"图书类型\t"+"图书状态\t");
		int i=0;
		for (i = 0; i < bookList.size(); i++) {
			Book book = bookList.get(i);
			System.out.println(book.getId()+"\t"+ book.getPrice()+"\t"+book.getName()+"\t"+
					book.getType()+"\t"+book.getState()+"\t");
		   }
		if(i==0) {
			System.out.println("书名输入错误或没有此书");
		}
		IsUserLogOut(user);
	}
	
	private static void historyList(User user) {
		UserService us = new UserServiceImpl();
		List<History> historyList = us.mybook(user.getName());
		System.out.println("图书ID\t" + "用户名\t"+"图书名称\t"+"借阅时间\t"+"\t归还截止时间\t"+"归还时间\t");
		for (int i = 0; i < historyList.size(); i++) {
			History history = historyList.get(i);
			if(history.getReturntime().equals(null)) {
				System.out.println(history.getBid()+"\t"+history.getUname()+"\t"+ history.getBname()+
						"\t"+history.getLendtime()+"\t"+history.getDdl()+"\t"+history.getReturntime()+"\t");
			}else {
				System.out.println(history.getBid()+"\t"+history.getUname()+"\t"+ history.getBname()+
						"\t"+history.getLendtime()+"\t"+history.getDdl()+"\t"+history.getReturntime()+"\t");
			}
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
			boolean type = true;
			while (type) {
				Administrator ad=administratorService.login();
				if (ad.getName() != null) {
					System.out.println("您已登录成功，可以进行如下操作");
					administrator=ad;
					AdministratorChoose(administrator);
					type = false;
				} else {
					System.out.println("登录失败,请重新登录");
					type = true;
				}
			}
			return administrator;
		}

		//管理员菜单
		private static void AdministratorChoose(Administrator administrator) {
			System.out.println("1：查询所有用户");
			System.out.println("2：查询指定用户");
			System.out.println("3：查询书籍");
			System.out.println("4：书籍入库");
			System.out.println("5：书籍出库");
			System.out.println("6：查询历史借阅记录");
			System.out.println("7；增添用户");
			System.out.println("8；删除用户");
			System.out.println("9；删除历史记录");
			System.out.println("10；修改用户信息");
			System.out.println("11；修改书籍信息");
			System.out.println("12；检测书籍");
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
						Main.listUser(administrator);
						type = false;
						break;
					case 2:
						Main.selectUser(administrator);
						type = false;
						break;
					case 3:
						Main.listBook(administrator);
						type = false;
						break;
					case 4:
						Main.addBook(administrator);
						type = false;
						break;
					case 5:
						Main.delBook(administrator);
						type = false;
						break;
					case 6:
						Main.listHistory(administrator);
						type = false;
						break;
					case 7:
						Main.addUser(administrator);
						type = false;
						break;
					case 8:
						Main.delUser(administrator);
						type = false;
						break;
					case 9:
						Main.delHistory(administrator);
						type = false;
						break;
					case 10:
						Main.changeUser(administrator);
						type = false;
						break;
					case 11:
						Main.changeBook(administrator);
						type = false;
						break;
					case 12:
						Main.checkBook(administrator);
						type = false;
						break;
					default:
						System.out.println("输入有误,请重新输入");
						type = true;
						break;
				}
			}
		}
		
		private static void checkBook(Administrator administrator){
			AdministratorService as=new AdministratorServiceImpl();
			boolean flag=as.check();
			if(!flag) {
				System.out.println("书籍没问题！");
			}
			IsAdministratorLogOut(administrator);
		}
		private static void listBook(Administrator administrator){
			BookDao bookDao = new BookDaoImpl();
			List<Book> bookList = bookDao.getAllBook();
			System.out.println("序号\t"+ "价格\t"+ "图书名称\t"+"图书类型\t"+"图书状态\t");
			for (int i = 0; i < bookList.size(); i++) {
				Book book = bookList.get(i);
				System.out.println(book.getId()+"\t"+ book.getPrice()+"\t"+book.getName()+"\t"+
						book.getType()+"\t"+book.getState()+"\t");
			}
			IsAdministratorLogOut(administrator);
		}
		private static void changeUser(Administrator administrator){
			AdministratorService administratorService=new AdministratorServiceImpl();
			boolean flag=administratorService.change_user();
			IsAdministratorLogOut(administrator);
		}
		private static void changeBook(Administrator administrator){
			AdministratorService administratorService=new AdministratorServiceImpl();
			boolean flag=administratorService.change_book();
			IsAdministratorLogOut(administrator);
		}
		private static void listUser(Administrator administrator){
			List<User> userList;
			AdministratorService administratorService=new AdministratorServiceImpl();
			userList=administratorService.list_user();
			System.out.println("序号\t" + "用户名\t"+"用户电话\t" +"用户等级\t"+"用户保证金\t");
			for (int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				System.out.println((i + 1) +"\t"+ user.getName()+"\t"+user.getPhone()+"\t"+
				user.getLevel()+"\t"+user.getMoney()+"\t");
			}
			IsAdministratorLogOut(administrator);
		}

		private static void selectUser(Administrator administrator){
			List<User> userList;
			Scanner input=new Scanner(System.in);
			AdministratorService administratorService=new AdministratorServiceImpl();
			System.out.println("输入想查询的用户名");
			String name=input.next();
			userList=administratorService.select_user(name);
			System.out.println("序号\t" + "用户名\t"+"用户电话\t" +"用户等级\t"+"用户保证金\t");
			int i=0;
			for (i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				System.out.println((i + 1) +"\t"+ user.getName()+"\t"+user.getPhone()+"\t"+
				user.getLevel()+"\t"+user.getMoney()+"\t");
			}
			if(i==0) {
				System.out.println("该用户不存在");
			}
			IsAdministratorLogOut(administrator);
		}

		private static void addBook(Administrator administrator){
			AdministratorService administratorService=new AdministratorServiceImpl();
			if (!administratorService.add_book()) {
				System.out.println("插入失败");
			}
			IsAdministratorLogOut(administrator);
		}

		private static void delBook(Administrator administrator){
			AdministratorService administratorService=new AdministratorServiceImpl();
			boolean flag=administratorService.delete_book();
			IsAdministratorLogOut(administrator);
		}
		
		private static void delHistory(Administrator administrator){
			AdministratorService administratorService=new AdministratorServiceImpl();
			boolean flag=administratorService.delete_history();
			if(flag) {
				System.out.println("成功删除！");
			}else {
				System.out.println("删除失败！");
			}
			IsAdministratorLogOut(administrator);
		}
		
		private static void delUser(Administrator administrator){
			AdministratorService administratorService=new AdministratorServiceImpl();
			boolean flag=administratorService.delete_user();
			IsAdministratorLogOut(administrator);
		}
		
		private static void listHistory(Administrator administrator){
			AdministratorService administratorService=new AdministratorServiceImpl();
			List<History> historyList=administratorService.list_history();
			System.out.println("图书ID\t" + "用户名\t"+"图书名称\t"+"借阅时间\t"+"归还截止时间\t"+"归还时间\t");
			for (int i = 0; i < historyList.size(); i++) {
				History history = historyList.get(i);
				System.out.println(history.getBid()+"\t"+ history.getBname()+"\t"+history.getLendtime()+"\t"
						+history.getDdl()+"\t"+history.getReturntime()+"\t");
			}
			IsAdministratorLogOut(administrator);
		}

		private static void addUser(Administrator administrator){
			AdministratorService administratorService=new AdministratorServiceImpl();
			boolean flag=administratorService.add_user();
			if(!flag) {
				System.out.println("添加失败！");
			}
			IsAdministratorLogOut(administrator);
		}

		//管理员退出登陆
		private static void IsAdministratorLogOut(Administrator administrator) {
			System.out.println("您是否继续其它操作若是请输入y,退出请按任意键");
			Scanner input = new Scanner(System.in);
			String code=input.next();
			if(code.equals("y")){
				AdministratorChoose(administrator);
			}
			else{
				System.out.println("您已成功退出系统");
			}
		}
}
