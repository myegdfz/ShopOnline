package which.me.utilty;

import java.util.List;
import which.me.Bean.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContext;
import which.me.Bean.bigTypeBean;
import which.me.Dao.bigTypeDao;
import which.me.Dao.slideDao;

/**
 * Application Lifecycle Listener implementation class applicationListener
 *
 */
@WebListener
public class applicationListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public applicationListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("application容器销毁");
    	
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("application容器初始化");
    	List<bigTypeBean> floor = bigTypeDao.selList(); //获取楼层大类集合
		List<bigTypeBean> bigTypes = bigTypeDao.bigselList(); //获取大类及小类级联
		List<slideBean> slideList = slideDao.selList();  //获取幻灯图片集合
		
		ServletContext application = arg0.getServletContext();
		application.setAttribute("floor", floor);
		application.setAttribute("slideList", slideList);
		application.setAttribute("bigTypes", bigTypes);
		System.out.println("已放入application");
    }
	
}
