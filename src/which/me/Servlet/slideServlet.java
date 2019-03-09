package which.me.Servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import which.me.Dao.slideDao;
import which.me.utilty.responseUtil;

/**
 * Servlet implementation class slideServlet
 */
@WebServlet("/slideServlet")
public class slideServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public slideServlet() {
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
		MethodName = MethodName.substring(1, MethodName.length() - 6);
		System.out.println("MethodName : " + MethodName);
		try {
			Method method = getClass().getDeclaredMethod(MethodName, HttpServletRequest.class , HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 查询所有
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


			JSONObject result = new JSONObject();
			
			JSONArray jsonArray = slideDao.selAll(); //获取dao返回的json集合
			result.put("rows", jsonArray);
			result.put("total", 5);
			responseUtil.write(response, result);
		
	}
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		SmartUpload su=new SmartUpload();
		PageContext pc=JspFactory.getDefaultFactory().getPageContext(this, request, response,null, true, 8192, true);
		su.initialize(pc);//初始化
		su.setMaxFileSize(1024*2048);//限制大小2M
		try {
			su.setAllowedFilesList("jpg,png,bmp,gif,jpeg,txt,doc,xls,ppt");//允许上传类型
			su.setDeniedFilesList("exe,bat,jsp,html,htm,java,class");//不允许上传的类型
			su.upload();//上传及检测
			String name=su.getRequest().getParameter("name");//获取表单组件nam的值
			System.out.println("更新幻灯接收到的名字是："+name);
			String url=su.getRequest().getParameter("url");
			int id = Integer.parseInt(request.getParameter("id"));
			Files fs=su.getFiles();//获取所有上传的文件集合
			File f=fs.getFile(0);//获取上传的文件
			if(f.getSize()==0){
				int i = slideDao.update(id, name, url, null);
				JSONObject result=new JSONObject();
				if(i==0){
					result.put("errorMsg", "更新失败");
				}else{
					result.put("success", "true");
				}
				responseUtil.write(response, result);
				//未接收到图片
				return;
			}else{
				//接收到图片
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH-mm-ss");
				String url2 = "images/slide/"+sdf.format(new Date())+"."+f.getFileExt();
				f.saveAs(url2);//保存
				int i = slideDao.update(id, name, url, url2);
				JSONObject result=new JSONObject();
				if(i==0){
					result.put("errorMsg", "更新失败");
				}else{
					result.put("success", "true");
				}
				responseUtil.write(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
