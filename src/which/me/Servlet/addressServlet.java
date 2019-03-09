package which.me.Servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import which.me.Bean.addressBean;
import which.me.Dao.addressDao;
import which.me.Dao.usersDao;
import which.me.utilty.responseUtil;

/**
 * Servlet implementation class addressServlet
 */
@WebServlet("/addressServlet")
public class addressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addressServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("收到请求");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String MethodName = request.getServletPath();
		MethodName = MethodName.substring(1, MethodName.length() - 8);
		System.out.println("MethodName : " + MethodName);
		try {
			Method method = getClass().getDeclaredMethod(MethodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 设置默认地址
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void setDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int addressId = Integer.parseInt(request.getParameter("id"));
		String username = (String)request.getSession().getAttribute("username");
		if(username == null){
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		int userId = usersDao.nameIsId(username);
		addressDao.xgzt(userId);
		int i = addressDao.setDefeat(userId, addressId);
		JSONObject json = new JSONObject();
		if(i!=0){
			json.put("success", true);
		}else{
			json.put("error", true);
		}
		responseUtil.write(response, json);
		
	}
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int addressId = Integer.parseInt(request.getParameter("id"));
		int i = addressDao.del(addressId);
		JSONObject json = new JSONObject();
		if(i!=0){
			json.put("success", true);
		}else{
			json.put("error", true);
		}
		responseUtil.write(response, json);
	}
	/**
	 * 添加地址
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("- 地址添加 -");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String area = request.getParameter("area");
		String address = request.getParameter("posi");
		String phone = request.getParameter("pho");
		String username = request.getParameter("rel"); //填写的收货人
		String msg = request.getParameter("msg"); //状态
		
		String uname = request.getParameter("userName");
		if(uname==null){
			uname = (String)request.getSession().getAttribute("username");
			if(uname==null){
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
		System.out.println("获取的名字为："+uname);
		int userId = usersDao.nameIsId(uname); //根据用户昵称获取用户ID
		if(msg.equals("1")){
			//根据userId条件  去除其他的默认值
			addressDao.xgzt(userId);
		}
		
		System.out.println("根据用户名字查ID："+userId);
		
		addressBean bean = new addressBean(province, city, area, address, phone, username, Integer.parseInt(msg), userId);
		int id = addressDao.add(bean);
		JSONObject result=new JSONObject();
		System.out.println("返回的ID是："+id);
		
		if(id!=0){
			result.put("success", "true");
			result.put("AddressId", id);
		}else{
			result.put("errorMsg", "添加失败");
		}
		responseUtil.write(response, result);
	}

}
