package which.me.utilty;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class responseUtil {

	public static void write(HttpServletResponse response, Object o) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(o.toString());
			System.out.println("servlet响应为："+o.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
