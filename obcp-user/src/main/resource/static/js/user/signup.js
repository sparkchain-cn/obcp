/**
 * 
 */
layui.config({
	base : "js/"
}).use(['form','layer','jquery'],function(){
	
	var form = layui.form,curCount = 0,
	layer = parent.layer === undefined ? layui.layer : parent.layer,
	$ = layui.jquery;
	
	
//	$("#invitation").focus(function(){
//		layer.tips('11！', '#invitation', {
//	 	  tips: [1, '#000'],
//	 	  time: 4000
//	 	});
//	})
	
	
	//表单验证
	form.verify({
			 confirmPassword:function(value,item){
				 var passwordText = $("[name=password]").val();
				 if(!value || value == ""){
					  return "密码不能为空"
				  }
				 if(value != passwordText){
					 return "两次输入密码不一致";
				 }
			 },
			 require:function(value, item){
				 if(!value || value ==""){
					 return "手机号码不能为空";
				 }
			 },
			 username: function(value, item){ //value：表单的值、item：表单的DOM对象	
				 	if(!value || value == ""){
				 		return "昵称不能为空"
				 	}
			        else if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
					      return '昵称不能有特殊字符';
					    }
				    else if(/(^\_)|(\__)|(\_+$)/.test(value)){
					      return '昵称首尾不能出现下划线\'_\'';
					    }
				    else  if(/^\d+\d+\d$/.test(value)){
					      return '昵称不能全为数字';
					    }
				    else if( value.length > 18){
				    	 return '昵称的长度最大为18位';
				    }
			  }
			  ,passwords: function(value,item){				  
				 // var reg=new RegExp(/^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\d)(?=.*?[0-9])[a-zA-Z\d#@*&.]*$/);
				 var reg = new RegExp(/^(\w){6,18}$/);
				  if(!value || value == ""){
					  return "密码不能为空"
				  }
				  var numPatrn=new RegExp(/^[0-9]{1,20}$/);
				  if(numPatrn.test(value)){
					  return "密码不能为全为数字";
				  }
				  else if(!reg.test(value)){
						  return "请输入6-18位，字母，数字或下划线";
					  }				  				
			  },
			  requireMsgCode:function(value,item){
				  if(!value || value == ""){
					  return "短信验证码不能为空"
				  }
			  }
	});
	
	$("#btnSendCode").on("click",function(){
		curCount = 60;
        //button效果，开始计时
        var obj = {};
        obj.mobileNum = $('[name=mobile]').val();
        if(checkPhone(obj.mobileNum)){
            //向后台发送处理数据
            $(this).attr({"disabled":"disabled"});
            $.ajax({
                type: "POST", //用POST方式传输
                dataType: "json", //数据格式:JSON
                url: '/v1/userextend/register/code', //目标地址
                data:obj,
                error: function(XMLHttpRequest, textStatus, errorThrown) {},
                success: function(data) {
                    if(data.success!=true){		                       
                        layer.msg(data.message, {
		    			            icon: 5,
		    			            time: 2000
		    			          });
                    }else{
                        $("#btnSendCode").attr("class","layui-btn layui-btn-disabled");
                        $("#btnSendCode").html('');
                        $("#btnSendCode").html(curCount + "秒再获取");
                        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
                    }
                }
            });
        }
	});

		
    //timer处理函数
    function SetRemainTime() {
    /* 	*/
	    //当前剩余秒数
        if(curCount == 1) {
            window.clearInterval(InterValObj); //停止计时器
            $("#btnSendCode").attr("class","layui-btn layui-btn-normal");
            $("#btnSendCode").html('');
        	  $("#btnSendCode").html("重新发送验证码");
        		$('#btnSendCode').removeAttr("disabled");
            code = ""; //清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
        } else {
            curCount--;
            $("#btnSendCode").html('');
        	$("#btnSendCode").html(curCount + "秒后重新获取");
        }
    }
	
    //验证手机号码合法性
    function checkPhone(mobileNum) {
        var phoneNumReg = /^1[3|4|5|6|7|8|9]\d{9}$/;
        var phoneNum = mobileNum;
        <!--表示以1开头，第二位可能是3/4/5/7/8等的任意一个，在加上后面的\d表示数字[0-9]的9位，总共加起来11位结束。-->
        if(!phoneNumReg.test(phoneNum)) {
        	layer.msg("手机号码有误，请重填，手机号码11位数字，目前支持前两位13、14、15、16、17、18、19手机号码", {
	            icon: 5,
	            time: 2000
	          });
            return false;
        }
        return true;
    }
	//表单提交事件
	form.on("submit(signup)",function(data){
		if(data.field.confirmPassword != data.field.password){
			layer.msg("两次密码不一致", {
	            icon: 5,
	            time: 2000
	          });
		}else {		
			$.post("/v1/userextend/register",data.field,function(response){
				if(response.code == 200){
					var msg = "注册成功！"
					if(response.message){
						msg = response.message;
					}
					layer.msg(msg, {
			            icon: 6,
			            time: 2000
			          },function(){
			        	  window.location.href = "/login";
			          });
										
				}else{
					layer.msg(response.message, {
			            icon: 5,
			            time: 2000
			          });
				}
			});
		
		}
		return false;
	});
	
	

	
	
})

		