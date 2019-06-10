<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
    isELIgnored="false"%><!DOCTYPE HTML>
<html>
<head>
<link rel="shortcut icon" href="#" />
<title>WEBRTC视频通话</title>
<meta charset="utf-8" />

<meta name="keywords" content="ACGIST的视频应用,webrtc" />
<meta name="description" content="使用webrtc实现的网页视频通话。" />
    <link rel="stylesheet"  href="/css/bootstrap.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
      <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>
    <script src="${pageContext.request.contextPath}/js/friendsearch.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/friendsearch.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script> 
	 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layer.css" /> 
<style type="text/css">
* {
    margin: 0;
    padding: 0;
    overflow-y: hidden;
}

body {
    background-color: rgb(34, 34, 34);
}

#main {
    display: none;
    -webkit-transition-property: rotation;
    -webkit-transition-duration: 2s;
    text-align: center;
    -webkit-transform-style: preserve-3d;
    width: 1200px;
    margin: 0 auto;
    padding: 60px 0;
}

#localVideo {
    box-shadow: 0 0 20px #000;
    width: 600px;
    display: inline-block;
    
}

#remoteVideo {
    box-shadow: 0 0 20px #000;
    width: 600px;
    display: none;
   
}

#miniVideo {
    box-shadow: 0 0 20px #000;
    width: 300px;
    display: none;
}

#footer {
    position: absolute;
    bottom: 0;
    width: 100%;
    height: 28px;
    background-color: #404040;
    color: #fff;
    font-size: 13px;
    font-weight: bold;
    line-height: 28px;
    text-align: center;
}

.browser {
    box-shadow: 0 0 20px #000 inset;
    width: 400px;
    margin: 200px auto;
    padding: 20px;
    text-align: center;
    color: #fff;
    font-weight: bold;
}

@media screen and (-webkit-min-device-pixel-ratio:0) {
    #main {
        display: block;
    }
    .browser {
        display: none;
    }
}
#divVocalVideo{
	z-index: 2;
	float:left;
}
#divRemoteVideo{
	z-index: 2;
	float:left;
}

#divMiniVideo{
float:left;
	z-index: 3;
	offsetLeft:30px;
}
</style>
</head>
<body ondblclick="fullScreen()">
    <div class="browser">对不起暂时只支持google chrome浏览器！</div>
    <div id="main">
    	<div id="divVocalVideo">
        <video id="localVideo" autoplay="autoplay"></video>
        </div>
        <div id="divRemoteVideo">
        <video id="remoteVideo" autoplay="autoplay" ></video>
        </div>
        <div id="divMiniVideo">
        <video id="miniVideo"  autoplay="autoplay"></video>
        </div>
    </div>
   
    <div id="footer"></div>
    <script type="text/javascript">
    
        var pc;
        var main; // 视频的DIV
        var errorNotice; // 错误提示DIV
        var socket; // websocket
        var localVideo; // 本地视频
        var miniVideo; // 本地小窗口
        var remoteVideo; // 远程视频
        var localStream; // 本地视频流
        var remoteStream; // 远程视频流
        var localVideoUrl; // 本地视频地址
        var initiator = ${requestScope.initiator}; // 是否已经有人在等待

        var started = false; // 是否开始
        var channelReady = false; // 是否打开websocket通道

        var channelOpenTime;
        var channelCloseTime;
        //分享给好友的https视频链接
        var localWebrtcConnetctHttps;
        //连接类
        var PeerConnection = window.PeerConnection || window.webkitPeerConnection00
                || window.webkitRTCPeerConnection || window.mozRTCPeerConnection;

        //RTC对话类
        var RTCSessionDescription = window.mozRTCSessionDescription
                || window.RTCSessionDescription;

        //RTC候选对象
        var RTCIceCandidate = window.mozRTCIceCandidate || window.RTCIceCandidate;

        //获取本地媒体流对象
        navigator.getMedia = (navigator.getUserMedia
                || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia);

        //获取远程媒体流URL
        var URL = window.webkitURL || window.URL;

        var mediaConstraints = {
            "has_audio" : true,
            "has_video" : true
        }; // 音频和视频

        // 初始化
        function initialize() {
            console.log("初始化");
        main = document.getElementById("main");
            errorNotice = document.getElementById("errorNotice");
            localVideo = document.getElementById("localVideo");
            miniVideo = document.getElementById("miniVideo");
            remoteVideo = document.getElementById("remoteVideo");
           
            
            noticeMsg();
            openChannel();
            getUserMedia();
        }

        // 获取用户的媒体
         function getUserMedia() {
            console.log("获取用户媒体");
          
            navigator.getMedia({
                "audio" : true,
                "video" : true
            }, onUserMediaSuccess, onUserMediaError);
        } 
		var i=1;
        // 获取用户媒体成功
        function onUserMediaSuccess(stream) {
        	 var url;
        	 var isOldVersion=true;
        	 try{
             url = URL.createObjectURL(stream);
        	 }catch(e){
        		 isOldVersion=false;
        	 }
       		console.log("URL生成的url:"+url);
            localVideo.style.display = "inline-block";
            remoteVideo.style.display = "none";
      
			if(isOldVersion){
           		localVideo.src = url;
			}else{
				localVideo.srcObject=stream;
			}
            localVideoUrl = url;
            localStream = stream;
          
            if (initiator)
                maybeStart();
        }

        // 开始连接
        function maybeStart() {
            if (!started && localStream && channelReady) {
                setNotice("连接中...");
                createPeerConnection();
                pc.addStream(localStream);
                started = true;
                if (initiator)
                    doCall();
            }
        }

        //接受显示选择
        var mediaConstraints = {
            optional: [],
            mandatory: {
                OfferToReceiveAudio: true,
                OfferToReceiveVideo: true
            }
        };

        // 开始通话
        function doCall() {
            console.log("开始呼叫");

            pc.createOffer(setLocalAndSendMessage, function() {}, mediaConstraints);
        }

        function setLocalAndSendMessage(sessionDescription) {
            pc.setLocalDescription(sessionDescription);
            sendDspMessage(sessionDescription);
        }
        // 打开websocket
        function openChannel() {
            console.log("打开websocket...");
            var supportWebsocket=true;
				try{
                  socket = new WebSocket(
                    "wss://192.168.43.30:8443/video/${requestScope.uid}"
            		//"ws://192.168.43.30:8080/video/${requestScope.uid}"
                    );
				}catch(ex){
					try{
						//socket = new MozWebSocket("wss://192.168.43.30:8080/video/${requestScope.uid}");
						socket = new MozWebSocket("wss://192.168.43.30:8443/video/${requestScope.uid}");
					}catch(ex){
						alert("您的浏览器不支持Websocket!");
						console.log("您的浏览器不支持Websocket!");
						supportWebsocket=false;
					}
				}
//WSS是https
				if(supportWebsocket){
				            socket.onopen = onChannelOpened;
				            socket.onmessage = onChannelMessage;
				            socket.onclose = onChannelClosed;
				            socket.onerror = function(event){
				            	console.log("socket error:"+event);
				            }
				            
				}
        }
        // 发送信息Dsp
        function sendDspMessage(message) {
        	var jsonObject=[];
        	var row ={};
        	 // 发送信息Dsp
        	row.type=2;
        	row.message='';
        	row.data=message;
        	jsonObject.push(row);
            var msgJson = JSON.stringify(jsonObject);

            socket.send(msgJson);

            console.log("发送dsp信息 : " + msgJson);
        }
        // 发送Signal信息1
        function sendSignalMessage(message) {
        	var jsonObject=[];
        	var row ={};
        	 // 发送信息Signal
        	row.type=1;
        	row.message=message;
        	row.data='';
        	jsonObject.push(row);
            var msgJson = JSON.stringify(jsonObject);

            socket.send(msgJson);

            console.log("发送Signal信息 : " + msgJson);
        }
        // 发送Signal信息2
        function sendSignalMessage3(message) {
        	var jsonObject=[];
        	var row ={};
        	 // 发送信息Signal
        	row.type=3;
        	row.message=message;
        	row.data='';
        	jsonObject.push(row);
            var msgJson = JSON.stringify(jsonObject);

            socket.send(msgJson);

            console.log("发送Signal信息1 : " + msgJson);
        }
     // 发送Signal信息4
        function sendSignalMessage4() {
        	var jsonObject=[];
        	var row ={};
        	 // 发送信息Signal
        	row.type=4;
        	row.message='';
        	
        	row.data={
              	   oid:'${requestScope.targetId}',
     			   message:'https://192.168.43.30:8443/msg?oid=${requestScope.uid}'	,
     			   uid:'${requestScope.requestId}',
     			   type:'6'
        };
        	jsonObject.push(row);
            var msgJson = JSON.stringify(jsonObject);

            socket.send(msgJson);

            console.log("发送Signal信息1 : " + msgJson);
        }

        // 设置状态
        function noticeMsg() {
            if (!initiator) {
               // setNotice("让别人加入（注意事项查看源码）: https://192.168.43.30:8443/msg?oid=${requestScope.uid }");
               setNotice("正在等待响应请稍等。。。");
                //发送给后台
               //localWebrtcConnetctHttps="让别人加入（注意事项查看源码）: https://192.168.43.30:8443/msg?oid=${requestScope.uid }";
               localWebrtcConnetctHttps={
                   type : 1,
                   message:"让别人加入（注意事项查看源码）: https://192.168.43.30:8443/msg?oid=${requestScope.uid }"
               };
               

               //通知对方http://localhost:9003/message/sendMessage
               //由于https不能访问http，所以下面无法实现，只能使用服务端去发消息
               /*  $.post(
           		'https://192.168.43.30:9003/message/sendMessage',
           		{'oid':'https://192.168.43.30:8443/msg?oid=${requestScope.uid}',
           		 'message':'视频通话请求中。。。'	,
           		 'uid':'${requestScope.uid}',
           		 'type':6
           		},
                   function(result){
                       if(result.status==1){
                       	//	消息发送成功
                         layer.msg('视频通话请求中。。。',{
                            time:2000,
                            skin:'successMsg'
                        },function(){
                            //重新加载数据
                        })
                        
                       }else{
                       	//消息获取失败
                       }
                       
                   }
               ); */
               
               
               console.log("让别人加入（注意事项查看源码）: https://192.168.43.30:8443/msg?oid=${requestScope.uid }");
            	//setNotice("让别人加入（注意事项查看源码）: http://192.168.43.30:80/msg?oid=${requestScope.uid }");
            } else {
                setNotice("初始化...");
            }
        }

        // 打开连接
        function createPeerConnection() {
            var server = {
                "iceServers" : [ {
                    "url" : "stun: 192.168.43.30"
                } ]
            };
            pc = new PeerConnection(server);
            pc.onicecandidate = onIceCandidate;
            pc.onconnecting = onSessionConnecting;
            pc.onopen = onSessionOpened;
            pc.onaddstream = onRemoteStreamAdded;
            pc.onremovestream = onRemoteStreamRemoved;
           
        }

        // 谁知状态
        function setNotice(msg) {
            document.getElementById("footer").innerHTML = msg;
        }

        // 响应
        function doAnswer() {
            pc.createAnswer(setLocalAndSendMessage, function(error) {
                console.log('Failure callback: ' + error);
            });
        }

        // websocket打开
        function onChannelOpened() {
            console.log("websocket打开");
            sendSignalMessage("signalMessage.......websocket 已打开！");
            sendSignalMessage4();
            channelOpenTime = new Date();
            channelReady = true;
            if (initiator)
                maybeStart();
        }

        // 接受websocket发送来的dsp消息
        function onChannelMessage(message) {
            console.log("收到信息 : " + message.data);

            processSignalingMessage(message.data);//建立视频连接
        }

        // 处理消息
        function processSignalingMessage(message) {
            var msg = JSON.parse(message);

            if (msg.type === "offer") {
                if (!initiator && !started)
                    maybeStart();
                pc.setRemoteDescription(new RTCSessionDescription(msg));
                doAnswer();
            } else if (msg.type === "answer" && started) {
                pc.setRemoteDescription(new RTCSessionDescription(msg));
            } else if (msg.type === "candidate" && started) {
                var candidate = new RTCIceCandidate({
                    sdpMLineIndex : msg.label,
                    candidate : msg.candidate
                });
                pc.addIceCandidate(candidate);
            } else if (msg.type === "bye" && started) {
                onRemoteClose();
            } else if (msg.type === "nowaiting") {
                onRemoteClose();
                setNotice("对方已离开！");
            }
        }

        /* // websocket异常
        function onChannelError(event) {
            console.log("websocket异常 ： " + event);

            //alert("websocket异常");
        }
 */
        // websocket关闭
        function onChannelClosed() {
            console.log("websocket关闭");

            if (!channelOpenTime) {
                channelOpenTime = new Date();
            }
            channelCloseTime = new Date();
            openChannel();
        }

        // 获取用户流失败
        function onUserMediaError(error) {
            console.log("获取用户流失败！"+error);
            alert("获取用户流失败！");
        }

        // 邀请聊天：这个不是很清楚，应该是对方进入聊天室响应函数
        function onIceCandidate(event) {
            if (event.candidate) {
            	
                sendDspMessage(
                		{
                            type : "candidate",
                            label : event.candidate.sdpMLineIndex,
                            id : event.candidate.sdpMid,
                            candidate : event.candidate.candidate
                        }
                );
                
            } else {
                console.log("End of candidates.");
            }
        }

        // 开始连接
        function onSessionConnecting(message) {
            console.log("开始连接");
        }

        // 连接打开
        function onSessionOpened(message) {
            console.log("连接打开");
        }

        // 远程视频添加
        function onRemoteStreamAdded(event) {
             console.log("远程视频添加");
			
            var url;
            var isOldVersion=true;
            try{
            	url= URL.createObjectURL(event.stream);
            }catch(e){
            	isOldVersion=false;
            }
            
            if(isOldVersion){
            	miniVideo.src = localVideo.src;
                remoteVideo.src = url;
                console.log("---isOldVersion---:true");
            }else{
            	miniVideo.srcObject = localStream;
                remoteVideo.srcObject = event.stream;
            }
            remoteStream = event.stream;
            waitForRemoteVideo();  
         /*  console.log("远程视频添加");

            var url = URL.createObjectURL(event.stream);

            miniVideo.src = localVideo.src;
            remoteVideo.src = url;
            remoteStream = event.stream;
            waitForRemoteVideo();   */
        }

        // 远程视频移除
        function onRemoteStreamRemoved(event) {
            console.log("远程视频移除");
        }

        // 远程视频关闭
        function onRemoteClose() {
            started = false;
            initiator = false;

            miniVideo.style.display = "none";
            remoteVideo.style.display = "none";
            localVideo.style.display = "inline-block";

            main.style.webkitTransform = "rotateX(360deg)";

            miniVideo.src = "";
            remoteVideo.src = "";
            localVideo.src = localVideoUrl;

            setNotice("对方已断开！");

            pc.close();
        }

        // 等待远程视频
        function waitForRemoteVideo() {
            if (remoteVideo.currentTime > 0) { // 判断远程视频长度
                transitionToActive();
            } else {
                setTimeout(waitForRemoteVideo, 10000);
            }
        }

        // 接通远程视频
        function transitionToActive() {
            remoteVideo.style.display = "inline-block";
            localVideo.style.display = "none";
            main.style.webkitTransform = "rotateX(360deg)";
            setTimeout(function() {
                localVideo.src = "";
            }, 10000);
            setTimeout(function() {
                miniVideo.style.display = "inline-block";
              //  miniVideo.style.display = "none";
            }, 10000);

            setNotice("连接成功！");
        }

        // 全屏
        function fullScreen() {
            main.webkitRequestFullScreen();
        }

        // 关闭窗口退出
        window.onbeforeunload = function() {
           
            sendSignalMessage3("我已离开，通知对方。。。。");
            if(pc!=null)
            pc.close();
            if(socket!=null)
            socket.close();
        }

        // 设置浏览器支持提示信息
        function errorNotice(msg) {
        	if(main!=null&&main.style!=null){
            main.style.display = "none";

           errorNotice.style.display = "block";
            errorNotice.innerHTML = msg;
        	}
        }

        if (!WebSocket) {
            errorNotice("你的浏览器不支持WebSocket！建议使用<a href=\"https://www.google.com/intl/zh-CN/chrome/browser/\" target=\"_blank\">google chrome浏览器！</a>");
        } else if (!PeerConnection) {
            errorNotice("你的浏览器不支持RTCPeerConnection！建议使用<a href=\"https://www.google.com/intl/zh-CN/chrome/browser/\" target=\"_blank\">google chrome浏览器！</a>");
        } else {
            if (window.navigator.userAgent.indexOf("Chrome") !== -1)
                setTimeout(initialize, 1); // 加载完成调用初始化方法
        }
    </script>
</body>
</html>
