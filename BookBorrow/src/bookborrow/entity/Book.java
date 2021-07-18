package bookborrow.entity;

public class Book {
	int id;
	int price;
	String name;
	String state;
	String type;
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
	public Book(int id,int price,String name,String type,String state) {
		this.id=id;
		this.price=price;
		this.type=type;
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
