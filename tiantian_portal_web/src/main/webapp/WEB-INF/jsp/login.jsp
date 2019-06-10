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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery.md5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layer.css" />
	<script>
		//重新加载验证码
		function reloadImage(){
			$('#randCode').attr('src','${pageContext.request.contextPath}/login/code/image?time='+new Date().getTime());
			$('#code').val('');
		}

		$(function(){
			//页面加载完成后刷新验证码
			reloadImage();
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
					},
					code:{
						validators:{
							notEmpty:{
								message:'请输入验证码'
							},
							remote:{
								url:'${pageContext.request.contextPath}/login/code/checkCode',
								message:'验证码不正确'
							} 
						}
					}
				}
			});
			
			
			//服务端提示消息
			let errorMsg='${errorMsg}';
			if(errorMsg!=''){
				layer.msg(errorMsg,{
					time:2000,
					skin:'errorMsg'
				});
			}
		});
		function register(){
			location.href="${pageContext.request.contextPath}/page/register";
		}
		function login(){
			
					console.log($.md5($("input[name='password']").val()));
					var md5pass=$.md5($("input[name='password']").val());
					var loginName=$("input[name='loginName']").val();
					var code=$("input[name='code']").val();
		 	 document.write("<form action='${pageContext.request.contextPath}/user/login' method='post' name='loginfm' style='display:none'>");
		        document.write("<input type=hidden name='loginName' value="+loginName+">");//参数1
		        document.write("<input type=hidden name='password' value="+md5pass+">");//参数2
		        document.write("<input type=hidden name='code' value="+code+">");//参数2
		        document.write("</form>");
		        document.loginfm.submit(); 
		}
	</script>
</head>
<body>
<!-- 使用自定义css样式 div-signin 完成元素居中-->
<div class="container div-signin">
	<div class="panel panel-primary div-shadow">
		<!-- h3标签加载自定义样式，完成文字居中和上下间距调整 -->
		<div class="panel-heading">
			<h3>天天视频通话</h3>
			<span>Video Calls Every Day</span>
		</div>
		<div class="panel-body">
			<!-- login form start -->
			<form action="#"  class="form-horizontal" id="frmLogin">
				<div class="form-group">
					<label class="col-sm-3 control-label">用户名：</label>
					<div class="col-sm-9">
						<input class="form-control" type="text" placeholder="请输入用户名" name="loginName">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
					<div class="col-sm-9">
						<input class="form-control" type="password" placeholder="请输入密码" name="password">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">验证码：</label>
					<div class="col-sm-4">
						<input class="form-control" type="text" placeholder="验证码" id="code" name="code">
					</div>
					<div class="col-sm-2">
						<!-- 验证码 -->
						<img class="img-rounded"  id="randCode" style="height: 32px; width: 70px;"/>
					</div>
					<div class="col-sm-2">
						<button type="button" class="btn btn-link" onclick="reloadImage()">看不清</button>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-3">
					</div>
					<div class="col-sm-9 padding-left-0">
						<div class="col-sm-4">
							<input type="button"  class="btn btn-primary " onclick="login()" value="登&nbsp;&nbsp;陆">
						</div>
						<div class="col-sm-4">
							<button type="reset" class="btn btn-primary btn-block">重&nbsp;&nbsp;置</button>
						</div>
						<div class="col-sm-4">
							<button type="button" class="btn btn-link btn-block" onclick="register()">用户注册</button>
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
