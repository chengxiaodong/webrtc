package com.tiantian.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 
 * @author 晓东
 *
 */
@Controller
@RequestMapping("/login/code")
public class CodeController {

    @RequestMapping("/image")
    public void image(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");

        BufferedImage bfi = new BufferedImage(80, 25, BufferedImage.TYPE_INT_RGB);
        Graphics g = bfi.getGraphics();
        g.fillRect(0, 0, 80, 25);

        //验证码字符范围
        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        Random r = new Random();
        int index;
        StringBuffer sb = new StringBuffer(); //保存字符串
        for (int i = 0; i < 4; i++) {
            index = r.nextInt(ch.length);
            g.setColor(new Color(75, 0, 10)); //蓝色
            Font font = new Font("宋体", 30, 20);
            g.setFont(font);
            g.drawString(ch[index] + "", (i * 20) + 2, 23);
            sb.append(ch[index]);
        }

        // 添加噪点
        int area = 30;   //添加30个噪点
        for (int i = 0; i < area; ++i) {    
            int x = (int) (Math.random() * 80);   //x随机
            int y = (int) (Math.random() * 25);   //y随机
            bfi.setRGB(x, y, (int) (Math.random() * 255)); //噪点颜色随机
        }

     
        HttpSession session = request.getSession();  //保存到session
        session.setAttribute("verificationCode", sb.toString());
      /*  System.out.println("设置验证码  HttpSession:"+session);
        System.out.println("设置验证码  HttpSession id :"+session.getId());
        System.out.println("session设置verificationCode："+session.getAttribute("verificationCode"));*/
        ImageIO.write(bfi, "JPG", response.getOutputStream());  //写到输出流
    }


    /**
     * 
     * @param code
     * @param session
     * @return
     */
	@RequestMapping("/checkCode")
    @ResponseBody
    public Map<String,Object> checkCode(String code,HttpSession session){
		
    	System.out.println("ajax所在session:"+session);
    	System.out.println("ajax所在sessionid:"+session.getId());
    	System.out.println("---------------------------------------------------------------");
    	System.out.println("----------------------code:---------"+code+"=？verificationCode:"+session.getAttribute("verificationCode"));
    	System.out.println("---------------------------------------------------------------");
        Map<String,Object> map=new HashMap<>();
                                                                
        String verificationCode = (String) session.getAttribute("verificationCode");
        
        if(verificationCode.equalsIgnoreCase(code)){
            map.put("valid",true);
        }else{
            map.put("valid",false);
        }
        return map;
    }
}
