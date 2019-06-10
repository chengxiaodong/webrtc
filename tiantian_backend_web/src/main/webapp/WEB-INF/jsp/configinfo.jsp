<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>天天视频通话</title>
	<meta charset="utf-8">
	
	
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layer.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/configinfo.css" />
	<script>
	
	
	</script>
</head>
<body>  
<div class="mywrapper">
	<div class="row" >
		<div class="panel col-xs-2 mypanel" >
			<div class="panel-heading">tiantian_portal_web</div>
				<div class="panel-body">
					<p>ip地址:${webconfig.tiantian_portal_web_ip}</p>
					<p>http端口号:${webconfig.tiantian_portal_web_ip}</p>
					<p>https端口号:${webconfig.tiantian_portal_web_port_https}</p>
				</div>
		</div>
		
		<div class="panel col-xs-2 mypanel">
				<div class="panel-heading">tiantian_webrtc</div>
				<div class="panel-body">
					<p>ip地址:${webconfig.tiantian_webrtc_ip}</p>
					<p>端口号:${webconfig.tiantian_webrtc_port_http}</p>
				</div>
		</div>
	
	
		<div class="panel col-xs-2 mypanel">
			<div class="panel-heading">tiantian_webrtc_web</div>
			<div class="panel-body">
				<p>ip地址:${webconfig.tiantian_webrtc_web_ip}</p>
				<p>http端口号:${webconfig.tiantian_webrtc_web_port_http}</p>
				<p>https端口号:${webconfig.tiantian_webrtc_web_port_https}</p>
			</div>
		</div>
		
	
	
	
	
		<div class="panel  col-xs-2 mypanel">
			<div class="panel-heading">tiantian_backend</div>
			<div class="panel-body">
				<p>端口号:${webconfig.tiantian_backend_port}</p>
			</div>
		</div>
	</div>
	<div class="row" >
		<div class="panel  col-xs-2 mypanel">
			<div class="panel-heading">tiantian_backend_web</div>
			<div class="panel-body">
				<%-- ${webconfig.redisCluster_ip1} --%>
				<p>端口号:${webconfig.tiantian_backend_web}</p>
			</div>
		</div>
		
		<div class="panel  col-xs-3 mypanel">
			<div class="panel-heading">redisCluster </div>
			<div class="panel-body">
				<p>ip1:port: ${ webconfig.redisCluster_ip1}</p>
				<p>ip2:port: ${ webconfig.redisCluster_ip2}</p>
				<p>ip3:port: ${ webconfig.redisCluster_ip3}</p>
				<p>ip4:port: ${ webconfig.redisCluster_ip4}</p>
				<p>ip5:port: ${ webconfig.redisCluster_ip5}</p>
				<p>ip6:port: ${ webconfig.redisCluster_ip6}</p>
			</div>
		</div>
	
	
		<div class="panel col-xs-3 mypanel">
			<div class="panel-heading">zookeeper</div>
			<div class="panel-body">
				${webconfig.redisCluster_ip1}
				<p>ip1:port:${webconfig.zookeeper_ip1 }</p>
				<p>ip2:port:${webconfig.zookeeper_ip2 }</p>
				<p>ip3:port:${webconfig.zookeeper_ip3 }</p>
			</div>
		</div>
	</div>
 </div>	
	

<!-- 页尾 版权声明 -->
<div class="container">
	<div class="col-sm-12 foot-css">
		<p class="text-muted credit">
			Copyright 南京工程学院
		</p>
	</div>
</div>

</body>
</html>
