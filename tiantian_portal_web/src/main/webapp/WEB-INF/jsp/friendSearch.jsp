<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>天天视频通话</title>
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/bootstrap.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
      <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>
    <script src="${pageContext.request.contextPath}/js/friendsearch.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/friendsearch.css" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layer.css" />
    <script type="text/javascript">
    	function search(){                                               
    		location.href='${pageContext.request.contextPath}/user/search/searchUserbyNameOrphoneOremail?uid=${param.uid}&key='+$('#searchInput').val();
    	};
    	function onKeyDown(event){
    		                var e = event || window.event || arguments.callee.caller.arguments[0];
    		                if(e && e.keyCode==27){ // 按 Esc 
    		                    //要做的事情
    		                }
    		                if(e && e.keyCode==113){ // 按 F2 
    		                     //要做的事情
    		                }            
    		                if(e && e.keyCode==13){ // enter 键
    		                	search();
    		                }
    		                
    		            }
    	$(function(){
    		$('#searchInput').focus();
    	});
    	
    	function sendFreindRequest(oid,btn){
    		btn.disabled=true;
    		   $.post(
               		'${pageContext.request.contextPath}/message/sendMessage',
               		{'oid':oid,
               		 'message':'我想加你好友，请通过。。。'	,
               		 'uid':${param.uid},
               		 'type':3
               		},
                       function(result){
               			
               			// 返回值为@RequestBody  String 时需要解析成JSON对象   result=JSON.parse(result);
                           if(result.status==1){
                           	//	消息发送成功
                             layer.msg('好友请求已发送',{
                                time:2000,
                                skin:'successMsg'
                            },function(){
                                //重新加载数据
                            });
                            
                           }else{
                           	//消息获取失败
                           	alert(" 消息获取失败 "+result);
                           }
    					
                       }
                   );
    	}
    </script>
</head>

<body >
	
<!-- 搜索框 -->
  <div id="wrapper">
	<div class="container">
		<div class="input-group">
		<input type="text" class="form-control input-lg" id="searchInput"  placeholder="请输入好友昵称/电话号码/邮箱" onkeydown="onKeyDown(event)"><span class="input-group-addon btn btn-primary" onclick="search()">搜索</span>
		</div>
	</div>
<c:if test="${recommendFriends!=null&&fn:length(recommendFriends) > 0}">
	<!-- 展示搜索结果 -->
<div class="panel panel-default" style="margin-top: 12px;">
    <div class="panel-heading">
        <h3 class="panel-title">您可能认识的人</h3>
    </div>
    <div class="panel-body">
        <br>
        <div class="show-list text-center">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
              
                <c:choose> 
			
			     <c:when test="${recommendFriends!=null&&fn:length(recommendFriends) > 0}">    <!--如果 --> 
						 <tr class="text-danger">
						 		<th>头像</th>
			                    <th class="text-center">昵称</th>
			                    <th class="text-center">电话</th>
			                    <th class="text-center">邮箱</th>
			                    <th class="text-center">添加</th>
			                </tr>
				 </c:when>      
			
			     <c:otherwise>  <!--否则 -->    
							<tr class="text-danger">
			                    <th class="text-center">您搜索的用户不存在！</th>
			                </tr>
			 	 </c:otherwise> 
			
			</c:choose>
                </thead>
                <tbody id="tb">         
                	
                    <c:forEach items="${recommendFriends}" var="user">
                        <tr >
								<td style="vertical-align: middle;">
		                         <img id="requestUserIcon" style="width: 50px;height:50px;"   class=" img-rounded image-responsive" 
		                         src="${user.profilephoto}" alt="头像"/> 
		                       	 </td>
                            <td style="vertical-align: middle;">${user.username}</td>
                            <td style="vertical-align: middle;">${user.phone}</td>
                             <td style="vertical-align: middle;">${user.email}</td>
                             
                            <td class="text-center" style="vertical-align: middle;">
                                <input type="button" class="btn btn-warning btn-sm doProTypeModify" value="添加" onclick="sendFreindRequest(${user.id},this)">
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
            <!-- 使用bootstrap-paginator实现分页 -->
            <ul id="pagination"></ul>
        </div>
    </div>
</div>
</c:if>
</div>



</body>

</html>
