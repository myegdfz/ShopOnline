package which.me.utilty;

import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.Test;
public class Conn {

	@Test
	public static Connection getCon() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/myshop?characterEncoding=utf8","root","");
			System.out.println(con);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return con;
	}
	public static void main(String[] args) {
		getCon();
	}
}
