<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="zh">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <!--  -->
   <!--  <meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0"> -->
    
    <title>天天视频通话</title>
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/bootstrap.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layer.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/fileinput/fileinput.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
      <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
	<!-- 必须将 fileinput.css 放在 其js前面  -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layer.css" />
	<script src="${pageContext.request.contextPath}/js/fileinput/fileinput.js"></script>
<%-- 	<script src="${pageContext.request.contextPath}/js/fileinput/exif.js"></script> --%>
    <script src="${pageContext.request.contextPath}/js/fileinput/zh.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/userInfo.css" />
    <script >
    $(function(){
    	//初始化fileupload
    	   $(".myfile").fileinput({
    	            //上传的地址
    	            uploadUrl:"${pageContext.request.contextPath}/user/update/profilePhoto?uid=${user.id}",
    	            uploadAsync : true, //默认异步上传
    	            showUpload : false, //是否显示上传按钮,跟随文本框的那个
    	            showRemove : false, //显示移除按钮,跟随文本框的那个
    	            showCaption : false,//是否显示标题,就是那个文本框
    	            showPreview : true, //是否显示预览,不写默认为true
    	            dropZoneEnabled : false,//是否显示拖拽区域，默认不写为true，但是会占用很大区域
    	            //minImageWidth: 50, //图片的最小宽度
    	            //minImageHeight: 50,//图片的最小高度
    	            //maxImageWidth: 1000,//图片的最大宽度
    	            //maxImageHeight: 1000,//图片的最大高度
    	            //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
    	            //minFileCount: 0,
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
    	            console.log(data);
    	          
    	            //刷新头像图片,不能使用原生的fileinput的data，否则会报错
    	     		//$('#headIconImg').attr('src',data.response.data);
    	            //添加t=Math.random（）解决浏览器对图片缓存不更新图片
    	           $('#headIconImg').attr('src','${pageContext.request.contextPath}/headIcons/${user.id}.jpg?t='+Math.random());
    	           layer.msg(data.response.message,{
                       time:2000,
                       skin:'successMsg'
                   },function(){
                      
                   
                   })
    	           
    	           /*  console.log("fileuploaded ");
    	            var ref = $(this).attr("data-ref");
    	            $("input[name='" + ref + "']").val(data.response.url); */
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
    	        
    	        
    });
  

   function updateHeadIcon() {
		$('.chooseHeadIcon').css('display','block');
		
	}
	function updateName(){
		 $.post(
	           		'${pageContext.request.contextPath}/user/update/username',
	           		{'uid':${param.uid},
	           		 'username':$('#inputusername ').val()
	           		},
	                   function(result){
	                       if(result.status==1){
	                       	//	消息发送成功
	                         layer.msg('用户名修改成功',{
	                            time:2000,
	                            skin:'successMsg'
	                        },function(){
	                            //重新加载数据
	                           // $('#inputusername ').val()
	                           //隐藏输入栏行
	                            //将新改的username跟新到main.js
	                            //跟新到本页的username
	                            $('#username').html($('#inputusername ').val());
	                            parent.$('#username').html($('#inputusername ').val());
	                        	$('#input1').css('display','none');
	                        })
	                        
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
	function updatePhone(){
		 $.post(
	           		'${pageContext.request.contextPath}/user/update/phone',
	           		{'uid':${param.uid},
	           		 'phone':$('#inputphone').val()
	           		},
	                   function(result){
	           			if(result.status==1){
	                       	//	消息发送成功
	                         layer.msg('手机号码修改成功',{
	                            time:2000,
	                            skin:'successMsg'
	                        },function(){
	                            //重新加载数据
	                           // $('#inputusername ').val()
	                           
	                            //跟新到本页的username
	                            $('#phone').html($('#inputphone').val());
	                           //隐藏输入栏行
	                          
	                        	$('#input2').css('display','none');
	                        })
	                        
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
	function updateEmail(){
		 $.post(
	           		'${pageContext.request.contextPath}/user/update/email',
	           		{'uid':${param.uid},
		           		 'email':$('#inputemail ').val()
		           		},
		                   function(result){
		           			if(result.status==1){
		                       	//	消息发送成功
		                         layer.msg('邮箱修改成功',{
		                            time:2000,
		                            skin:'successMsg'
		                        },function(){
		                            //重新加载数据
		                           // $('#inputusername ').val()
		                           
		                            //跟新到本页的username
		                            $('#email').html($('#inputemail').val());
		                           //隐藏输入栏行
		                          
		                        	$('#input3').css('display','none');
		                        })
		                        
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
	function updatePassword(){
		 $.post(
	           		'${pageContext.request.contextPath}/user/update/password',
	           		{'uid':${param.uid},
		           		 'password1':$('#inputpassword1').val(),
		           		'password2':$('#inputpassword2').val(),
		           		},
		                   function(result){
		           			if(result.status==1){
		                       	//	消息发送成功
		                         layer.msg('密码修改成功',{
		                            time:2000,
		                            skin:'successMsg'
		                        },function(){
		                            //重新加载数据
		                           // $('#inputusername ').val()
		                           
		                            //跟新到本页的username
		                            //$('#inputpassword1').html($('#inputpassword1').val());
		                           //隐藏输入栏行
		                          
		                        	$('#input4').css('display','none');
		                        	$('#input5').css('display','none');
		                        })
		                        
		                       }else{
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
    </script>
</head>

<body >
  <div id="wrapper">
<!-- 展示搜索结果 -->
<div class="panel panel-default" id="userSet">
    <div class="panel-heading"  align="center">
       		 <img class="img-rounded image-responsive roundImage" id="headIconImg"  src="${user.profilephoto}" alt="头像"/>
    	<div class="aWrapper"><a id="updateHeadIcon"   onclick="updateHeadIcon()">[修改头像]</a></div>
    	<div class="row text-right chooseHeadIcon" >
                     <label for="headIcon" class="col-sm-2 control-label"></label> 
                    <div class="col-sm-5">
                        <input type="file" name="headIcon"  id="headIcon" class="myfile" />
                    </div>
       </div>
    </div>
    <div class="panel-body">
        <br>
        <div class="show-list text-center">
            <table class="table table-bordered table-hover" style='text-align: center;'>
               
                <tbody id="tb">         
               <tr>
               		<td>
               			<label class="col-sm-4 control-label">用户名：</label>
						
						<label class="col-sm-3 control-label" id="username">${user.username}</label>
						
						<a class="col-sm-3 a_text"  onclick="javascript:$('#input1').css('display','table-row');">修改</a>
               		</td>
               </tr>
               <!-- 修改输入行 -->
               <tr class="updateRow"  id="input1">
               		<td>
               			<label class="col-sm-4 control-label"></label>
						
						<input class="col-sm-3 control-form" id="inputusername" placeholder="请输入新的用户名..."></input>
						
						
						<button type="button" class="btn btn-sm btn-primary btn-success  col-sm-3" style="margin-left: 10px;" onclick="updateName()">提交</button>
						
               		</td>
               </tr>
               </tbody>
               <tbody>
 			<tr>
               		<td>
               			<label class="col-sm-4 control-label">手机号：</label>
						
						<label class="col-sm-3 control-label" id="phone">${user.phone}</label>
						
						<a class="col-sm-3 a_text"  onclick="javascript:$('#input2').css('display','table-row');">修改</a>
               		</td>
               </tr>
               <!-- 修改输入行 -->
               <tr class="updateRow" id="input2">
               		<td>
               			<label class="col-sm-4 control-label"></label>
						
						<input class="col-sm-3 control-form" id="inputphone" placeholder="请输入新的手机号..."></label>
						
						
						<button type="button" class="btn btn-sm btn-primary btn-success  col-sm-3" style="margin-left: 10px;" onclick="updatePhone()">提交</button>
						
               		</td>
               </tr>
               </tbody>
              <tbody>
                <tr>
               		<td>
               			<label class="col-sm-4 control-label">邮箱：</label>
						
						<label class="col-sm-3 control-label" id="email">${user.email}</label>
						
						
						<a class="col-sm-3 a_text" onclick="javascript:$('#input3').css('display','table-row');">修改</a>
               		</td>
               </tr>
               <!-- 修改输入行 -->
               <tr class="updateRow" id="input3">
               		<td>
               			<label class="col-sm-4 control-label"></label>
						
						<input class="col-sm-3 control-form" id="inputemail" placeholder="请输入新的邮箱..."></label>
						
						
						<button type="button" class="btn btn-sm btn-primary btn-success  col-sm-3" style="margin-left: 10px;" onclick="updateEmail()">提交</button>
						
               		</td>
               </tr>
                </tbody>
                 <tbody>
                <tr>
               		<td>
               			<label class="col-sm-4 control-label">注册时间：</label>
						
						<label class="col-sm-3 control-label">${user.createdate}</label>
						
						<a class="col-sm-3 a_text" onclick="javascript:$('#input4').css('display','table-row');$('#input5').css('display','table-row');">修改密码</a>
               		</td>
               </tr>
               <!-- 修改输入行 -->
               <tr class="updateRow" id="input4">
               		<td>
               		<label class="col-sm-4 control-label"></label>
						<input class="col-sm-3 control-form" id="inputpassword1" type="password"  placeholder="请输入新的密码..."></label>
               		</td>
               </tr>
               <tr class="updateRow" id="input5">
               		<td>
               		<label class="col-sm-4 control-label"></label>
               			<input class="col-sm-3 control-form" id="inputpassword2" type="password"   placeholder="请再次输入新的密码..."></label>
						
						
						<button type="button"  class="btn btn-sm btn-primary btn-success  col-sm-3" style="margin-left: 10px;" onclick="updatePassword()">提交</button>
               		</td>
               </tr>
                </tbody>
            </table>
            
        </div>
    </div>
</div>
</div>

</body>

</html>

