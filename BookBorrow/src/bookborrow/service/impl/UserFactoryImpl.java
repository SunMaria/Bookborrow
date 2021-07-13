package bookborrow.service.impl;
import java.util.*;
import bookborrow.service.*;
import bookborrow.dao.*;
import bookborrow.dao.impl.*;

public class UserFactoryImpl implements UserFactory {

	@Override
	public void createUser() {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		System.out.println("****输入创建用户的信息****");
		System.out.println("请输入用户名");
		String name=input.nextLine();
		System.out.println("请输入用户密码");
		String password=input.nextLine();
		String sql="insert into user('name','password') value(?,?,?)";
		Object[] param = {name,password};
		UserDao userdao=new UserDaoImpl();
		int count=userdao.updateUser(sql, param);
		if(count>0) {
			System.out.println("你已经成功创建一个用户用户名为"+name);
		}
	}

}
