package bookborrow.entity;
import java.util.*;
public class History {
	int bid;
	String uname;
	String bname;
	String lendtime;
	String ddl;//应当归还时间
    String returntime;//实际归还时间
	
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getDdl() {
		return ddl;
	}
	public void setDdl(String ddl) {
		this.ddl = ddl;
	}
	public String getLendtime() {
		return lendtime;
	}
	public void setLendtime(String lendtime) {
		this.lendtime = lendtime;
	}
	public String getReturntime() {
		return returntime;
	}
	public void setReturntime(String returntime) {
		this.returntime = returntime;
	}
	
}
