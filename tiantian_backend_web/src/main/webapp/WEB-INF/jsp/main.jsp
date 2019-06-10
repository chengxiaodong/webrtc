<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8" />
    <!-- 会出现宽度全屏后，高度不能占满父容器 -->
   <!--  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>-->
    <meta http-equiv="X-UA-Compatible" content="ie=edge" /> 
    
    <title>天天视频通话</title>
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/bootstrap.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
     <script src="${pageContext.request.contextPath}/js/main.js"></script>
 <script src="${pageContext.request.contextPath}/layer/layer.js"></script>
 
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layer.css" />
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/main.css" />
  <script type="text/javascript">
 
        $(function(){
             $("#userManage").click(function() {
            	//加载到在线的好友页面
                $("#frame-id").attr("src", "${pageContext.request.contextPath}/page/userManage");
            }); 
            // 点击切换页面
             $("#dubbo").click(function() {
            	 //加载到基本的用户页面
                $("#frame-id").attr("src", "http://192.168.182.128:8080/dubbo-admin/");
            });
             
              $("#configinfo").click(function() {
            	 //加载到添加好友页面
                $("#frame-id").attr("src", "${pageContext.request.contextPath}/page/configinfo");
            });
              
              $("#sysuser").click(function() {
             	 //加载到添加好友页面                                                                                   
                 $("#frame-id").attr("src", "${pageContext.request.contextPath}/page/sysuser/findAll");
             });
              
              $("#superuser").click(function() {
             	 //加载到添加好友页面
                 $("#frame-id").attr("src", "${pageContext.request.contextPath}/page/superuser/findAll");
             });
        });
        
    

    </script>
</head>

<body>  
<div class="mywrapper">

<div class="panel panel-default" >
<div class="wrapper-cc clearfix" >
    <div class="content-cc">
        <!-- header start -->
        <div class="clear-bottom head">
            <div class="container head-cc ">
                
               		 <p><img alt="" src="${pageContext.request.contextPath}/images/njit_logo.png" style="height: 96px;width:96px "><span ><strong>天天视频通话 &nbsp;&nbsp;后台</strong></span></p>
                <div class="welcome">
                    <div class="left" style="margin-right: 12px;">欢迎您：</div>
                    <div class="right" id="username" style="margin-right: 12px;">${sysuser.username}</div>
                    <div ><a href="${pageContext.request.contextPath}/user/logout"  id="exit"> 退出</a></div>
                </div>
            </div>
        </div>
        <!-- header end -->
        <!-- content start -->
        <div class="container-flude flude-cc" id="a">
            <div class="row friends-managerting flude-cc">
                <div class="col-xs-2 user-wrap">
                    <ul class="nav nav-tabs nav-stacked" id="menu">
                    	<li>
	                    	<a href="#systemSetting" class="nav-header collapsed" data-toggle="collapse">
			                          <i class="glyphicon glyphicon-home"></i> &nbsp;用户管理
			                           <span class="pull-right glyphicon glyphicon-chevron-down"></span>
	
	                        </a>
			                         <ul id="systemSetting" class="nav nav-list collapse " style="height: 0px;">
		                            <li><a href="#" id="sysuser"><i class="glyphicon glyphicon-user"></i>普通用户</a></li>
		                            <c:choose>
		                            	<c:when test="${sysuser.level==3 }">
		                           			 <li ><a href="#" id="superuser"><i class="glyphicon glyphicon-th-list"></i>管理员</a></li>
		                            	</c:when>
		                            	<c:otherwise>
		                            		 <li ><a href="javascript:alert('您的权限不够！')" ><i class="glyphicon glyphicon-th-list"></i>管理员</a></li>
		                            	</c:otherwise>
		                            </c:choose>
		                          
		                           
		                         
		                           </ul>
						</li>
                       
                        <c:if test="${sysuser.level==3}">
                        <li class="list-group-item"  id="dubbo">
                            <i class="glyphicon glyphicon-certificate"></i> &nbsp;dubbo管理
                        </li>
                        <li class="list-group-item"  id="configinfo">
                            <i class="glyphicon glyphicon-wrench"></i> &nbsp;网站部署信息
                        </li>
                        </c:if>
                       
                       
                    </ul>
                </div>
                <div class="col-xs-10" id="userPanel" >
                      <!--  <iframe id="frame-id" src="https://192.168.43.30:8443/msg?oid=21" width="100%" height="100%" frameborder="0" scrolling="yes">  -->
                      <iframe id="frame-id" src="${pageContext.request.contextPath}/page/sysuser/findAll"
                      
                        width="100%" height="100%" frameborder="0" scrolling="auto">  
                    <!--   sandbox="allow-forms allow-same-origin allow-scripts allow-top-navigation allow-modals"  -->
                    </iframe>
                </div>

            </div>
        </div>
        </div>
        <!-- content end-->
    </div>
</div>
<!-- footers start -->
<div class="footer ">
    版权所有：南京工程学院
</div>
<!-- footers end -->
</div>
<!-- ************************************confirm   modal******************************* -->
<!-- confirm视频请求 start -->
<div class="modal fade" tabindex="-1" id="cofirmVedioRequestModal">
    <!-- 窗口声明 -->
    <div class="modal-dialog">
        <!-- 内容声明 -->
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
         
            <div class="modal-header">
              <!--   <button class="close" data-dixsiss="modal">&times;</button> -->
                <h4 class="modal-title">用户视频请求</h4>
            </div>
             <div class="modal-body text-center">
            
                <div class="row text-right">
                    <label  class="col-xs-4 control-label"></label>
                    <div class="col-xs-4">
                       <img id="requestUserIcon" style="width: 100%;"   class=" img-rounded image-responsive" src="${pageContext.request.contextPath}/images/backimage.jpg" alt="头像"/> 
                    </div>
                </div>
                <br>
                
                
            </div>
            <div class="modal-body text-center">
            
                <div class="row text-right">
                    <label  class="col-xs-4 control-label">用户昵称：</label>
                    <div class="col-xs-4">
                         <label  class="col-xs-4 control-label" id="requestName"></label>
                    </div>
                </div>
                <br>
                
                
            </div>
              <!-- confirm -->
            <div class="modal-footer">
            <!-- 用于缓存收到的视频请求消息 -->
            	<input type="hidden" id="messageTemp">
                <button class="btn btn-success" onclick="accept()" type="button">
                <i class="glyphicon glyphicon-ok"></i>接受</button>
                <button class="btn btn-danger  " onclick="reject('对方已拒绝您的视频请求')" type="button"  style="background-color: red;">
                <i class="glyphicon glyphicon-remove " ></i>拒绝</button>
            </div>
        </div>
    </div>
</div>
<!-- confirm视频请求  end -->
<!-- music  加载 start-->


<audio src="${pageContext.request.contextPath}/sound/callin.mp3"   controls="controls"  id="callin" hidden="true">
</audio>

<audio src="${pageContext.request.contextPath}/sound/callout.mp3"  controls="controls"  id="callout" hidden="true">
</audio>
<audio src="${pageContext.request.contextPath}/sound/messageArrive.mp3"  controls="controls"  id="messageArrive" hidden="true">
</audio>
<!-- music  加载 end -->
</body>

</html>
