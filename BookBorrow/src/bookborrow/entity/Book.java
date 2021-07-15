package bookborrow.entity;

public class Book {
	int id;
	String name;
	String state;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Book() {}
	public Book(int id,String name,String state) {
		this.id=id;
		this.name=name;
		this.state=state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
