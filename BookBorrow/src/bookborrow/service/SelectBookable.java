package bookborrow.service;
import java.util.List;
import bookborrow.entity.*;

public interface SelectBookable {
	public List<Book> select(String bookname);
}
