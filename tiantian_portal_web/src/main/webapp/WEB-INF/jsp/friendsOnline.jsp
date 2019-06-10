<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
    <script >
   
    $(function(){
    	//初始化bootstrapPaginator分页插件
    	$('#pagination').bootstrapPaginator(
        		{
            bootstrapMajorVersion:3,
            currentPage:${pageInfo.pageNum},
            totalPages:${pageInfo.pages},
            pageUrl:function(type,page, current){
                 return '${pageContext.request.contextPath}/page/friendsOnline?uid=${uid}&pageNum='+page;
            },
            itemTexts: function (type, page, current) {
                switch (type) {
                    case "first":
                        return "首页";
                    case "prev":
                        return "上一页";
                    case "next":
                        return "下一页";
                    case "last":
                        return "末页";
                    case "page":
                        return page;
                }
            }
        });
    	
    });

    
   

	
	function vedioRequest(oid,btn){
		//将操作的一组按钮设置为false
		$(btn).attr('disabled',true);
		 		//当使用192.168.43.30会报错，改成localhost错误会消失
			  //window.location.href='https://192.168.43.30:8443/msg?requestId=${sessionScope.user.id}'+'&targetId='+oid;
			/* 
			
			
			*******************************************************************************************************
			待改 localhost====>192.168.43.30
			*******************************************************************************************************
			
			*/
			
			
			//打开菜单栏的挂断按钮
			parent.$('#stopPhoneIcon').css('display','block');
			//播放呼出音乐
			parent.calloutSound();
			//会在RtcVedio中发送请求信息
		    location.href='${pageContext.request.contextPath}/video/msg?requestId=${param.uid}'+'&targetId='+oid;
			//.location='https://192.168.43.30:8443/msg?requestId=${param.uid}'+'&targetId='+oid;
			//window.open('https://192.168.43.30:8443/msg?requestId=${param.uid}'+'&targetId='+oid);
	};
	
	
    </script>
</head>

<body >
  <div id="wrapper">
<!-- 展示搜索结果 -->
<div class="panel panel-default" id="userSet">
    <div class="panel-heading">
        <h3 class="panel-title">在线好友</h3>
    </div>
    <div class="panel-body">
        <br>
        <div class="show-list text-center">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
              
                <c:choose> 
			
			     <c:when test="${pageInfo.list!=null&&fn:length(pageInfo.list) > 0}">    <!--如果 --> 
						 <tr class="text-danger">
							 <th class="text-center">头像</th>
			                    <th class="text-center">用户名</th>
			                    <th class="text-center">操作</th>
			                </tr>
				 </c:when>      
			
			     <c:otherwise>  <!--否则 -->    
							<tr class="text-danger">
			                    <th class="text-center">您没有在线的好友！</th>
			                </tr>
			 	 </c:otherwise> 
			
			</c:choose>
                </thead>
                <tbody id="tb">         
                    <c:forEach items="${pageInfo.list}" var="friend" varStatus="status">
                        <tr>
                        <td style="vertical-align: middle;">
		                         <img id="requestUserIcon" style="width: 50px;height:50px;"   class=" img-rounded image-responsive" 
		                         src="${friend.profilephoto}" alt="头像"/> 
		                 </td>
                        
                            <td style="vertical-align: middle;">${friend.username}</td>
			               		 <td class="text-center" style="vertical-align: middle;">
                                 <input type="button" class="btn btn-warning btn-sm doProTypeModify message${status.count}" 
                                       value="视频"  onclick="vedioRequest(${friend.id},this)" >
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
</div>

</body>

</html>

