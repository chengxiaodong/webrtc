package com.tiantian.portal.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tiantian.vedio.RtcVideo;

import common.constant.ServerConstant;

/**
 * 视频通话的入口
 * @author 晓东
 *
 */
@Controller
@RequestMapping("/video")
public class VideoController  {
   
/**
 * 视屏请求页面会携带请求者id ：key='requestId',目标id：key='targetId'
 */
	
	@RequestMapping("/msg")
    public void video(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //oid可能为空，为空：当前连接为发起者；不为空：当前对象为加入者；
        String requestId = request.getParameter("requestId");
        String targetId = request.getParameter("targetId");
        request.setAttribute("portalServerIp", ServerConstant.portalServerIp);
        request.setAttribute("portServerPort", ServerConstant.portServerPort);
        if(requestId==null||targetId==null) {
        	System.out.println("requestId 或 targetId为空！");
        	/*request.setAttribute("requestId", -1);
        	request.setAttribute("targetId", -1);*/
        	//模拟测试
        	requestId="1";
        	targetId="2";
        }else {
        	System.out.println("requestId:"+requestId);
        	System.out.println("targetId:"+targetId);
        	request.setAttribute("requestId", requestId);
        	request.setAttribute("targetId", targetId);
        }
        String oid = request.getParameter("oid");
      //  System.out.println("------接受oid------"+oid);
        String uid = session.getId();
      //  String uid=request.getParameter("uid");
       /* String uid = System.currentTimeMillis() + "";
        System.out.println("------生成uid------"+uid);*/
        request.setAttribute("initiator", "false");
     
        if (!RtcVideo.canCreate()) {
            response.getWriter().write("不能创建通话房间，超过最大创建数量！");
            return ;
        }

        if (!RtcVideo.canJoin(oid)) {
            response.getWriter().write("对不起对方正在通话中，你不能加入！");
            return ;
        }

        if (RtcVideo.addUser(uid, oid)) {
        	//当前为创建者
            request.setAttribute("uid", uid);
        } else {
        	//当前为加入者
            request.setAttribute("initiator", "true");

            request.setAttribute("uid", uid);
            request.setAttribute("oid", oid);
        }
         request.getRequestDispatcher("/WEB-INF/jsp/video.jsp").forward(request, response);
        
    }


}
