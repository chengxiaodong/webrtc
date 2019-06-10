<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>用户注册</title>
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/bootstrap.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css" />
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/register.css" />
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mycss.css" />
    	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/fileinput/fileinput.css" />
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
    <script src="${pageContext.request.contextPath}/layer/layer.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layer.css" />
    <script src="${pageContext.request.contextPath}/js/fileinput/fileinput_upload_none.js"></script>
    <script src="${pageContext.request.contextPath}/js/fileinput/zh.js"></script>
    <script>
	
    $(function(){
    	$("#registerModal").modal("show");
    	//*****************************fileupload start*******************
//初始化fileupload
   $(".myfile").fileinput({
            //上传的地址
            uploadUrl:"${pageContext.request.contextPath}/food/uploadFile",
            uploadAsync : true, //默认异步上传
            showUpload : false, //是否显示上传按钮,跟随文本框的那个
            showRemove : false, //显示移除按钮,跟随文本框的那个
            showCaption : false,//是否显示标题,就是那个文本框
            showPreview : true, //是否显示预览,不写默认为true
            dropZoneEnabled : false,//是否显示拖拽区域，默认不写为true，但是会占用很大区域，
            maxFileCount : 1, //表示允许同时上传的最大文件个数
            enctype : 'multipart/form-data',
            validateInitialCount : true,
            previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
            msgFilesTooMany : "请先删除已选择的图片！",
            allowedFileTypes : [ 'image' ],//配置允许文件上传的类型
            allowedPreviewTypes : [ 'image' ],//配置所有的被预览文件类型
            allowedPreviewMimeTypes : [ 'jpg', 'png' ],//控制被预览的所有mime类型
            language : 'zh'
        })
        //异步上传返回结果处理
        $('.myfile').on('fileerror', function(event, data, msg) {
            console.log("fileerror");
            console.log(data);
        });
        //异步上传返回结果处理
        $(".myfile").on("fileuploaded", function(event, data, previewId, index) {
            console.log("fileuploaded");
            var ref = $(this).attr("data-ref");
            $("input[name='" + ref + "']").val(data.response.url);
     
        });
     
        //同步上传错误处理
        $('.myfile').on('filebatchuploaderror', function(event, data, msg) {
            console.log("filebatchuploaderror");
            console.log(data);
        });
     
        //同步上传返回结果处理
        $(".myfile").on("filebatchuploadsuccess",
                function(event, data, previewId, index) {
                    console.log("filebatchuploadsuccess");
                    console.log(data);
                });
     
        //上传前
        $('.myfile').on('filepreupload', function(event, data, previewId, index) {
            console.log("filepreupload");
        });
        //*****************************fileupload end*******************
    
//
    	$('#registerForm').bootstrapValidator({
			feedbackIcons: {
				valid: 'glyphicon glyphicon-ok',
				invalid: 'glyphicon glyphicon-remove',
				validating: 'glyphicon glyphicon-refresh'
			},
			fields:{
				name :{
					validators:{
						notEmpty:{
							message:'昵称不能为空'
						},
						stringLength: {
						    min: 6,
							max: 30,
							message: '用户名必须在6-30个字符之间'
						},
						
						
						remote: {
	                        url: '/user/check/name',
	                        message:"昵称已被占用",
	                        type: "post",
	                        dataType: 'json',
	                        data: {
	                            //默认传递的就是输入框的值
	                        },
	                        delay: 500,//延迟效果
	                    } 
					}
					
				},
				password1:{
					validators:{
						notEmpty:{
							message:'密码不能为空'
						},
						stringLength: {
						    min: 8,
							max: 18,
							message: '密码必须在8-18个字符之间'
						},
					}
				},
				password2:{
					validators:{
						notEmpty:{
							message:'请再次输入密码'
						} ,
						identical: {
	                            field: 'password1',
	                            message: '两次密码输入不一致！'
	                    }
					}
				},
				phone :{
					validators:{
						notEmpty:{
							message:'请输入手机号码'
						} ,regexp: {
	                        regexp: /^1\d{10}$/ ,
	                        message: '请输入正确的11位手机号'
	                    },
						remote: {
	                        url: '/user/check/phone',
	                        message:"手机号已被占用",
	                        type: "post",
	                        dataType: 'json',
	                        data: {
	                            //默认传递的就是输入框的值
	                        },
	                        delay: 500,//延迟效果
	                    } 
					}
				},
				email :{
					validators:{
						notEmpty:{
							message:'请输入邮箱'
						},
				       emailAddress: {
	                        message: '邮箱地址格式有误'
	                    },
	                    remote: {
	                        url: '/user/check/email',
	                        message:"邮箱已被占用",
	                        type: "post",
	                        dataType: 'json',
	                        data: {
	                            //默认传递的就是输入框的值
	                        },
	                        delay: 500,//延迟效果
	                    } 
					}
				}
			}
		});
    });

  
        // 改进后的    添加了头像     注册用户
     function register(){
        	//手动校验表单
        	 var flag = $("#registerForm").data("bootstrapValidator").isValid();//获取当前表单验证状态
   			if(!flag){
   				layer.msg('请填写完整注册信息');
   				return ;
   			}
        	
        	
        var formData = new FormData($( "#registerForm" )[0]);  
        
        /*    formData.append("name",$("#name").val());
        formData.append("password1",$("#password1").val());
        formData.append("password2",$("#password2").val());
        formData.append("phone",$("#phone").val());
        formData.append("email",$("#email").val());  
        formData.append("headIcon",$("#headIcon"));  */
     
        $.ajax({
            url:"${pageContext.request.contextPath}/user/register",
            type:"POST",
            dataType:"json",
            data:formData,
            contentType: false,
            processData: false,
            success:function(result) {
            	 if(result.status==1){
                     layer.msg('注册成功',{
                         time:2000,
                         skin:'successMsg'
                     },function(){
                     	window.location='${pageContext.request.contextPath}/page/login';
                     })
                 }else{
                 	layer.msg(result.message,{
                         time:2000,
                         skin:'errorMsg'
                     },function(){
                        // location.href='${pageContext.request.contextPath}/backend/sysuser/findAll?pageNum='+${pageInfo.pageNum};
                     })
                 }
            }
        });
       }
        
        function sendVerifyCode(btn){
        	//js对象转Jquery 对象
        	var btn=$(btn);
        	
        	btn.attr('disabled',true);
        	//发送验证码请求
        	 $.post(
 	           		'${pageContext.request.contextPath}/sms/sendSms',
 	           		{
 	           			'phoneNumber':$('#phone').val()
 	           		},
 	                   function(result){
 	                       if(result.status==1){
 	                       	//	消息发送成功
 	                    	  $('#verifyCode').attr('placeHolder','请输入验证码...');
	                            btn.html('300s 重发');
	                            resendTimer(btn);
 	                            //倒计时
 	                       }else{
 	                       	//消息获取失败
 	                       		//消息获取失败
 		                       	 layer.msg(result.message,{
 		                            time:2000,
 		                            skin:'successMsg'
 		                        },function(){
 		                           //
 		                        })
 	                       }
 						
 	                   }
 	               );
        }
        
        
        //传过来的是jquery对象
        function resendTimer(jqbtn){
        	//倒计时开始
            var i=0; 
            var timerId;
            //setInterval的每次触发间隔
            var INTERVAL=1000;
            //interval执行次数
            var INTERVAL_FULL=300;
        	timerId=setInterval(function(){
        		i++;
            	jqbtn.html(INTERVAL_FULL-i+'s 重发');
            	if(i>=INTERVAL_FULL){
            		//取消倒计时
            		clearInterval(timerId);
            		//重置
            		i=0;
            		//
            		 jqbtn.html('重发');
            		 jqbtn.attr('disabled',false);
            	}
        	},INTERVAL);
        }
       
        
        
        function login(){
        	window.location='${pageContext.request.contextPath}/page/login';
        }
		
     
 </script>
</head>

<body>
<!-- 系统用户管理 -->
<div class="panel panel-default" id="adminSet">
    
<!-- 添加系统用户 start -->
<div class="modal fade" tabindex="-1" id="registerModal" >
    <!-- 窗口声明 -->
    <div class="modal-dialog">
        <!-- 内容声明 -->
        <form id="registerForm"  >
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
         
            <div class="modal-header">
            <!-- 去除关闭按钮 -->
               <!--  <button class="close" data-dismiss="modal">&times;</button> -->
                <h4 class="modal-title">用户注册</h4>
            </div>
            <div class="modal-body text-center">
            
                <div class="row text-right">
                    <label for="name" class="col-sm-4 control-label">用户昵称(必填)：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="name" name="name">
                    </div>
                </div>
                <br>
                
                <div class="row text-right">
                    <label for="password1" class="col-sm-4 control-label">登录密码(必填)：</label>
                    <div class="col-sm-4">
                        <input type="password" class="form-control" id="password1" name="password1">
                    </div>
                </div>
                <br>
                
                 <div class="row text-right">
                    <label for="password2" class="col-sm-4 control-label">请再次输入密码：</label>
                    <div class="col-sm-4">
                        <input type="password" class="form-control" id="password2" name="password2">
                    </div>
                </div>
                <br>
                
                <div class="row text-right">
                    <label for="phone" class="col-sm-4 control-label">联系电话(必填)：</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="phone" name="phone">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="email" class="col-sm-4 control-label">联系邮箱(必填)：</label>
                    <div class="col-sm-4">
                        <input type="email" class="form-control" id="email" name="email">
                    </div>
                </div>
                <br>
                 <div class="row text-right">
                     <label for="email" class="col-sm-2 control-label"></label> 
                    <div class="col-sm-6">
                        <input type="file" name="headIcon"  id="headIcon" class="myfile" value="${deal.image}"/>
                    </div>
                </div>
                <br>
              <!-- 上传头像  待写  -->
               <div class="row text-right">
                     <label for="verifyCode" class="col-sm-4 control-label">短信验证码(必填)：</label> 
                    <div class="col-sm-4">
                        <input type="text" name="verifyCode"  id="verifyCode" class="form-control" placeholder="点击发送按钮..."/>
                    </div>
                    <div class="col-sm-2">
                        <button class="btn btn-primary  " onclick="sendVerifyCode(this)" >发送短信</button>
                    </div>
                </div>
                <br>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary add-Manger" onclick="register()" type="button">注册</button>
                <button class="btn btn-primary  " onclick="login()" type="button">去登录</button>
            </div>
        </div>
        </form>
    </div>
</div>
</div>


</body>

</html>