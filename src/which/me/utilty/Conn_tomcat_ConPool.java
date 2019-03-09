package which.me.utilty;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Conn_tomcat_ConPool {

	public static Connection getCon(){
		Connection con = null;
		try {
			Context c = new InitialContext();
			DataSource ds = (DataSource) c.lookup("java:/comp/env/myshop");
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
