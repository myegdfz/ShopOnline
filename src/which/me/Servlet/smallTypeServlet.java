package which.me.Servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import which.me.Bean.smallTypeBean;
import which.me.Dao.smallTypeDao;
import which.me.utilty.responseUtil;

/**
 * Servlet implementation class smallTypeServlet
 */
@WebServlet("/smallTypeServlet")
public class smallTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public smallTypeServlet() {
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
		
		MethodName  = MethodName.substring(1, MethodName.length()- 6);
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
	public void sel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("- 小类查询  -");
		String p = request.getParameter("page"); //需求页码
		String rows = request.getParameter("rows"); //每页多少条
		String sel = request.getParameter("s_productSmallTypeName"); //如果是查询这不为空
		System.out.println("收到请求："+p+"  "+rows+"  "+sel);
		if(sel==null){
			JSONObject result = new JSONObject();
			String sql = "select count(*) count from t_smallType";
			int count = smallTypeDao.count(sql); //获取条数
			
			JSONArray jsonArray = smallTypeDao.selAll(Integer.parseInt(p), Integer.parseInt(rows)); //获取dao返回的json集合
			System.out.println("MyName : "+jsonArray);
			result.put("rows", jsonArray);
			result.put("total", count);
			responseUtil.write(response, result);
		}else{
			JSONObject result = new JSONObject();
			String sql = "select count(*) count from t_smallType where name like '%"+sel+"%'";
			System.out.println("查询行数sql为："+sql);
			int count = smallTypeDao.count(sql); //获取条数
			JSONArray jsonArray = smallTypeDao.nameSel(Integer.parseInt(p), Integer.parseInt(rows),sel); //获取dao返回的json集合
			result.put("rows", jsonArray);
			result.put("total", count);
			responseUtil.write(response, result);
		}
	}
	/**
	 * 单个删除 and 多个删除
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("ids");
		int s = id.indexOf(",");
		int i = 0;
		if(s!=-1){
			i = smallTypeDao.manyDel(id);
		}else{
			i = smallTypeDao.del(Integer.parseInt(id));
		}
		System.out.println("接收到的为："+id);
		JSONObject result=new JSONObject();
		if(i==0){
			result.put("errorMsg", "删除失败");
		}else{
			result.put("success", "true");
		}
		responseUtil.write(response, result);
	}
	/**
	 * 添加用户
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("productSmallType.name");
		String remarks = request.getParameter("productSmallType.remarks");
		String bigTypeId = request.getParameter("productSmallType.bigTypeId");
		System.out.println("添加用户接收到："+name+remarks+bigTypeId);
		smallTypeBean b = new smallTypeBean(name,Integer.parseInt(bigTypeId),remarks);
		int i = smallTypeDao.add(b);
		JSONObject result=new JSONObject();
		if(i==0){
			result.put("errorMsg", "添加失败");
		}else{
			result.put("success", "true");
		}
		responseUtil.write(response, result);
	}
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("- 商品更新  -");
		String name = request.getParameter("productSmallType.name");
		String remarks = request.getParameter("productSmallType.remarks");
		int bigId = Integer.parseInt(request.getParameter("productSmallType.bigTypeId"));
		int id = Integer.parseInt(request.getParameter("productSmallTypeId"));
		smallTypeBean b = new smallTypeBean(name,bigId,remarks);
		b.setId(id);
		int i = smallTypeDao.update(b);
		JSONObject result=new JSONObject();
		if(i==0){
			result.put("errorMsg", "更新失败");
		}else{
			result.put("success", "true");
		}
		responseUtil.write(response, result);
	}
	public void selList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("id", "");
		jsonObject.put("name", "请先选择大类...");
		jsonArray.add(jsonObject);
		responseUtil.write(response, jsonArray);
		
	}
	public void idSelList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bigTypeId = request.getParameter("bigTypeId");
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		if(bigTypeId.equals("")){
			jsonObject.put("id", "");
			jsonObject.put("name", "请先选择大类...");
			jsonArray.add(jsonObject);
		}else{
			jsonObject.put("id", "");
			jsonObject.put("name", "请选择...");
			jsonArray.add(jsonObject);
			JSONArray jsonArray2 = smallTypeDao.bigTypeIdsel(Integer.parseInt(bigTypeId)); //获取dao返回的json集合
			jsonArray.addAll(jsonArray2);
		}
		responseUtil.write(response, jsonArray);
		
	}

}
