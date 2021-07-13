package bookborrow.service;

public interface AdministratorService extends ListBookable,SelectBookable{
	public void login();
	public void list_user();
	public void select_user();
	public void add_book();
	public void add_user();
	public void list_history();
	public void delete_book();
}
