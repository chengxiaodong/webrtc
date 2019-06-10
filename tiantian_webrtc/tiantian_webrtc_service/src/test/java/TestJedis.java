import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	/**
	 * 手动创建JedisCluster对象
	 */
/*	public void testJedisCluster() {
		//JedisCluster可以使单例的，且自带连接池
		Set<HostAndPort> hostAndPorts=new HashSet<>();
		hostAndPorts.add(new HostAndPort("192.168.182.128", 7001));
		hostAndPorts.add(new HostAndPort("192.168.182.128", 7002));
		hostAndPorts.add(new HostAndPort("192.168.182.128", 7003));
		hostAndPorts.add(new HostAndPort("192.168.182.128", 7004));
		hostAndPorts.add(new HostAndPort("192.168.182.128", 7005));
		hostAndPorts.add(new HostAndPort("192.168.182.128", 7006));
		//创建JedisCluster对象，构造参数为Set<HostAndPort>
		JedisCluster jedisCluster=new JedisCluster(hostAndPorts);
	
		//使用JedisCluster访问集群
		jedisCluster.set("name", "xiaoming");
		System.out.println(jedisCluster.get("name"));
		//interSection(jedisCluster);
		
	}*/
	
	/**
	 * jedis单机版对象
	 * @param jedisCluster
	 * @return
	 */
	@Test
	public  void test02() {
		Jedis jedis=new Jedis("192.168.182.128",7007);
	    
		//集合缓存
		ArrayList<ArrayList<String>> lists=new ArrayList<>();
		//结果集
		ArrayList<String> results=new ArrayList<>();
		//条件集合
		ArrayList<String> list1= new ArrayList<>();
		ArrayList<String> list2 = new ArrayList<>();
		ArrayList<String> list3 = new ArrayList<>();
		
		list1.add("5");
		list1.add("10");
		list1.add("11");
		list1.add("12");
		list2.add("5");
		list2.add("12");
		list3.add("5");
		list3.add("10");
		//放到大集合
		lists.add(list1);
		lists.add(list2);
		lists.add(list3);
		//放到redis 集合中        
		for(int i=0;i<lists.size();i++) {
			for(String str:lists.get(i)) {
				//集合名，添加元素
				jedis.sadd("set"+i, str);
				//20s后过期，自动删除
				jedis.expire("set"+i,60);
			}
		}
		//进行集合运算
		for(int i=0;i<lists.size();i++) {
			for(int j=i+1;j<lists.size();j++) {
				//集合名，添加元素
				Set<String> sinter = jedis.sinter("set"+i, "set"+j);
				results.addAll(sinter);
			}
		}
		//Set<String> set = jedis.sinter("setA","setB");
		/*for(String str:results) {
			System.out.println(str);
		}
		*/
		//按好友重合次数排序
		HashMap<String,Integer> sortMap=new HashMap<>();
		for(String str:results) {
			if(!sortMap.containsKey(str)) {
				//第一次出现
				sortMap.put(str, 1);
			}else {
				//出现次数加1
				sortMap.put(str, sortMap.get(str)+1);
			}
		}
		  List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(sortMap.entrySet());
	        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
	            //升序排序
	            public int compare(Entry<String, Integer> o1,
	                    Entry<String, Integer> o2) {
	            	//按出现次数降序
	                return o2.getValue().compareTo(o1.getValue());
	            }
	            
	        });
	        //清空已缓存的数据
	        results.clear();
	        //将排序后的结果放入results
	        for(Map.Entry<String,Integer> mapping:list){ 
	        		//按好友重合次数排序 返回
	        		results.add(mapping.getKey());
	        		//  key   出现次数
	             //  System.out.println(mapping.getKey()+":"+mapping.getValue()); 
	          } 
	     for(String s:results) {
	    	 System.out.println(s);
	     }
	     
	}
}
















