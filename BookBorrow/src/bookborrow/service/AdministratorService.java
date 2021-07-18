package bookborrow.service;

import bookborrow.entity.Administrator;
import bookborrow.entity.History;
import bookborrow.entity.User;
import java.util.List;

public interface AdministratorService extends ListBookable,SelectBookable{
	public Administrator login();
	public List<User> list_user();
	public List<User> select_user(String name);
	public boolean add_book();
	public boolean add_user();
	public boolean change_user();
	public boolean change_book();
	public List<History> list_history();
	public boolean delete_book();
	public boolean delete_user();
	public boolean delete_history();
	public boolean check();
}
