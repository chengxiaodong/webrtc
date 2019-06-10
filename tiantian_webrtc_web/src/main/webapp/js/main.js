$(function(){
	$("#menu li").click(function(){
		$("#menu li").removeClass("active");
		$(this).addClass("active");
		var panelId = "#"+$(this).attr("name");
		$("#userPanel > div").css({'display':'none'});
		$(panelId).css({'display':'block'});
	
	});
});
	

        /*
        $(".updateOne").on("click", function() {
            $("#myModal").modal("hide");
        });

        // 管理员管理 添加管理员模态框
        $("#doAddManger").on("click", function() {
            _this = this; //this是事件源
            $("#myMangerUser").modal("show");
        });



});


function openFileInput(){
	$('#file').click();
}*/
