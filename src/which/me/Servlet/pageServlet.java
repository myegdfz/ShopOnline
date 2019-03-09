package which.me.Servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import which.me.Bean.PageBean;
import which.me.Bean.addressBean;
import which.me.Bean.bigTypeBean;
import which.me.Bean.collectBean;
import which.me.Bean.goodsBean;
import which.me.Bean.orderBean;
import which.me.Bean.orderItemBean;
import which.me.Bean.shoppingCart;
import which.me.Bean.slideBean;
import which.me.Bean.userBean;
import which.me.Dao.addressDao;
import which.me.Dao.bigTypeDao;
import which.me.Dao.collectDao;
import which.me.Dao.goodsDao;
import which.me.Dao.orderDao;
import which.me.Dao.orderItemDao;
import which.me.Dao.shoppingCartDao;
import which.me.Dao.slideDao;
import which.me.Dao.usersDao;
import which.me.utilty.responseUtil;


/**
 * Servlet implementation class pageServlet
 */
@WebServlet("/pageServlet")
public class pageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String MethodName = request.getServletPath();
		
		MethodName = MethodName.substring(1, MethodName.length()-5);
		System.out.println("MethodName : " + MethodName);
		try {
			Method method = getClass().getDeclaredMethod(MethodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void userMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" - 用户中心 - ");
		String username = (String)request.getSession().getAttribute("username");
		String p = request.getParameter("p");
		
		if(p==null){ //如果未收到请求页码  则赋值1
			p="1";
		}
		String type = request.getParameter("type");
		/**
		 * 1：全部订单
		 * 2：待支付订单
		 * 3：代发货订单
		 * 4：待确认订单
		 */
		if(type==null){
			type="1";
		}
		
		if(username==null){
			response.sendRedirect("login.jsp");
		}else{
			int userId = usersDao.nameIsId(username);
			PageBean pageBean =orderDao.userIdIsorder(userId,Integer.parseInt(p),type);
			//System.out.println(pageBean);
			request.setAttribute("PageDate", pageBean); //放入订单集合
			
			//获取购物车
			List<goodsBean> goodsList = new ArrayList<goodsBean>(); //购物车集合
			//获取购物车中所有购物项目
			List<shoppingCart> list = shoppingCartDao.selList(userId);
			for(shoppingCart s: list){
				goodsBean hgoods2 = goodsDao.gwcGoodsIdSel(s.getGoodsId()); //从数据库查找
				hgoods2.setNum(s.getNum());
				goodsList.add(hgoods2);
			}
			request.setAttribute("type", type); //放入请求类型
			request.setAttribute("p", p); //放入请求页码
			request.setAttribute("gwcGoodsList", goodsList); //放入购物车集合
			request.setAttribute("rightPage", "orderAll");
			request.getRequestDispatcher("userMain.jsp").forward(request, response);
		}
	}
	/**
	 * 订单详情
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void orderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("username");
		if(username==null){
			response.sendRedirect("login.jsp");
		}
		
		System.out.println(" - 订单详情 - ");
		String id = request.getParameter("id");
		orderBean orderBean = orderDao.orderIdSel(id);
		if(orderBean.getRemarks().equals("")){
			orderBean.setRemarks("未填写");
		}
		addressBean address = addressDao.idSel(orderBean.getAddressId());
		String addressStr = address.getProvince()+"  "+address.getCity()+"  "+address.getArea()+"  "+address.getAddress()+"&nbsp;&nbsp;&nbsp;&nbsp;手机："+address.getPhone()+"&nbsp;&nbsp;&nbsp;&nbsp;收货人姓名："+address.getUsername();
		if(address.getProvince().equals("未填写")){
			addressStr="未填写";
		}
		List<orderItemBean> orderItemList = orderItemDao.orderIdSelItem(id); //获取订单项目
		
		
		//获取购物车
		List<goodsBean> goodsList = new ArrayList<goodsBean>(); //购物车集合
		//获取购物车中所有购物项目
		int userId = usersDao.nameIsId(username);
		List<shoppingCart> list = shoppingCartDao.selList(userId);
		for(shoppingCart s: list){
			goodsBean hgoods2 = goodsDao.gwcGoodsIdSel(s.getGoodsId()); //从数据库查找
			hgoods2.setNum(s.getNum());
			goodsList.add(hgoods2);
		}
		
		
		request.setAttribute("gwcGoodsList", goodsList); //放入购物车集合
		request.setAttribute("orderBean", orderBean);
		request.setAttribute("address", addressStr);
		request.setAttribute("orderItemList", orderItemList);
		request.setAttribute("rightPage", "orderDetail");
		request.getRequestDispatcher("userMain.jsp").forward(request, response);
	}
	/**
	 * ajax添加收藏
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addCollect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goodsId = request.getParameter("id");
		String username = (String)request.getSession().getAttribute("username");
		int userId = usersDao.nameIsId(username);
		goodsBean goods = goodsDao.goodsIdSel(Integer.parseInt(goodsId));
		int i = collectDao.count(userId, Integer.parseInt(goodsId));
		JSONObject json = new JSONObject();
		if(i!=0){
			json.put("msg", "您已收藏过该商品！");
		}else{
			collectDao.add(new collectBean(userId, Integer.parseInt(goodsId), goods.getName(),
					goods.getProPic(), goods.getPrice(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
			json.put("msg", "收藏成功！");
		}
		responseUtil.write(response, json);
	}
	/**
	 * 删除收藏
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void delCollect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goodsId = request.getParameter("id");
		String username = (String)request.getSession().getAttribute("username");
		int userId = usersDao.nameIsId(username);
		int i = collectDao.del(userId, Integer.parseInt(goodsId));
		JSONObject json = new JSONObject();
		if(i!=0){
			json.put("msg", "删除成功！");
		}
		responseUtil.write(response, json);
	}
	/**
	 * 我的收藏
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void MyCollect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("username");
		if(username==null){
			response.sendRedirect("login.jsp");
		}
		//获取购物车
		List<goodsBean> goodsList = new ArrayList<goodsBean>(); //购物车集合
		//获取购物车中所有购物项目
		int userId = usersDao.nameIsId(username);
		List<shoppingCart> list2 = shoppingCartDao.selList(userId);
		for(shoppingCart s: list2){
			goodsBean hgoods2 = goodsDao.gwcGoodsIdSel(s.getGoodsId()); //从数据库查找
			hgoods2.setNum(s.getNum());
			goodsList.add(hgoods2);
		}
		request.setAttribute("gwcGoodsList", goodsList); //放入购物车集合
		
		String page = request.getParameter("page");
		if(page==null){
			page="1";
		}
		int count = (int)Math.ceil(collectDao.count(userId)*1.0/10);
		List<collectBean> list =collectDao.selList(userId,Integer.parseInt(page));
		request.setAttribute("count", count); 
		request.setAttribute("collectList", list); 
		request.setAttribute("rightPage", "MyCollect");
		request.getRequestDispatcher("userMain.jsp").forward(request, response);
	}
	/**
	 * 用户信息页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void userData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("username");
		if(username==null){
			response.sendRedirect("login.jsp");
		}
		//获取购物车
		List<goodsBean> goodsList = new ArrayList<goodsBean>(); //购物车集合
		//获取购物车中所有购物项目
		int userId = usersDao.nameIsId(username);
		List<shoppingCart> list2 = shoppingCartDao.selList(userId);
		for(shoppingCart s: list2){
			goodsBean hgoods2 = goodsDao.gwcGoodsIdSel(s.getGoodsId()); //从数据库查找
			hgoods2.setNum(s.getNum());
			goodsList.add(hgoods2);
		}
		userBean u = usersDao.idSel(userId);
		System.out.println("得到的userBean对象："+u);
		request.setAttribute("userBean", u); //放入userBean
		request.setAttribute("gwcGoodsList", goodsList); //放入购物车集合
		request.setAttribute("rightPage", "userData");
		request.getRequestDispatcher("userMain.jsp").forward(request, response);
		
	}
	/**
	 * 用户地址管理页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void userAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("username");
		if(username==null){
			response.sendRedirect("login.jsp");
		}
		//获取购物车
		List<goodsBean> goodsList = new ArrayList<goodsBean>(); //购物车集合
		//获取购物车中所有购物项目
		int userId = usersDao.nameIsId(username);
		List<shoppingCart> list2 = shoppingCartDao.selList(userId);
		for(shoppingCart s: list2){
			goodsBean hgoods2 = goodsDao.gwcGoodsIdSel(s.getGoodsId()); //从数据库查找
			hgoods2.setNum(s.getNum());
			goodsList.add(hgoods2);
		}
		request.setAttribute("gwcGoodsList", goodsList); //放入购物车集合
		
		
		List<addressBean> list = addressDao.selAll(userId); //获取该用户全部的地址条目
		
		request.setAttribute("addressList", list);
		request.setAttribute("rightPage", "address");
		request.getRequestDispatcher("userMain.jsp").forward(request, response);
	}
	/**
	 * 用戶添加地址
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("username");
		if(username==null){
			response.sendRedirect("login.jsp");
		}
		
		//获取购物车
		List<goodsBean> goodsList = new ArrayList<goodsBean>(); //购物车集合
		//获取购物车中所有购物项目
		int userId = usersDao.nameIsId(username);
		List<shoppingCart> list2 = shoppingCartDao.selList(userId);
		for(shoppingCart s: list2){
			goodsBean hgoods2 = goodsDao.gwcGoodsIdSel(s.getGoodsId()); //从数据库查找
			hgoods2.setNum(s.getNum());
			goodsList.add(hgoods2);
		}
		request.setAttribute("gwcGoodsList", goodsList); //放入购物车集合
		
		request.setAttribute("rightPage", "addAddress");
		request.getRequestDispatcher("userMain.jsp").forward(request, response);
	}
	/**
	 * 用户修改密码
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String)request.getSession().getAttribute("username");
		if(username==null){
			response.sendRedirect("login.jsp");
		}
		
		//获取购物车
		List<goodsBean> goodsList = new ArrayList<goodsBean>(); //购物车集合
		//获取购物车中所有购物项目
		int userId = usersDao.nameIsId(username);
		List<shoppingCart> list2 = shoppingCartDao.selList(userId);
		for(shoppingCart s: list2){
			goodsBean hgoods2 = goodsDao.gwcGoodsIdSel(s.getGoodsId()); //从数据库查找
			hgoods2.setNum(s.getNum());
			goodsList.add(hgoods2);
		}
		request.setAttribute("gwcGoodsList", goodsList); //放入购物车集合
		
		request.setAttribute("rightPage", "changePassword");
		request.getRequestDispatcher("userMain.jsp").forward(request, response);
	}
	/**
	 * 刷新缓存
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void refreshSystem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<bigTypeBean> floor = bigTypeDao.selList(); //获取楼层大类集合
		List<bigTypeBean> bigTypes = bigTypeDao.bigselList(); //获取大类及小类级联
		List<slideBean> slideList = slideDao.selList();  //获取幻灯图片集合
		
		ServletContext application = request.getServletContext();
		application.setAttribute("floor", floor);
		application.setAttribute("slideList", slideList);
		application.setAttribute("bigTypes", bigTypes);
		System.out.println("已刷新系统缓存");
		JSONObject json = new JSONObject();
		json.put("success", "true");
		responseUtil.write(response, json);
		
	}
}
