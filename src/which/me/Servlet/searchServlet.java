package which.me.Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import which.me.Bean.PageBean;
import which.me.Bean.goodsBean;
import which.me.Bean.shoppingCart;
import which.me.Dao.bigTypeDao;
import which.me.Dao.goodsDao;
import which.me.Dao.shoppingCartDao;
import which.me.Dao.smallTypeDao;
import which.me.Dao.usersDao;

/**
 * Servlet implementation class searchServlet
 */
public class searchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchServlet() {
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
		String s = request.getParameter("name"); //获取搜索的商品名称
		int p = Integer.parseInt(request.getParameter("p")); //获取需求的页数
		String order = request.getParameter("order"); //获取排序方式
		System.out.println("获取默认的排序方式是:"+order);
		String bid = request.getParameter("bid");
		String sid = request.getParameter("sid");
		List<goodsBean> salesTop = goodsDao.salesTop(); //前五销量
		PageBean PageDate = null;
		if(order==null){
			order="1";
		}
		if(bid!=null){
			//根据大类ID查询
			PageDate = goodsDao.bidPageSel(bid, p, order);
			request.setAttribute("bigTypeName", bigTypeDao.bidIsName(bid));  //大类名称放入到请求中
		}else if(sid!=null){
			//根据小类ID查询
			PageDate = goodsDao.sidPageSel(sid, p, order);
			request.setAttribute("smallTypeName", smallTypeDao.sidIsName(sid));  //小类名称放入请求
		}else{
			//商品名称查询
			/*
			1:按热度排序
			2:按发布时间
			3:按价格排序
			4:按销量排序
			 */
			PageDate = goodsDao.pageSel(s, p,order); //得到查询页的数据
		}
		//获取购物车
		String username = (String)request.getSession().getAttribute("username"); //获取登录的用户名
		List<goodsBean> goodsList = new ArrayList<goodsBean>(); //购物车集合
		if(username==null){
			//用户没有登录
			//获取购物车
			HashMap<String, goodsBean> gwc = (HashMap)request.getSession().getAttribute("gwc");
			Set keyList = gwc.keySet();
			Iterator it = keyList.iterator();
			
			while(it.hasNext()){
				String hid = (String)it.next();
				goodsBean hgoods = gwc.get(hid);
				int num = hgoods.getNum(); //获取这个商品的数量
				goodsBean hgoods2 = goodsDao.gwcGoodsIdSel(Integer.parseInt(hid)); //从数据库查找
				hgoods2.setNum(num);
				goodsList.add(hgoods2);
			}
			
		}else{
			//用户已经登录
			int userId = usersDao.nameIsId(username);
			//获取购物车中所有购物项目
			List<shoppingCart> list = shoppingCartDao.selList(userId);
			for(shoppingCart s1: list){
				goodsBean hgoods2 = goodsDao.gwcGoodsIdSel(s1.getGoodsId()); //从数据库查找
				hgoods2.setNum(s1.getNum());
				goodsList.add(hgoods2);
			}
		}
		request.setAttribute("gwcGoodsList", goodsList);
		request.setAttribute("PageDate", PageDate);  //放入到请求中
		request.setAttribute("salesTop", salesTop); //放入前5销量
		request.setAttribute("order", order); //放入排序方式
		
		
		

		String type = "name";  //标识搜索的类型   按商品名称查询  按大类ID查询  按小类ID查询
		String tname = s; //搜索名称或按类别查询的名称
		if(s==null){
			if(bid!=null){
				s=bid;
				type="bid";
				tname=(String)request.getAttribute("bigTypeName");
			}else if(sid!=null){
				s=sid;	
				type="sid";
				tname=(String)request.getAttribute("smallTypeName");
			}
		}
		request.setAttribute("s", s); //放入搜索名称或ID
		request.setAttribute("type", type); //放入搜索类型
		request.setAttribute("tname", tname); //放入搜索名称
		request.getRequestDispatcher("search.jsp").forward(request, response);  //转发到jsp
	}

}
