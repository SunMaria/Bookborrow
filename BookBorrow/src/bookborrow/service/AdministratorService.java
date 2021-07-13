package bookborrow.service;

import bookborrow.entity.Administrator;
import bookborrow.entity.User;
import java.util.List;

public interface AdministratorService extends ListBookable,SelectBookable{
	public Administrator login();
	public List<User> list_user();
	public List<User> select_user();
	public void add_book();
	public void add_user();
	public void list_history();
	public void delete_book();
}
