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
import which.me.Bean.bigTypeBean;
import which.me.Bean.goodsBean;
import which.me.Bean.shoppingCart;
import which.me.Bean.slideBean;
import which.me.Dao.bigTypeDao;
import which.me.Dao.goodsDao;
import which.me.Dao.shoppingCartDao;
import which.me.Dao.slideDao;
import which.me.Dao.smallTypeDao;
import which.me.Dao.usersDao;

/**
 * Servlet implementation class indexServlet
 */

public class indexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public indexServlet() {
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
		/*List<bigTypeBean> floor = bigTypeDao.selList(); //获取楼层大类集合
		List<slideBean> slideList = slideDao.selList();  //获取幻灯图片集合
		request.setAttribute("floor", floor);
		request.setAttribute("slideList", slideList);
		System.out.println("二楼的名称是："+floor.get(1).getName());*/
		
		
		System.out.println("- 首页  -");
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
			for(shoppingCart s: list){
				goodsBean hgoods2 = goodsDao.gwcGoodsIdSel(s.getGoodsId()); //从数据库查找
				hgoods2.setNum(s.getNum());
				goodsList.add(hgoods2);
			}
			
		}
		
		
		request.setAttribute("gwcGoodsList", goodsList);
		request.getRequestDispatcher("/index.jsp").forward(request, response); //转发到首页
		
		
	}

}
