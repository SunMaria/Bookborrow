package bookborrow.service;
import bookborrow.entity.*;

public interface SelectBookable {
	public Book select(String bookname);
}
