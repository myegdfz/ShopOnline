package which.me.Servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import which.me.Bean.addressBean;
import which.me.Bean.goodsBean;
import which.me.Bean.orderBean;
import which.me.Bean.orderItemBean;
import which.me.Dao.addressDao;
import which.me.Dao.bigTypeDao;
import which.me.Dao.goodsDao;
import which.me.Dao.orderDao;
import which.me.Dao.orderItemDao;
import which.me.Dao.usersDao;
import which.me.utilty.responseUtil;

/**
 * Servlet implementation class orderServlet
 */
@WebServlet("/orderServlet")
public class orderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public orderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String MethodName = request.getServletPath();
		System.out.println(MethodName);
		MethodName = MethodName.substring(1, MethodName.length()-6);
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
	 * 查询所有 and 模糊搜索
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("进入订单查询");
		String p = request.getParameter("page"); 														//需求页码
		String rows = request.getParameter("rows");														//每页多少条
		String sel = request.getParameter("oname");														//如果是查询这不为空
		System.out.println("收到请求："+p+"  "+rows+"  "+sel);
		if(sel==null){
			JSONObject result = new JSONObject();
			String sql = "select count(*) count from t_order";
			int count = orderDao.count(sql); 															//获取条数
			
			JSONArray jsonArray = orderDao.selAll(Integer.parseInt(p), Integer.parseInt(rows)); 		//获取dao返回的json集合
			
			result.put("rows", jsonArray);
			result.put("total", count);
			responseUtil.write(response, result);
		}else{
			JSONObject result = new JSONObject();
			String sql = "select count(*) count from t_goods where name like '%"+sel+"%'";
			
			int count = bigTypeDao.count(sql); 															//获取条数
			JSONArray jsonArray = goodsDao.nameSel(Integer.parseInt(p), Integer.parseInt(rows),sel); 	//获取dao返回的json集合
			result.put("rows", jsonArray);
			result.put("total", count);
			responseUtil.write(response, result);
		}
	}
	/**
	 * 提交订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void tjdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("提交订单 : ");
		String orderId = request.getParameter("tag");//订单编号
		String addressId = request.getParameter("sender");//地址ID
		String PayType = request.getParameter("PayType");//支付方式
		String liuyan = request.getParameter("liuyan");//留言信息
		System.out.println("Flag : " +orderId + " " + addressId + " " + PayType + " " + liuyan);
		List<orderItemBean> list = orderItemDao.orderIdSelItem(orderId);
		System.out.println("My Flat.");
		for(orderItemBean g:list){
			goodsDao.stockJian(g.getId(), g.getSum()); //减去库存
		}
		
		int i = orderDao.update(orderId, addressId, PayType, liuyan); 
		
		if(i==1){
			System.out.println("修改订单状态成功");
			request.setAttribute("orderState", 1);
			request.getRequestDispatcher("orderState.jsp").forward(request, response);
		}else{
			System.out.println("修改订单状态失败");
		}
	}
	/**
	 * 取消订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void qxdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("取消订单: ");
		String id = request.getParameter("orderId");
		String userName = (String)request.getSession().getAttribute("username");
		
		int userId = usersDao.nameIsId(userName);
		orderBean o = orderDao.orderIdSel(id);
		if(o.getUserId() == userId) { 
			orderDao.qxdd(id, 4);
			response.sendRedirect("userMain.page");
		}else {
			response.sendRedirect("userMain.page");
		}
	}
	/**
	 * 确定收货
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void qdsh(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderId = request.getParameter("orderId");
		String username = (String)request.getSession().getAttribute("username");
		int userId = usersDao.nameIsId(username); //根据用户名获取ID
		orderBean o = orderDao.orderIdSel(orderId);
		if(o.getUserId()==userId){
			//修改订单状态
			orderDao.qxdd(orderId, 5);
			response.sendRedirect("userMain.page");
		}else{
			//非法操作
			response.sendRedirect("userMain.page");
		}
	}
	/**
	 * 订单发货
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void ddfh(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderId = request.getParameter("orderNos");
		
		//修改订单状态
		orderDao.qxdd(orderId, 3);
		
		//success
		JSONObject result = new JSONObject();
		
		result.put("success", true);
		responseUtil.write(response, result);
		
	}
	/**
	 * 支付订单
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void zfdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("支付订单 ：");
		String orderId = request.getParameter("orderId");
		String username = (String)request.getSession().getAttribute("username");
		
		System.out.println("获取到的订单id为："+orderId);
		System.out.println("获取到的当前登录人为："+username);
		
		int userId = usersDao.nameIsId(username); //根据用户名获取ID
		
		orderBean o = orderDao.orderIdSel(orderId);
		if(o.getUserId()==userId){
			System.out.println("当前登录人为本人");
			DecimalFormat df = new DecimalFormat("#.00");  	//防止价格出现异常
			//查询订单信息
			List<orderItemBean> list = orderItemDao.orderIdSelItem(orderId);
			List<goodsBean> goodsList = new ArrayList<goodsBean>();
			for(orderItemBean oi : list){
				System.out.println("商品名称为："+oi.getGoodsName()+"商品ID为："+oi.getGoodsId());
				
				goodsBean hgoods = goodsDao.gwcGoodsIdSel(oi.getGoodsId());
				System.out.println("小计未格式化："+oi.getSum()*hgoods.getPrice());
				double hj = Double.parseDouble(df.format(oi.getSum()*hgoods.getPrice()));
				System.out.println(oi.getGoodsName()+"小计为："+hj);
				hgoods.setNum(oi.getSum());
				hgoods.setTotal(hj);
				goodsList.add(hgoods);
			}
			
			//根据用户ID获取地址信息
			List<addressBean> addressList= addressDao.selAll(userId); 
			
			request.setAttribute("addressList", addressList); //地址
			request.setAttribute("ze", o.getTotal());  //总额
			request.setAttribute("gwcGoodsList", goodsList);  //购物车中的商品
			request.setAttribute("addressId", orderId); //订单id
			request.getRequestDispatcher("order.jsp").forward(request, response);  //转发到jsp
		}else{
			//非法操作
			response.sendRedirect("userMain.page");
		}
	}
	public void oidSel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(" -查看订单详情- ");
		String orderId = request.getParameter("orderNo");
		JSONObject result = new JSONObject();
		JSONArray jsonArray = orderItemDao.orderIdSel(orderId);
		
		result.put("rows", jsonArray);
		responseUtil.write(response, result);
		
	}
}
