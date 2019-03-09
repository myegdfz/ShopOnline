package which.me.Servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.IIOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import which.me.Bean.goodsBean;
import which.me.Bean.shoppingCart;
import which.me.Bean.userBean;
import which.me.Dao.shoppingCartDao;
import which.me.Dao.usersDao;
import which.me.utilty.responseUtil;

/**
 * Servlet implementation class userServlet
 */
@WebServlet("/userServlet")
public class userServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("收到登录请求");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String MethodName = request.getServletPath();
		MethodName = MethodName.substring(1, MethodName.length()-5);
		System.out.println("MethodName: " + MethodName);
		try {
			Method method = getClass().getDeclaredMethod(MethodName,new Class[] { HttpServletRequest.class, HttpServletResponse.class});
			method.invoke(this, new Object[] { request, response });
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 查询所有 and 模糊搜索
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IIOException{
		String p = request.getParameter("page");		//需求页码
		String rows = request.getParameter("rows");		//每页条数
		String sel = request.getParameter("s_userName");//查询不为空
		System.out.println("Request : " + p+" " + rows+ " " + sel);
		if(sel==null) {
			JSONObject result = new JSONObject();
			String sql = "select count(*) count from t_user";
			int count = usersDao.count(sql);			//条数
		
			JSONArray jsonArray = usersDao.selAll(Integer.parseInt(p), Integer.parseInt(rows));
			//获取JSON 集合
			result.put("rows", jsonArray);
			result.put("total", count);
			responseUtil.write(response, result);
		}else {
			JSONObject result = new JSONObject();
			String sql = "select count(*) count from t_user where userName like '%"+sel+"%'";
			System.out.println("查询行数sql为："+sql);
			int count = usersDao.count(sql); //获取条数
			JSONArray jsonArray = usersDao.nameSel(Integer.parseInt(p), Integer.parseInt(rows),sel); //获取dao返回的json集合
			result.put("rows", jsonArray);
			result.put("total", count);
			responseUtil.write(response, result);
		}
	}
	/**
	 * 单个删除  / 多个删除
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String id = request.getParameter("ids");
		int s = id.indexOf(","), isok = 0;
		if(s!=-1) 
			isok = usersDao.manyDel(id);
		else 
			isok = usersDao.del(Integer.parseInt(id));
		
		System.out.println("Receive ："+id);
		JSONObject result=new JSONObject();
		if(isok == 0)
			result.put("errorMsg", "删除失败");
		else
			result.put("success", "true");
		responseUtil.write(response, result);
	}
	/**
	 * 添加用户
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IIOException
	 */
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IIOException {
		String userName = request.getParameter("user.userName");
		String trueName = request.getParameter("user.trueName");
		String sex = request.getParameter("user.sex");
		String birthday = request.getParameter("user.birthday");
		String statusID = request.getParameter("user.statusID");
		String phone = request.getParameter("user.phone");
		String address = request.getParameter("user.address");
		String email = request.getParameter("user.email");
		String password = request.getParameter("user.password");
		System.out.println("[添加用户接收到："+userName+trueName+sex+birthday+statusID+phone+address+email+ password+"]");
		
		userBean u = new userBean(userName, trueName, sex, birthday, statusID, phone, address, email, 1+"", password);
		int i = usersDao.add(u);
		JSONObject result=new JSONObject();
		if(i==0){
			result.put("errorMsg", "删除失败");
		}else{
			result.put("success", "true");
		}
		responseUtil.write(response, result);
	}
	/**
	 * 信息更新
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IIOException
	 */
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IIOException {
		
		String userName = request.getParameter("user.userName");
		String trueName = request.getParameter("user.trueName");
		String sex = request.getParameter("user.sex");
		String birthday = request.getParameter("user.birthday");
		String statusID = request.getParameter("user.statusID");
		String phone = request.getParameter("user.phone");
		String address = request.getParameter("user.address");
		String email = request.getParameter("user.email");
		String password = request.getParameter("user.password");
		
		int id = Integer.parseInt(request.getParameter("user.id"));
		userBean u = new userBean(userName, trueName, sex, birthday, statusID, phone, address, email, 1+"", password);
		
		int isok = usersDao.update(u);
		JSONObject jsonObject = new JSONObject();
		if(isok == 0)
			jsonObject.put("errorMsg", "删除失败");
		else jsonObject.put("success", "true");
		responseUtil.write(response, jsonObject);
	}
	public void rege(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		System.out.println("接收到要注册的用户名为："+name);
		int i = usersDao.count("select count(*) count from t_user where userName = '"+name+"'");
		JSONObject result=new JSONObject();
		if(i==0){
			result.put("success", "true");
		}else{
			result.put("success", "false");
		}
		responseUtil.write(response, result);
		
		
	}
	/**
	 * 注册用户
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void regeuser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		userBean u = new userBean();
		u.setUserName(username);u.setPassword(password);
		int i = usersDao.adduser(u);
		if(i==1){
			//注册成功
			request.setAttribute("regeState", 1);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else{
			//注册失败
			request.setAttribute("regeState", 2);
			request.getRequestDispatcher("rege.jsp").forward(request, response);
		}
		
	}
	/**
	 * ajax 验证码校验
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void yzm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jyzm = request.getParameter("jyzm"); //接收到的验证码
		String yz = which.me.utilty.yzm.getImg(request, response);
		JSONObject result=new JSONObject();
		if(jyzm.equalsIgnoreCase(yz)){
			result.put("yzm", "true");
		}else{
			result.put("yzm", "false");
		}
		responseUtil.write(response, result);
	}
	/**
	 * 管理员登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void adminLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jyzm = request.getParameter("jyzm"); //接收到的验证码
		String name = request.getParameter("username");
		System.out.println(name);
		String password = request.getParameter("password");
		String yz = which.me.utilty.yzm.getImg(request, response);
		if(jyzm.equalsIgnoreCase(yz)){
			//System.out.println("验证码验证通过");
			//验证码正确
			String password2 = usersDao.adminLogin(name);
			System.out.println("接收到的名字："+name);
			System.out.println("接收的密码为："+password);
			System.out.println("返回的密码为："+password2);
			if(password.equals(password2)){
				//密码正确
				request.getSession().setAttribute("stateOK", 0);
				response.sendRedirect("admin/main.jsp");
				request.getSession().setAttribute("adminName", name);
			}else{
				//密码错误 或者非管理员
				request.getSession().setAttribute("state", 2);
				response.sendRedirect("/zo_omyshop/admin");
			}
		}else{
			//验证码错误
			request.getSession().setAttribute("state", 1);
			response.sendRedirect("/zo_omyshop/admin");
		}
		
		
	}
	/**
	 * 管理员修改密码
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void adminXgmm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("adminName");
		int userId = usersDao.nameIsId(username); //获取用户ID
		String old = request.getParameter("oldPassword");
		String password = request.getParameter("newPassword");
		System.out.println("管理员修改密码："+userId+old+password);
		
		int i = 0;
		i = usersDao.changePassword(userId, old, password);
		JSONObject result = new JSONObject();
		if(i==0){
			//密码修改失败
			result.put("error", true);
		}else{
			//修改成功
			result.put("success", true);
		}
		responseUtil.write(response, result);
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("进入登陆");
		String username = request.getParameter("username"); 
		String password = request.getParameter("password"); 
		JSONObject result=new JSONObject();
		String password2 = usersDao.login(username);
		if(password2==null){
			result.put("info", "用户不存在！");
			result.put("status", 0);
			result.put("url", "");
		}else{
			if(password2.equals(password)){
				result.put("info", "登陆成功");
				result.put("status", 1); //1则成功登陆
				result.put("url", ""); 
				request.getSession().setAttribute("username", username);
				HashMap<String, goodsBean> gwc = (HashMap)request.getSession().getAttribute("gwc");
				int userId = usersDao.nameIsId(username); //获取用户ID
				//判断未登录之前购物车是否有购物项
				int gwcSize = gwc.size();
				if(gwcSize>0){
					Set keyList = gwc.keySet();
					Iterator it = keyList.iterator();
					
					while(it.hasNext()){
						String hid = (String)it.next();
						goodsBean hgoods = gwc.get(hid);
						int i = shoppingCartDao.count(userId, hgoods.getId());
						if(i>0){
							//存在购物项  增加数量
							shoppingCartDao.updateNum(new shoppingCart(userId, hgoods.getId(), hgoods.getNum(), 0));
						}else{
							//不存在  添加购物项
							shoppingCartDao.add(new shoppingCart(userId, hgoods.getId(), hgoods.getNum(), hgoods.getPrice()));
						}
					}
					gwc.clear(); //清空当前session的购物车
				}
			}else{
					result.put("info", "输入的密码有误");
					result.put("status", 0);
					result.put("url", "");
				}
		}
		responseUtil.write(response, result);
	}
	public void mainLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Main进入登陆");
		String username = request.getParameter("username"); //接收到的用户名
		String password = request.getParameter("password"); 
		String password2 = usersDao.login(username);
		System.out.println("++ : " + username +" " + password + " " + password2);
		if(password2.equals(password)){
			//登录成功
			request.getSession().setAttribute("username", username);
			HashMap<String, goodsBean> gwc = (HashMap)request.getSession().getAttribute("gwc");
			int userId = usersDao.nameIsId(username); //获取用户ID
			//判断未登录之前购物车是否有购物项
			int gwcSize = gwc.size();
			if(gwcSize>0){
				Set keyList = gwc.keySet();
				Iterator it = keyList.iterator();
				
				while(it.hasNext()){
					String hid = (String)it.next();
					goodsBean hgoods = gwc.get(hid);
					int i = shoppingCartDao.count(userId, hgoods.getId());
					if(i>0){
						//存在购物项  增加数量
						shoppingCartDao.updateNum(new shoppingCart(userId, hgoods.getId(), hgoods.getNum(), 0));
					}else{
						//不存在  添加购物项
						shoppingCartDao.add(new shoppingCart(userId, hgoods.getId(), hgoods.getNum(), hgoods.getPrice()));
					}
				}
				gwc.clear(); //清空当前session的购物车
			}
			response.sendRedirect("/zo_omyshop"); //重定向到首页
		}else{
			//登录失败密码输入有误
			request.getSession().setAttribute("loginFail", username);
			response.sendRedirect("login.jsp"); //重定向到登录
		}
	}
	/**
	 * 用户修改资料
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void userDataUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String trueName = request.getParameter("trueName");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String statusID = request.getParameter("statusID");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		int userId = Integer.parseInt(request.getParameter("uid"));
		
		String username = (String)request.getSession().getAttribute("username");
		int userId2 = usersDao.nameIsId(username); //获取用户ID
		
		if(userId!=userId2){
			//非法操作
			request.getSession().invalidate();
			response.sendRedirect("login.jsp"); 
		}
		userBean u = new userBean(null, trueName, sex, birthday, statusID, phone, null, email, null, null);
		u.setId(userId);
		int i = usersDao.userUpdate(u);
		JSONObject result = new JSONObject();
		if(i!=0){
			result.put("success", "true");
		}else{
			result.put("errorMsg", "更新失败");
		}
		responseUtil.write(response, result);
	}
	/**
	 * 用户修改密码
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void changePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("username");
		if(username==null){
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		int userId = usersDao.nameIsId(username); //获取用户ID
		String old = request.getParameter("old");
		String password = request.getParameter("password");
		
		int i = 0;
		i = usersDao.changePassword(userId, old, password);
		JSONObject reslt = new JSONObject();
		if(i==0){
			//密码修改失败
			reslt.put("error", true);
		}else{
			//修改成功
			reslt.put("success", true);
		}
		responseUtil.write(response, reslt);
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect("/zo_omyshop");
		
	}
}
