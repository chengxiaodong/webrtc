<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
 var messageCount=0;
        $(function(){
        	
        	//消息提示栏初始化：提示在离线时收到的消息
        	messageCount+=${informsize};
        	
            // 点击切换页面
             $("#user-info").click(function() {
            	 //加载到基本的用户页面
                $("#frame-id").attr("src", "${pageContext.request.contextPath}/page/userInfo?uid=${user.id}");
            });
             
            $("#friends-online").click(function() {
            	//加载到在线的好友页面
                $("#frame-id").attr("src", "${pageContext.request.contextPath}/page/friendsOnline?uid=${user.id}");
            });
             $("#friends-search").click(function() {
            	 //加载到添加好友页面
                $("#frame-id").attr("src", "${pageContext.request.contextPath}/page/friendSearch?uid=${user.id}");
            });
            $("#friends-manager").click(function() {
            	//加载到所有好友页面
                $("#frame-id").attr("src", "${pageContext.request.contextPath}/page/friendsManage?uid=${user.id}");
            });
           
            $("#message").click(function() {
            	//加载到用户信息修改页面,type=2====>获取信息后会删除服务器上的message
                $("#frame-id").attr("src", "${pageContext.request.contextPath}/page/message?type=2&uid=${user.id}");
            	//清空消息提示栏
            	//重置消息
            	messageCount=0;
                $('#messageNums').text("");
            }); 
            $("#stopPhoneIcon").click(function() {
            	//js window 回退1;退出视频页面
            	//history.go(-1);
            	//加载到在线的好友页面
                $("#frame-id").attr("src", "${pageContext.request.contextPath}/page/friendsOnline?uid=${user.id}");
            	$('#stopPhoneIcon').css('display','none');
            	//取消呼出音乐
            	cancelCallOutSound();
            }); 
            //测试modal入口
           // $('#cofirmVedioRequestModal').modal("show");
            //启动Message刷新
            getMessage();
        });
        
     
        
        //刷新消息
        function getMessage(){
        	
            $.post(
            		'${pageContext.request.contextPath}/message/getMessage?uid=${user.id}',
                    function(result){
            			
            			if(result==null)
            				//过滤掉空结果，超时的视频请求会返回空
            				return;
                        if(result.status==1){
                        	//	消息获取成功
                           	//获取message 大小
                           	var data=result.data;
                           	var length=data.length;
                        	if(length>0||messageCount>0){
                        		
                        		for(var i in data){
                        			var message=data[i];
                        			if(message.type==6){
                        				//视频请求信息
                        					confirmVedioRequest(message);
                        			}else if(message.type==7){
                        				//视频请求被拒绝
                        				//模拟挂断通话点击按钮
                        				$("#stopPhoneIcon").click();
                        				
                        				   layer.msg(message.message,{
	                                            time:2000,
	                                            skin:'successMsg'
	                                        },function(){
	                                            //重新加载数据
	                                        })
                        			}else{
                        				//普通消息
                        				messageCount++;
                        				//播放消息提示音
                        				messageArriveSound();
                        			}
                        		}
                        		if(messageCount>0){
                           			$('#messageNums').text(messageCount+"        >>");
                        		}
                        	}
                        	else
                        		$('#messageNums').text("");
                        }else{
                        	//消息获取失败
                        }
					//设置没3秒刷新一次
					setTimeout('getMessage()', 3000); //延迟3秒
                    }
                );
        };
        
   
        //接受对方的视频请求
        function confirmVedioRequest(message){
        	//将message缓存到modal中
        	messageTemp=message;
        	//显示视频来源
        	$('#requestUserIcon').attr('src',message.protect.profilephoto);
        	$('#requestName').html(message.protect.username);
        	//播放音乐
        	 intervalCallinid=setInterval(callinSound(),6000);
        	setTimeout(function(){
        		//查看了boostrap。css源码，.hide {
					/*   display: none !important;
					}
					.show {
					  display: block !important;
					} */
        		if($('#cofirmVedioRequestModal').css('display')=="block"){
        			//modal框处于打开状态
        			cancelCallInSound();
        			//返回超时结果
        			reject('对方未接听。。。');
        			$('#cofirmVedioRequestModal').modal("hidden");
        		}else{
        			//alert('modal未打开');
        		}
        	},10000);
        	$('#cofirmVedioRequestModal').modal("show");
        }
        //message缓存区
        var messageTemp;
        function accept(){
        	//用户接受视频
        	//关闭背景音乐
			cancelCallInSound();
        	//ajax发送消息请求被接受
        	var message=messageTemp;
    		$('#frame-id').attr("src",message.message);
        	$('#stopPhoneIcon').css('display','block');
        	$('#cofirmVedioRequestModal').modal("hide");
        	$('#stopPhoneIcon').attr('visiable',true);
        }
        function reject(tip){
        	//关闭背景音乐
        	cancelCallInSound();
        	//用户拒绝请求
        	//ajax发送消息请求被拒绝
        	var message=messageTemp;
        	 $.post(
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
                );
        	$('#cofirmVedioRequestModal').modal("hide");
        }
        
        var intervalCallinid;
        var intervalCallOutid;
        function calloutSound(){
        	//播放音乐
       	 intervalCallOutid=setInterval(calloutSoundPlay(),2000);
        }
        function calloutSoundPlay(){
        	var callout=$('#callout')[0];
        	callout.load();
        	callout.play();
        	return calloutSoundPlay;
        }
        
        function callinSound(){
        	var callin=$('#callin')[0];
        	callin.load();
        	callin.play();
        	//第一次执行完成后返回这个函数
        	return callinSound;
        }
        function messageArriveSound() {
        	var messagein=$('#messageArrive')[0];
        	messagein.load();
        	messagein.play();
		}
        function cancelCallOutSound(){
        	clearInterval(intervalCallOutid);
        }
        function cancelCallInSound(){
        	clearInterval(intervalCallinid);
        }
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
                
               		 <p><span>天天视频通话</span></p>
                <div class="welcome">
                    <div class="left" style="margin-right: 12px;">欢迎您：</div>
                    <div class="right" id="username" style="margin-right: 12px;">${user.username}</div>
                    <div ><a href="${pageContext.request.contextPath}/user/logout"  id="exit"> 退出</a></div>
                </div>
            </div>
        </div>
        <!-- header end -->
        <!-- content start -->
        <div class="container-flude flude-cc" id="a">
            <div class="row friends-managerting flude-cc">
                <div class="col-xs-2 user-wrap">
                    <ul class="list-group" id="menu">
                        <li class="list-group-item active" name="user-info" id="user-info">
                            <i class="glyphicon glyphicon-home"></i> &nbsp;基本信息
                        </li>
                        <li class="list-group-item" name="friends-online" id="friends-online">
                            <i class="glyphicon glyphicon-user"></i> &nbsp;在线好友
                        </li>
                        <li class="list-group-item" name="friends-search" id="friends-search">
                            <i class="glyphicon glyphicon-plus"></i> &nbsp;添加好友
                        </li>
                        <li class="list-group-item" name="friends-manager" id="friends-manager">
                            <i class="glyphicon glyphicon-asterisk"></i> &nbsp;好友管理
                        </li>
                       
                        <li class="list-group-item" name="user-info-manager" id="message">
                            <i class="glyphicon glyphicon-envelope"></i> &nbsp;<span>消息&nbsp;&nbsp;<span id="messageNums" >1&nbsp;>></span></span>
                        </li>
                         <li class="list-group-item  "   style="color: red;display: none;" id="stopPhoneIcon">
                            <i class="glyphicon glyphicon-phone-alt"></i> &nbsp;<span  >挂断视频</span></span>
                        </li>
                    </ul>
                </div>
                <div class="col-xs-10" id="userPanel" >
                      <!--  <iframe id="frame-id" src="https://192.168.43.30:8443/msg?oid=21" width="100%" height="100%" frameborder="0" scrolling="yes">  -->
                      <iframe id="frame-id" src="${pageContext.request.contextPath}/page/userInfo?uid=${user.id}"
                      
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
<div class="footer">
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
