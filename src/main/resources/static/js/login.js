
//登录功能实现
$(function(){
	$("#loginPasswd").keypress(function(event){
		if(event.which == 13) {
			login();
		}
	});
})

function login(){
	var loginEmail = $("#loginEmail").val();
    var loginPasswd = $("#loginPasswd").val();
	var a = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
	if(loginEmail =="" || !a.test(loginEmail)){
		alert("邮箱格式错误！");
		return;
	}
    if(loginPasswd == ""){
    	alert("密码不能为空！");
    	return;
    }
    $.ajax({
        url:'/user/login',
        type : 'post',
        dataType : 'json',
        data: {
            "email" : loginEmail,
            "pwd" : loginPasswd
        },
        success: function(result){
        	if(result.success == true || result.success == "true"){
        		window.location.href = '/';
        	}else{
        		alert(result.msg);
        	}
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            alert("出现错误："+XMLHttpRequest+" "+textStatus+" "+errorThrown);
        }
    });
}

//注册功能
function SignUp(){
	$("#register_btn").attr('disabled',true);
	var userName = $.trim($("#userNameRG").val());
	var userEmail = $.trim($("#emailRG").val());
	var userPwd	= $.trim($("#password").val());
	var userPwd1 = $.trim($("#password_confirm").val());
	var str = /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;
	if(userName == "" || !str.test(userName)){
		alert("用户名格式错误！");
		$("#register_btn").attr('disabled',false);
		return;
	}
	
	var a = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	if(userEmail =="" || !a.test(userEmail)){
		alert("邮箱格式错误！");
		return;
	}
	if(userPwd != userPwd1){
		alert("两次密码不一致！");	
		return;
	}
	
	if(userPwd ==""|| userPwd.length < 3){
		alert("密码长度必须大于3位！");	
		return;
	}
	$.ajax({
		url: "/user/register",
		dataType: "json",
		async: false,
		type: "post",
		data: {
			"name": userName,
			"email": userEmail,
			"pwd": userPwd,
			"pwd_repeat": userPwd1
		},
		success: function(result) {
			alert(result.msg);
			$("#register_btn").attr('disabled',false);
		},
		error: function() {
			alert("error");
			$("#register_btn").attr('disabled',false);
		}
	});
}


//忘记密码
function resetPasswd(){
	$("#resetPwd_btn").attr('disabled',true);
    var email = $.trim($("#emailUp").val());
    var a = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	if(email =="" || !a.test(email)){
		alert("邮箱格式错误！");
		$("#resetPwd_btn").attr('disabled',false);
		return;
	}
    $.ajax({
        url : '/user/resetPwdEmail',
        type : 'post' ,
        dataType : 'json',
        data: {"userEmail" : email} ,
        success: function(result) {
        	alert(result.msg);
        	$("#resetPwd_btn").attr('disabled',false);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert("出现错误："+XMLHttpRequest+" "+textStatus+" "+errorThrown);
            $("#resetPwd_btn").attr('disabled',false);
        }
    })
}
