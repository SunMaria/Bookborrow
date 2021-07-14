package bookborrow.service.impl;

import bookborrow.entity.User;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
                UserServiceImpl u=new UserServiceImpl();
		User u1=new User();
		u1=u.login();
		System.out.println(u1.getName());
		System.out.println(u1.getLevel());
		//u.lend(u1);
		u.returnbook(u1);
		System.out.println(u1.getLevel());
		
		//u.mybook("aa");
		//u.returnbook(u1);
		/*List<Book> booklist = new ArrayList<Book>();
		booklist=u.list();
		for(int i=0;i<booklist.size();i++) {
			System.out.println(booklist.get(i).getName());
		}*/
	}

}
