package bookborrow.service.impl;
import bookborrow.dao.impl.*;
import bookborrow.service.AdministratorFactory;
import java.util.Scanner;

public class AdministratorFactoryImpl implements AdministratorFactory{
	@Override
    public void createAdministrator() {
        Scanner input = new Scanner(System.in);
        System.out.println("****输入创建管理员的信息****");
        System.out.println("请输入管理员名称");
        String name=input.nextLine();
        System.out.println("请输入管理员密码");
        String password=input.nextLine();
        System.out.println("请输入管理员电话");
        String phone=input.nextLine();
        String sql="insert into administrator('name','password','phone') value(?,?,?)";
        Object[] param = {name,password,phone};
        AdministratorDaoImpl administratordao=new AdministratorDaoImpl();
        int count=administratordao.updateAdministrator(sql,param);
        if(count>0) {
            System.out.println("你已经成功创建一个管理员，管理员名为"+name);
        }
    }
}
