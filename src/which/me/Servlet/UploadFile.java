package which.me.Servlet;

import java.io.IOException;
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

import net.sf.json.JSONObject;
import which.me.Bean.bigTypeBean;
import which.me.Dao.bigTypeDao;
import which.me.utilty.responseUtil;

/**
 * Servlet implementation class UploadFile
 */

public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFile() {
        super();
        // TODO Auto-generated constructor stub
        System.out.println("UpPICTRUE 执行方法");
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
		SmartUpload su=new SmartUpload();
		PageContext pc=JspFactory.getDefaultFactory().getPageContext(this, request, response,null, true, 8192, true);
		su.initialize(pc); 
		su.setMaxFileSize(1024*2048); 
		try {
			su.setAllowedFilesList("jpg,png,bmp,gif,jpeg,txt,doc,xls,ppt");//允许上传类型
			su.setDeniedFilesList("exe,bat,jsp,html,htm,java,class");//不允许上传的类型
			su.upload();	
			String name=su.getRequest().getParameter("productBigType.name");//获取表单组件nam的值
			String remarks=su.getRequest().getParameter("productBigType.remarks");
			System.out.println("name="+name);
			System.out.println("remarks"+remarks);
			Files fs=su.getFiles();//获取所有上传的文件集合
			System.out.println("总字节数："+fs.getSize());
			File f=fs.getFile(0);//获取上传的文件
			System.out.println("文件全名"+f.getFilePathName());
			System.out.println("文件表单名称："+f.getFieldName());
			System.out.println("文件名："+f.getFileName());
			System.out.println("文件全名称："+f.getFilePathName());
			System.out.println("文件扩展名："+f.getFileExt());
			System.out.println("文件大小："+f.getSize());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH-mm-ss");
			String url = "/images/bigTypeImg/"+sdf.format(new Date())+"."+f.getFileExt();
			f.saveAs(url);//保存
			bigTypeBean b = new bigTypeBean(name,remarks);
			b.setImgUrl(url);
			int i = bigTypeDao.add(b);
			JSONObject result=new JSONObject();
			if(i==0){
				result.put("errorMsg", "添加失败");
			}else{
				result.put("success", "true");
			}
			responseUtil.write(response, result);
			
			//response.sendRedirect("info.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
