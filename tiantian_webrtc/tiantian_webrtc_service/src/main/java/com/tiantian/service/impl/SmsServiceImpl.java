package com.tiantian.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tiantian.service.SmsService;

import common.util.HttpClientUtils;
import common.util.ResponseResult;
import redis.clients.jedis.JedisCluster;

/**
 * 短信发送服务
 * 使用聚合sms服务发送验证码，验证码有效时间为5分钟(300s)，将生成的验证码使用jedis存放在redis key为REDIS_CODEVERIFY_KEY中hashset中。
 * @author 晓东
 *
 */
@Service
public class SmsServiceImpl implements SmsService{
	@Autowired
	private JedisCluster jedisCluster;
	@Value("codeVerify")
	private String REDIS_CODEVERIFY_KEY;
	//sms变量
	//sms的发送api地址
	 @Value("${sms.url}")
	    private String url;
//sms api的用户识别码
	    @Value("${sms.key}")
	    private String key;
//sms api的消息模板id
	    @Value("${sms.tplId}")
	    private String tplId;
//sms api模板中的 ${sms.tplValue}===》%code%=   
	    @Value("${sms.tplValue}")
	    private String tplValue;
	
	/**
	 * 1、调用聚合的接口向phoneNumber发送验证短信，
	 * 2、调用jedisCluster将短信验证码保存在redis中
	 * 
	 */
	@Override
	public ResponseResult sendSmsVerifyCode(String JSESSIONID,String phoneNumber) {
		//sms发送验证码
		
		try {
            //生成六位的随机验证码
            Random random = new Random();
            //生成6位随机数
            int verifyCode = random.nextInt(999999);
            //session.setAttribute("randVerificationCode", randVerificationCode);

           /* //将验证码存放到Redis中
            RedisUtils.set(session.getId(), randVerificationCode+"", 2 * 60); //验证码有效期为2分钟
*/
            //发送短信
            Map<String, String> params = new HashMap<>();
            params.put("mobile", phoneNumber);
            params.put("key", key);
            params.put("tpl_id", tplId);
            params.put("tpl_value", tplValue + verifyCode);
            //调用sms api发送短信
            String result = HttpClientUtils.doPost(url, params);
           System.out.println(result);
            //保存到redis
           
            //保存验证码到redis
            jedisCluster.set(REDIS_CODEVERIFY_KEY+JSESSIONID,verifyCode+"");
            //设置验证码过期时间为5分钟
            jedisCluster.expire(REDIS_CODEVERIFY_KEY+JSESSIONID, 60*5);
            System.out.println("以向"+phoneNumber+"发送验证码，验证码已保存值redis"+verifyCode);
            return ResponseResult.success("验证码发送成功");
        } catch (Exception e) {
            return ResponseResult.fail("验证码发送失败");
        }
		
	
		
	}

	/**
	 * 获得key为JSESSIONID的短信验证码值
	 */
	@Override
	public String getSmsVerifyCode(String JSESSIONID) {
		String codeValue=null;
		try {
			//从redis中取数据
			codeValue = jedisCluster.get(REDIS_CODEVERIFY_KEY+JSESSIONID);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return codeValue;
	}

}
