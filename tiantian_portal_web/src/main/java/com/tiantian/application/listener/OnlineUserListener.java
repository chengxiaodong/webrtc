package com.tiantian.application.listener;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;

import org.springframework.util.StringUtils;

import com.tiantian.pojo.TbUser;

import javax.servlet.http.HttpSessionEvent;

/**                
 * 保存在线用户的记录===》uid
 * @author 晓东
 *
 */
public class OnlineUserListener implements HttpSessionListener {
	
	public static Integer lock=1;
	
    public void sessionCreated(HttpSessionEvent event) {
    	
    }

    public void sessionDestroyed(HttpSessionEvent event) {
    	//ServletContext不是线程安全的，所以必须枷锁
    	synchronized (lock) {
    		HttpSession session = event.getSession();
            ServletContext applicationContext = session.getServletContext();

            // 取得登录的用户名
            TbUser user = (TbUser) session.getAttribute("user");
            System.out.println("session "+user+"  session close");
            if(user!=null) {
    		        String uid=user.getId()+"";
    		        if(!StringUtils.isEmpty(uid)) {
    		        	//当前session用户已登录
    			        // 从在线列表中删除用户名
    			        List onlineUserList = (List) applicationContext.getAttribute("onlineUserList");
    			        onlineUserList.remove(uid);
    			        applicationContext.setAttribute("onlineUserList",onlineUserList);
    			        System.out.println(uid + "退出。");
    		        }
            }
		}
        
    }
    /**
     * 判断指定的uid是否在线
     * @param uid
     * @return
     */
    public static boolean isOnline(String uid,HttpSession session) {
    	ServletContext applicationContext;
    	List<String> onlineUserList;
    	synchronized (lock) {
			
    		 applicationContext=session.getServletContext();
    		 onlineUserList = (ArrayList<String>) applicationContext.getAttribute("onlineUserList");
    		for(String i:onlineUserList) {
    			System.out.println("在线user的id:"+i);
    		}
		}
    	return onlineUserList.contains(uid);
    	
    }
    /**
     * 
     * @param uids
     * @return 在线的用户uid线性表
     */
    
    public static List<String> isOnline(List<String> uids,HttpSession session) {
    	ServletContext applicationContext=session.getServletContext();
    	List<String> onlineUserList = (List) applicationContext.getAttribute("onlineUserList");
    	
    	for(String i:onlineUserList) {
    		System.out.println("在线user的id:"+i);
    	}
    	
    	List onlineUids=new ArrayList<String>();
    	for(String uid:uids) {
    		if(onlineUserList.contains(uid)) {
    			onlineUids.add(uid);
    		}
    	}
    	return onlineUids;
    }
    
    public static void addOnline(Integer uid,HttpSession session) {
    	ServletContext applicationContext;
    	List<String> onlineUserList;
    	synchronized (lock) {
			
    		 applicationContext=session.getServletContext();
    		 onlineUserList = (ArrayList<String>) applicationContext.getAttribute("onlineUserList");
    		  //将用户的在线情况保存到applicationContext
             if(onlineUserList==null)
            	 onlineUserList=new ArrayList<String>();
             if(!onlineUserList.contains(uid+""))
            	 onlineUserList.add(uid+"");
             session.getServletContext().setAttribute("onlineUserList",onlineUserList);
		}
    }
    
}



















