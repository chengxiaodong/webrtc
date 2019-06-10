<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE html>
<html>
<head>
	<title>天天视频通话</title>
	<meta charset="utf-8">
	
	
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery.md5.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layer.css" />
	<script>
		
		$(function(){
			//页面加载完成后刷新验证码
			$('#frmLogin').bootstrapValidator({
				feedbackIcons: {
					valid: 'glyphicon glyphicon-ok',
					invalid: 'glyphicon glyphicon-remove',
					validating: 'glyphicon glyphicon-refresh'
				},
				fields:{
					loginName:{
						validators:{
							notEmpty:{
								message:'用户名不能为空'
							}
						}
					},
					password:{
						validators:{
							notEmpty:{
								message:'密码不能为空'
							}
						}
					}
				}
			});
			
			
			//服务端提示消息;登录失败
			let errorMsg='${errorMsg}';
			if(errorMsg!=''){
				layer.msg(errorMsg,{
					time:2000,
					skin:'errorMsg'
				});
			}
		});
		
	function login(){
			
			
			//alert($("input[name='password']").val()); 
			//alert($.md5($("input[name='password'].val()")));
			//console.log($.md5($("input[name='password']").val()));
		 	location.href="${pageContext.request.contextPath}/user/login?loginName="+$("input[name='loginName']").val()+
					"&password="+$.md5($("input[name='password']").val());  
		}
	
	</script>
</head>
<body>
<!-- 使用自定义css样式 div-signin 完成元素居中-->
<div class="container div-signin">
	<div class="panel panel-primary div-shadow">
		<!-- h3标签加载自定义样式，完成文字居中和上下间距调整 -->
		<div class="panel-heading">
			<h3>天天视频通话 后台</h3>
			<span>Video Calls Every Day</span>
		</div>
		<div class="panel-body">
			<!-- login form start -->
			<form action="${pageContext.request.contextPath}/user/login" class="form-horizontal" method="post" id="frmLogin">
				<div class="form-group">
					<label class="col-sm-3 control-label">用户名：</label>
					<div class="col-sm-9">
						<input class="form-control" type="text" placeholder="请输入账号" name="loginName">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
					<div class="col-sm-9">
						<input class="form-control" type="password" placeholder="请输入密码" name="password">
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-3">
					</div>
					<div class="col-sm-9 padding-left-0">
						<div class="col-sm-4">
							<input  type="button" class="btn btn-primary btn-block" value="登&nbsp;&nbsp;陆" onclick="login()"/>
						</div>
						<div class="col-sm-4">
							<button type="reset" class="btn btn-primary btn-block">重&nbsp;&nbsp;置</button>
						</div>
						
					</div>
				</div>
			</form>
			<!-- login form end -->
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
