package bookborrow.service;
import java.util.List;
import bookborrow.entity.*;


public interface UserService extends ListBookable,SelectBookable{
	public void lend(User user);
	public void returnbook(User user);
	public List<History> mybook(String uname);
	public User login();
	public void passwordchange(User user,String password);
	public void recharge(User user);
	public List<Book> selecttype(String booktype);
	public Book selectid(int id);
}
