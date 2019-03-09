package which.me.utilty;

import javax.servlet.annotation.WebListener;
import which.me.Bean.*;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
/**
 * Application Lifecycle Listener implementation class SesListener
 *
 */
@WebListener
public class SesListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public SesListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("创建session对象");
		HashMap<String, goodsBean> gwc = new HashMap<String, goodsBean>();
		arg0.getSession().setAttribute("gwc", gwc);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("销毁session对象");
    }
	
}
