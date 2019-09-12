layui.config({
	base : "js/"
}).use(['form','layer'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		$ = layui.jquery;
//	//video背景
//	$(window).resize(function(){
//		if($(".video-player").width() > $(window).width()){
//			$(".video-player").css({"height":$(window).height(),"width":"auto","left":-($(".video-player").width()-$(window).width())/2});
//		}else{
//			$(".video-player").css({"width":$(window).width(),"height":"auto","left":-($(".video-player").width()-$(window).width())/2});
//		}
//	}).resize();
//	
	//登录按钮事件
	form.on("submit(login)",function(data){
		$.post("/v1/userextend/login",data.field,function(response){
			if(response.code == 200){
				window.location.href = "/index";
			}else{
				layer.msg(response.message, {
		            icon: 5,
		            time: 2000
		          });
			}
		});
		//
		return false;
	});
	
/*	$("#signup").on("click",function(){
		try{
			location.href="/signup";
		}catch(e){
			console.log(e);
		}
	})*/
})
