package bookborrow.service.impl;

import bookborrow.dao.UserDao;
import bookborrow.dao.impl.AdministratorDaoImpl;
import bookborrow.dao.impl.UserDaoImpl;
import bookborrow.service.AdministratorFactory;

import java.util.Scanner;

public class AdministratorFactoryImpl implements AdministratorFactory {

    @Override
    public void createAdministrator() {
        Scanner input = new Scanner(System.in);
        System.out.println("****���봴������Ա����Ϣ****");
        System.out.println("���������Ա����");
        String name=input.nextLine();
        System.out.println("���������Ա����");
        String password=input.nextLine();
        String sql="insert into administrator('name','password') value(?,?,?)";
        Object[] param = {name,password};
        AdministratorDaoImpl administratordao=new AdministratorDaoImpl();
        int count=administratordao.updateAdministrator(sql,param);
        if(count>0) {
            System.out.println("���Ѿ��ɹ�����һ������Ա������Ա��Ϊ"+name);
        }
    }
}
