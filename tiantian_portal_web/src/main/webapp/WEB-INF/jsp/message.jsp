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
                 return '${pageContext.request.contextPath}/page/message?uid=${uid}&pageNum='+page;
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
    
    
    function markMessage(btn,messageId){
    	btn=$(btn);
    	btn.attr("disabled",true);
    	 $.post( 
				 //好友请求消息标为已读
	           		'${pageContext.request.contextPath}/inform/mark',
	           		{
	           			'messageId':messageId,
	           			'readed':1
	           		 
	           		},
	                   function(result){
	                       if(result.status==1){
	                    	  btn.val('已读');
	                        
	                       }else{
	                       	//消息获取失败
	                       	alert("消息获取失败"+result);
	                       }
	                       
	                   }
	               );
    }
    
   

	
	function sendFreindResponseAccept(oid,btnClass,messageId){
		//将操作的一组按钮设置为false
		btnClass='.message'+btnClass;
		$(btnClass).attr('disabled',true);
		 $.post( 
				 //好友请求消息标为已读
	           		'${pageContext.request.contextPath}/inform/mark',
	           		{
	           			'messageId':messageId,
	           			'readed':1
	           		 
	           		},
	                   function(result){
	                       if(result.status==1){
	                    	   //向对方发送请求结果
	                        	 $.post(
	                                		'${pageContext.request.contextPath}/message/sendMessage',
	                                		{
	                                			'uid':${param.uid},
	                                			'oid':oid,
	                                		 'message':'我们已成为好友，我们视频吧。。。'	,
	                                		 'accept':true,
	                                		 //同意请求
	                                		 'type':4
	                                		},
	                                        function(result){
	                                            if(result.status==1){
	                                            	//	消息发送成功
	                                              layer.msg('已加为好友',{
	                                            time:2000,
	                                            skin:'successMsg'
	                                        },function(){
	                                            //重新加载数据
	                                        })
	                                             
	                                            }else{
	                                            	//消息获取失败
	                                            	alert("消息获取失败"+result);
	                                            }
	                                            
	                                        }
	                                    );
	                        
	                       }else{
	                       	//消息获取失败
	                       	alert("消息获取失败"+result);
	                       }
	                       
	                   }
	               );
		  
	};
	
	function sendFreindResponseReject(oid,btnClass,messageId){
		//将操作的一组按钮设置为false
		btnClass='.message'+btnClass;
		$(btnClass).attr('disabled',true);
		 $.post( 
				 //好友请求消息标为已读
	           		'${pageContext.request.contextPath}/inform/mark',
	           		{
	           			'messageId':messageId,
	           			'readed':1
	           		 
	           		},
	                   function(result){
	                       if(result.status==1){
	                    	   $.post(
	                           		'${pageContext.request.contextPath}/message/sendMessage',
	                           		{'uid':${param.uid},
                            		'oid':oid,
	                           		 'message':'对方拒绝加为好友。。。'	,
	                           		 'accept':false,
	                           		 'type':4
	                           		},
	                                   function(result){
	                                       if(result.status==1){
	                                       	//	消息发送成功
	                                         layer.msg('已拒绝加为好友',{
	                                            time:2000,
	                                            skin:'successMsg'
	                                        },function(){
	                                            //重新加载数据
	                                        })
	                                        
	                                       }else{
	                                       	//消息获取失败
	                                       }
	                   					
	                                   }
	                               );
	                        
	                       }else{
	                       	//消息获取失败
	                       	alert("消息获取失败"+result);
	                       }
	                       
	                   }
	               );
		  
	}
    </script>
</head>

<body >
  <div id="wrapper">
<!-- 展示搜索结果 -->
<div class="panel panel-default" id="userSet">
    <div class="panel-heading">
        <h3 class="panel-title">我的消息</h3>
    </div>
    <div class="panel-body">
        <br>
        <div class="show-list text-center">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
              
                <c:choose> 
			
			     <c:when test="${pageInfo.list!=null&&fn:length(pageInfo.list) > 0}">    <!--如果 --> 
						 <tr class="text-danger">
			                    <th class="text-center">发送方</th>
			                    <th class="text-center">消息</th>
			                    <th class="text-center">类型</th>
			                    <th class="text-center">发送时间</th>
			                    <th class="text-center">操作</th>
			                </tr>
				 </c:when>      
			
			     <c:otherwise>  <!--否则 -->    
							<tr class="text-danger">
			                    <th class="text-center">您没有未读的消息！</th>
			                </tr>
			 	 </c:otherwise> 
			
			</c:choose>
                </thead>
                <tbody id="tb">         
                    <c:forEach items="${pageInfo.list}" var="message" varStatus="status">
                        <tr>
                            <td>${message.ouser.username}</td>
                            <td>${message.message}</td>
<!--                              /**
 * type
 * 3表示此条信息为 好友请求信息
 * 4表示此条信息为 好友请求返回结果信息
 * 5表示此条信息为 普通信息
 * @author 晓东
 *
 */ -->
                 <c:choose> 
			
			     <c:when test="${message.type == 3}">    <!--如果 --> 
						
			               		 <td>好友请求信息</td>
			               
				 </c:when>      
				
				<c:when test="${message.type == 4}">    <!--如果 --> 
						 
			               		 <td>好友请求返回结果</td>
			             
				 </c:when>  
				 
			     <c:otherwise>  <!--否则 -->    
							
			               		 <td>普通信息</td>
			               
			 	 </c:otherwise> 
               </c:choose>
                             
                             <td>${message.date}</td>
             <c:choose> 
			
			     <c:when test="${message.type == 3 && message.readed==0}">    <!--如果 --> 
						
			               		 <td class="text-center">                                                                                                                   
                                <input type="button" class="btn btn-warning btn-sm doProTypeModify message${status.count}" value="同意"  onclick="sendFreindResponseAccept(${message.ouser.id},${status.count},${message.messageId})">
                                 <input type="button" class="btn btn-warning btn-sm doProTypeModify message${status.count}" value="拒绝"  onclick="sendFreindResponseReject(${message.ouser.id},${status.count},${message.messageId})">
                            </td>
			               
				 </c:when>      
				
				<c:when test="${message.type == 4 && message.readed==0}">    <!--如果 --> 
						
			               		 <td>
			               		 <input type="button" class="btn btn-warning btn-sm doProTypeModify message${status.count}" value="标为已读"  
			               		 onclick="markMessage(this,${message.messageId})">
			               		 </td>
			               
				 </c:when>  
				 
			     <c:otherwise>  <!--否则 -->    
							
			               		 <td>已读</td>
			                
			 	 </c:otherwise> 
               </c:choose>
                            
               
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
