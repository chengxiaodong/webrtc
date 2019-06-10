<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
	<title>天天视频通话</title>
	<meta charset="utf-8">
	
	
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-select.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-select.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layer.css" />
	<script>
		
		$(function(){
			
			
			$('#updateModalForm').bootstrapValidator({
				feedbackIcons: {
					valid: 'glyphicon glyphicon-ok',
					invalid: 'glyphicon glyphicon-remove',
					validating: 'glyphicon glyphicon-refresh'
				},
				fields:{
					
					password:{
						validators:{
							notEmpty:{
								message:'密码不能为空'
							},
							stringLength: {
							    min: 8,
								max: 18,
								message: '密码必须在8-18个字符之间'
							}
						}
					},
					
					levelselector:{
						validators:{
							/* notEmpty:{
								message:'请选择权限'
							} */
						}
					}
				}
			});
				
				$('#addModalForm').bootstrapValidator({
					feedbackIcons: {
						valid: 'glyphicon glyphicon-ok',
						invalid: 'glyphicon glyphicon-remove',
						validating: 'glyphicon glyphicon-refresh'
					},
					fields:{
						
						username:{
							validators:{
								notEmpty:{
									message:'用户名不能为空'
								},
								stringLength: {
								    min: 8,
									max: 20,
									message: '密码必须在8-20个字符之间'
								}
							}
						},
						password:{
							validators:{
								notEmpty:{
									message:'密码不能为空'
								},
								stringLength: {
								    min: 8,
									max: 18,
									message: '密码必须在8-18个字符之间'
								}
							}
						},
						phone:{
							validators:{
								notEmpty:{
									message:'密码不能为空'
								},
								regexp: {
			                        regexp: /^1\d{10}$/ ,
			                        message: '请输入正确的11位手机号'
			                    }
							}
						},
						email:{
							validators:{
								notEmpty:{
									message:'密码不能为空'
								},emailAddress: {
			                        message: '邮箱地址格式有误'
			                    }
								
							}
						},
						
					}
			});
			
		/* 	$.post(
            		'${pageContext.request.contextPath}/message/sendMessage',
            				{
            			'uid':${user.id},
            			'oid':message.uid,
            			'type':7,
            			'message':tip
            				},
                    function(result){
                        if(result.status==1){
                        	//	消息获取成功
                           
    		           //alert("已拒绝视频请求！");
                        }else{
                        	//消息获取失败
                        }
				
                    }
                ); */
				//服务端提示消息;登录失败
			
				let errorMsg='${requestScope.errorMsg}';
				if(errorMsg!=''){
					
					/* layer.msg(errorMsg,{
						offset: '300px',
						time:2000
					}); */
					alert(errorMsg);
				}
				
			
		});
				//btn,rowClass===userId
		function updateUser(btn,rowClass){
			//将旧数据填充到modal框
			var username_input=$('.'+rowClass+" .username");
			$('#updateModal #username ').val(username_input.html().trim());
			$('#updateModal #uid').val(rowClass);
			
			$("#updateModal").modal("show");
		}
		function updateUserExcute(){
			if(!$('#updateModalForm').data('bootstrapValidator').isValid()){  
				layer.msg('请填写完整的修改信息');
		         return ;  
		      } 
			//提交数据
			$.post(
            		'${pageContext.request.contextPath}/user/user/update',
            				{
            			'password':$('#updateModal #password').val(),
            			
            			'email':$('#uid').val()
            				},
                    function(result){
                        if(result.status==1){
                        	//	消息获取成功
                       // alert(result.data.username);
                        	layer.msg('密码修改成功',{
                        		skin:'successMsg',
                        		offset: '300px',
                        		time:2000
                        	},function(){
                        		$("#updateModal").modal("hide");
                        	});
                        	
                        }else{
                        	//消息获取失败
                            alert(result.message);

                        }
				
                    }
                );
		}
		
		
		
		//btn,rowClass===userId
		function addUser(btn){
			//将旧数据填充到modal框
			
			$("#addModal").modal("show");
		}
		function addUserExcute(){
			if(!$('#addModalForm').data('bootstrapValidator').isValid()){  
				layer.msg('请填写完整的修改信息');
		         return ;  
		      } 
			//添加用户提交数据
		
			location='${pageContext.request.contextPath}/user/user/add?username='+$('#addModalForm #username').val()
					+' &password='+$('#addModalForm #password').val()
					+' &phone='+$('#addModalForm #phone').val()
					+' &email='+$('#addModalForm #email').val();
		}
		
		//删除
		function deleteSysuser(btn,uid){
			var btn=$(btn);
			//提交数据
			$.post(
            		'${pageContext.request.contextPath}/user/user/delete',
            				{
            			'uid':uid
            				},
                    function(result){
                        if(result.status==1){
                        	//	消息获取成功
                        	layer.msg('删除成功',{
                        		skin:'successMsg',
                        		offset: '300px',
                        		time:2000
                        	},function(){
                        		$('.'+uid).css('display','none');
                        	});
                        }else{
                        	//消息获取失败
                            alert(result.message);

                        }
				
                    }
                );
		}
	</script>
</head>
<body>
  <div id="wrapper">
<!-- 展示搜索结果 -->
<div class="panel panel-default" >
    <div class="panel-heading">
        <h3 class="panel-title">普通用户</h3>
    </div>
    <div class="panel-body">
        <br>
        <div class="show-list text-center">
        	<div style="text-align: left;margin-bottom: 12px;">
        		<button class="btn btn-primary btn-success" onclick="addUser(this)"> <i class="glyphicon glyphicon-plus" ></i> 添加</button>
        	</div>
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
              
                <c:choose> 
			
			     <c:when test="${users!=null&&fn:length(users) > 0}">    <!--如果 --> 
						 <tr class="text-danger">
							 <th class="text-center">用户名</th>
			                    <th class="text-center">密码</th>
			                    <th class="text-center">手机</th>
			                    <th class="text-center">邮箱</th>
			                    <th class="text-center">操作</th>
			                </tr>
				 </c:when>      
			
			     <c:otherwise>  <!--否则 -->    
							<tr class="text-danger">
			                    <th class="text-center">没有记录！</th>
			                </tr>
			 	 </c:otherwise> 
			
			</c:choose>
                </thead>
                <tbody id="tb">         
                    <c:forEach items="${users}" var="user" varStatus="status">
                    <!-- 将userId作为每行的唯一标识符，并且作为数据跟新的主键 -->
                        <tr class="${user.id}">
                        <td style="vertical-align: middle;" class="username">
                              ${user.username}</td>
                          <td style="vertical-align: middle;" class="password">
                              ********
						 </td>
						  <td style="vertical-align: middle;" class="phone">
                               ${user.phone}
						 </td>
						  <td style="vertical-align: middle;" class="email">
                               ${user.email}
						 </td>
					
				              
		                 
                           
			               		 <td class="text-center" style="vertical-align: middle;">
                                 <input type="button" class="btn btn-warning btn-sm  " 
                                       value="修改密码"  onclick="updateUser(this,${user.id})"/>
                                       
                                         <input type="button" class="btn btn-warning btn-sm  " 
                                       value="删除"  onclick="deleteSysuser(this,${user.id})"/>
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

<!-- 页尾 版权声明 -->
<div class="container">
	<div class="col-sm-12 foot-css">
		<p class="text-muted credit text-center">
			Copyright 南京工程学院
		</p>
	</div>
</div>
    
<!-- 普通管理员时 start -->
<div class="modal fade" tabindex="-1" id="updateModal" >
    <!-- 窗口声明 -->
    <div class="modal-dialog">
        <!-- 内容声明 -->
        <form id="updateModalForm"  >
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
         
            <div class="modal-header">
            <!-- 去除关闭按钮 -->
               <!--  <button class="close" data-dismiss="modal">&times;</button> -->
                <h4 class="modal-title">修改用户密码</h4>
            </div>
            <div class="modal-body text-center">
            
                <div class="row text-right">
                	<input type="hidden" id="uid">
                    <label for="name" class="col-sm-4 control-label" >用户名：</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="username" name="username" disabled="disabled">
                    </div>
                </div>
                <br>
                
                <div class="row text-right">
                    <label for="password1" class="col-sm-4 control-label">登录密码：</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="password" name="password" placeholder="请输入8-20位密码">
                    </div>
                </div>
                  <br>
                  
                 
               
               
                <br>
              
            <div class="modal-footer">
                <button class="btn btn-primary " onclick="updateUserExcute()" type="button">提交</button>
                <button class="btn btn-primary  " data-dismiss="modal" onclick="javascript:void(0)" type="button">取消</button>
            </div>
        </div>
        </div>
        </form>
    </div>
    </div>
    
    
<!-- 添加管理员时 start -->
<div class="modal fade" tabindex="-1" id="addModal" >
    <!-- 窗口声明 -->
    <div class="modal-dialog">
        <!-- 内容声明 -->
        <form id="addModalForm"   >
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
         
            <div class="modal-header">
            <!-- 去除关闭按钮 -->
               <!--  <button class="close" data-dismiss="modal">&times;</button> -->
                <h4 class="modal-title">添加用户</h4>
            </div>
            <div class="modal-body text-center">
            
                <div class="row text-right">
                    <label for="name" class="col-sm-4 control-label" >用户名：</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
                    </div>
                </div>
                <br>
                
                <div class="row text-right">
                    <label for="password1" class="col-sm-4 control-label">登录密码：</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="password" name="password" placeholder="请输入8-20位密码">
                    </div>
                </div>
                  <br>
                  
                  <div class="row text-right">
                    <label for="password1" class="col-sm-4 control-label">手机号码：</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="phone" name="phone"  placeholder="请输入11位手机号码">
                    </div>
                </div>
                  <br>
                  
                  
                   <div class="row text-right">
                    <label for="password1" class="col-sm-4 control-label">邮箱：</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="email" name="email"  placeholder="请输入邮箱">
                    </div>
                </div>
                  <br>
                    
                </div>
                <br>
               
                <br>
              
            <div class="modal-footer">
                <button class="btn btn-primary " onclick="addUserExcute()" type="button">提交</button>
                <button class="btn btn-primary  " data-dismiss="modal" onclick="javascript:void(0)" type="button">取消</button>
            </div>
        </div>
        </div>
        </form>
    </div>
    </div>    
    
</body>
</html>
