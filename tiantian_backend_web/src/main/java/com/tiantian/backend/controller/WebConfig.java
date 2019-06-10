package com.tiantian.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebConfig {
													/**
													 * #tiantian_portal_web
												tiantian_portal_web.ip=192.168.43.30
												tiantian_portal_web.port_http=9003
												tiantian_portal_web.port_https=8444
												#tiantian_webrtc
												tiantian_webrtc.ip=192.168.43.30
												tiantian_webrtc.port_http=9001
												#tiantian_webrtc_web
												tiantian_webrtc_web.ip=192.168.43.30
												tiantian_webrtc_web.port_http=9002
												tiantian_webrtc_web.port_https=8443
												#tiantian_backend
												tiantian_backend.port=9004
												#tiantian_backend_web
												tiantian_backend_web.port=9005
												
												#redisCluster 
												redisCluster.ip1=192.168.182.128:7001
												redisCluster.ip2=192.168.182.128:7001
												redisCluster.ip3=192.168.182.128:7001
												redisCluster.ip4=192.168.182.128:7001
												redisCluster.ip5=192.168.182.128:7001
												redisCluster.ip6=192.168.182.128:7001
												#zookeeper
												zookeeper.ip1=192.168.182.128:2181
												zookeeper.ip1=192.168.182.131:2181
												zookeeper.ip1=192.168.182.132:2181
	 */
	//tiantian_portal_web
	@Value("${tiantian_portal_web.ip}")
	private String tiantian_portal_web_ip;
	@Value("${tiantian_portal_web.port_http}")
	private String tiantian_portal_web_port_http;
	public String getTiantian_portal_web_ip() {
		return tiantian_portal_web_ip;
	}

	public void setTiantian_portal_web_ip(String tiantian_portal_web_ip) {
		this.tiantian_portal_web_ip = tiantian_portal_web_ip;
	}

	public String getTiantian_portal_web_port_http() {
		return tiantian_portal_web_port_http;
	}

	public void setTiantian_portal_web_port_http(String tiantian_portal_web_port_http) {
		this.tiantian_portal_web_port_http = tiantian_portal_web_port_http;
	}

	public String getTiantian_portal_web_port_https() {
		return tiantian_portal_web_port_https;
	}

	public void setTiantian_portal_web_port_https(String tiantian_portal_web_port_https) {
		this.tiantian_portal_web_port_https = tiantian_portal_web_port_https;
	}

	public String getTiantian_webrtc_ip() {
		return tiantian_webrtc_ip;
	}

	public void setTiantian_webrtc_ip(String tiantian_webrtc_ip) {
		this.tiantian_webrtc_ip = tiantian_webrtc_ip;
	}

	public String getTiantian_webrtc_port_http() {
		return tiantian_webrtc_port_http;
	}

	public void setTiantian_webrtc_port_http(String tiantian_webrtc_port_http) {
		this.tiantian_webrtc_port_http = tiantian_webrtc_port_http;
	}

	public String getTiantian_webrtc_web_port_http() {
		return tiantian_webrtc_web_port_http;
	}

	public void setTiantian_webrtc_web_port_http(String tiantian_webrtc_web_port_http) {
		this.tiantian_webrtc_web_port_http = tiantian_webrtc_web_port_http;
	}

	public String getTiantian_webrtc_web_port_https() {
		return tiantian_webrtc_web_port_https;
	}

	public void setTiantian_webrtc_web_port_https(String tiantian_webrtc_web_port_https) {
		this.tiantian_webrtc_web_port_https = tiantian_webrtc_web_port_https;
	}

	public String getTiantian_backend_port() {
		return tiantian_backend_port;
	}

	public void setTiantian_backend_port(String tiantian_backend_port) {
		this.tiantian_backend_port = tiantian_backend_port;
	}

	public String getTiantian_backend_web() {
		return tiantian_backend_web;
	}

	public void setTiantian_backend_web(String tiantian_backend_web) {
		this.tiantian_backend_web = tiantian_backend_web;
	}

	public String getRedisCluster_ip1() {
		return redisCluster_ip1;
	}

	public void setRedisCluster_ip1(String redisCluster_ip1) {
		this.redisCluster_ip1 = redisCluster_ip1;
	}

	public String getRedisCluster_ip2() {
		return redisCluster_ip2;
	}

	public void setRedisCluster_ip2(String redisCluster_ip2) {
		this.redisCluster_ip2 = redisCluster_ip2;
	}

	public String getRedisCluster_ip3() {
		return redisCluster_ip3;
	}

	public void setRedisCluster_ip3(String redisCluster_ip3) {
		this.redisCluster_ip3 = redisCluster_ip3;
	}

	public String getRedisCluster_ip4() {
		return redisCluster_ip4;
	}

	public void setRedisCluster_ip4(String redisCluster_ip4) {
		this.redisCluster_ip4 = redisCluster_ip4;
	}

	public String getRedisCluster_ip5() {
		return redisCluster_ip5;
	}

	public void setRedisCluster_ip5(String redisCluster_ip5) {
		this.redisCluster_ip5 = redisCluster_ip5;
	}

	public String getRedisCluster_ip6() {
		return redisCluster_ip6;
	}

	public void setRedisCluster_ip6(String redisCluster_ip6) {
		this.redisCluster_ip6 = redisCluster_ip6;
	}

	public String getZookeeper_ip1() {
		return zookeeper_ip1;
	}

	public void setZookeeper_ip1(String zookeeper_ip1) {
		this.zookeeper_ip1 = zookeeper_ip1;
	}

	public String getZookeeper_ip2() {
		return zookeeper_ip2;
	}

	public void setZookeeper_ip2(String zookeeper_ip2) {
		this.zookeeper_ip2 = zookeeper_ip2;
	}

	public String getZookeeper_ip3() {
		return zookeeper_ip3;
	}

	public void setZookeeper_ip3(String zookeeper_ip3) {
		this.zookeeper_ip3 = zookeeper_ip3;
	}

	@Value("${tiantian_portal_web.port_https}")
	private String tiantian_portal_web_port_https;
	//tiantian_webrtc
	@Value("${tiantian_webrtc.ip}")
	private String tiantian_webrtc_ip;
	@Value("${tiantian_webrtc.port_http}")
	private String tiantian_webrtc_port_http;
	//tiantian_webrtc_web
	@Value("${tiantian_webrtc_web.ip}")
	private String tiantian_webrtc_web_ip;
	public String getTiantian_webrtc_web_ip() {
		return tiantian_webrtc_web_ip;
	}

	public void setTiantian_webrtc_web_ip(String tiantian_webrtc_web_ip) {
		this.tiantian_webrtc_web_ip = tiantian_webrtc_web_ip;
	}

	@Value("${tiantian_webrtc_web.port_http}")
	private String tiantian_webrtc_web_port_http;
	@Value("${tiantian_webrtc_web.port_https}")
	private String tiantian_webrtc_web_port_https;
	//tiantian_backend
	@Value("${tiantian_backend.port}")
	private String tiantian_backend_port;
	//tiantian_backend_web
	@Value("${tiantian_backend_web.port}")
	private String tiantian_backend_web;
	//redisCluster 
	
	
	@Value("${redisCluster.ip1}")
	private String redisCluster_ip1;
	@Value("${redisCluster.ip2}")
	private String redisCluster_ip2;
	@Value("${redisCluster.ip3}")
	private String redisCluster_ip3;
	@Value("${redisCluster.ip4}")
	private String redisCluster_ip4;
	@Value("${redisCluster.ip5}")
	private String redisCluster_ip5;
	@Value("${redisCluster.ip6}")
	private String redisCluster_ip6;
	
	//zookeeper
	@Value("${zookeeper.ip1}")
	private String zookeeper_ip1;
	
	@Value("${zookeeper.ip2}")
	private String zookeeper_ip2;
	
	@Value("${zookeeper.ip3}")
	private String zookeeper_ip3;
	
}











































