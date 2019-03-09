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

import which.me.Bean.goodsBean;
import which.me.Bean.shoppingCart;
import which.me.Dao.goodsDao;
import which.me.Dao.shoppingCartDao;
import which.me.Dao.usersDao;

/**
 * Servlet implementation class goodsPageServlet
 */
@WebServlet("/goodsPageServlet")
public class goodsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public goodsPageServlet() {
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
		//doGet(request, response);
		String ttt =request.getParameter("id");
		System.out.println("opop : " + ttt);
		
		int id = Integer.parseInt(request.getParameter("id")); //获取宝贝的ID
		goodsBean g = goodsDao.goodsIdSel(id);
		System.out.println("商品名称："+g.getName()+"  商品价格："+g.getPrice()+"  图片路径："+g.getProPic()+"  商品分类："+g.getBrand()+"  商品销量："+g.getSales()+"  浏览量："+g.getViews()+"  商品库存："+g.getStock()+"  商品描述："+g.getContents()+"  大类ID："+g.getBigTypeId()+"  大类名称："+g.getBigTypeName()+"  小类ID："+g.getSmallTypeId()+"  小类名称："+g.getSmallTypeName());
		List<goodsBean> xgGoods = goodsDao.bigTypeIdSelxg(g.getBigTypeId()); //获取相关商品
		
		request.setAttribute("goodsBean", g);
		request.setAttribute("xgGoods", xgGoods);
		goodsDao.addViews(id); //浏览量加1
		
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
			for(shoppingCart s: list){
				goodsBean hgoods2 = goodsDao.gwcGoodsIdSel(s.getGoodsId()); //从数据库查找
				hgoods2.setNum(s.getNum());
				goodsList.add(hgoods2);
			}
		}
		request.setAttribute("gwcGoodsList", goodsList);
		
		request.getRequestDispatcher("goods.jsp").forward(request, response);
	}

}
