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
                 return '${pageContext.request.contextPath}/page/friendsManage?uid=${uid}&pageNum='+page;
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
    
   

	//删除好友
	function deleteRequest(oid,btnClass){
		//将操作的一组按钮设置为false
		$(btnClass).attr('disabled',true);
		
		    $.post(
           		'${pageContext.request.contextPath}/friend/delete',
           		{
           			'uid':${param.uid},
           			'oid':oid
           		},
                   function(result){
                       if(result.status==1){
                       	//	消息发送成功
                         layer.msg(result.message,{
                            time:2000,
                            skin:'successMsg'
                        },function(){
                            //重新加载数据
                        	$('#deleteBtn').val('已被删除');
                        	 $('#deleteBtn').attr('class','btn  btn-sm disabled');
                        })
                        
                       }else{
                       	//消息获取失败
                       	alert('好友删除失败！');
                       }
                       
                   }
               ); 
	};
	
	//拉黑好友
		function blackFriend(btn,oid){
		//将操作的一组按钮设置为false
		$(btn).css('disabled',true);
		
		    $.post(
           		'${pageContext.request.contextPath}/black/insertBlack',
           		{
           			'uid':${param.uid},
           			'oid':oid
           		},
                   function(result){
                       if(result.status==1){
                       	//	消息发送成功
                         layer.msg(result.message,{
                            time:2000,
                            skin:'successMsg'
                        },function(){
                            //重新加载数据
                        	$(btn).val('已拉黑');
                        	$(btn).attr('class','btn  btn-sm disabled');
                            //显示可点击
                        })
                        
                       }else{
                       	//拉黑失败
                    	   $(btn).css('disabled',false);
                       }
                       
                   }
               ); 
	};
	//取消好友拉黑
	//id是tbBlack的主键
	function cancelBlackFriend(btn,id){
	//将操作的一组按钮设置为false
	$(btn).css('disabled',true);
	
	    $.post(
       		'${pageContext.request.contextPath}/black/cancelBlack',
       		{
       			'id':id
       		},
               function(result){
                   if(result.status==1){
                   	//	消息发送成功
                     layer.msg(result.message,{
                        time:2000,
                        skin:'successMsg'
                    },function(){
                        //重新加载数据
                    	$(btn).val('已取消拉黑');
                        $(btn).attr('class','btn  btn-sm disabled');
                    })
                    
                   }else{
                   	//消息获取失败
                   	 layer.msg(result.message,{
                        time:2000,
                        skin:'successMsg'
                    },function(){
                        //重新加载数据
                    	$(btn).css('disabled',false);
                    })
                   }
                   
               }
           ); 
};
    </script>
</head>

<body >
  <div id="wrapper">
<!-- 展示搜索结果 -->
<div class="panel panel-default" >
    <div class="panel-heading">
        <h3 class="panel-title">好友管理</h3>
    </div>
    <div class="panel-body">
        <br>
        <div class="show-list text-center">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
              
                <c:choose> 
			
			     <c:when test="${pageInfo.list!=null&&fn:length(pageInfo.list) > 0}">    <!--如果 --> 
						 <tr class="text-danger">
						 		<th>头像</th>
			                    <th class="text-center">用户名</th>
			                    <th class="text-center">操作</th>
			                </tr>
				 </c:when>      
			
			     <c:otherwise>  <!--否则 -->    
							<tr class="text-danger">
			                    <th class="text-center">您还没有好友，赶快去添加吧！</th>
			                </tr>
			 	 </c:otherwise> 
			
			</c:choose>
                </thead>
                <tbody id="tb">         
                    <c:forEach items="${pageInfo.list}" var="frienddto" varStatus="status">
                        <tr>
		                       <td style="vertical-align: middle;">
		                         <img  style="width: 50px;height:50px;"   class=" img-rounded image-responsive" 
		                         src="${frienddto.tbUser.profilephoto}" alt="头像"/> 
		                       	 </td>
		                       	 
                            <td style="vertical-align: middle;">${frienddto.tbUser.username}</td>
			               		 <td class="text-center" style="vertical-align: middle;">
			               		 
                                 <input type="button" id="deleteBtn" class="btn btn-danger btn-sm  message${status.count}" 
                                       value="删&emsp;&emsp;除"  onclick="deleteRequest(${frienddto.tbUser.id},'.message${status.count}')"/>

								<c:choose>
									<c:when test="${not empty frienddto.tbBlack.id}">
										<input type="button" class="btn btn-success btn-sm message${status.count} " 
                                       value="取消拉黑"  onclick="cancelBlackFriend(this,${frienddto.tbBlack.id})"/>
									</c:when>
									<c:otherwise>
										<input type="button" class="btn btn-warning btn-sm message${status.count}" 
                                       value="拉&emsp;&emsp;黑"  onclick="blackFriend(this,${frienddto.tbUser.id})"/>
									</c:otherwise>
								</c:choose>
								
            			      
            			        
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

