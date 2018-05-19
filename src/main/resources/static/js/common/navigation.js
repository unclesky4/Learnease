var isStudent = true;
var isTeacher = true;
$.ajax({
	url: "/user/checkLogin",
	async: true,
	dataType: "json",
	data: {},
	type: 'get',
	success: function(data) {
		if (data.success == true || data.success == "true") {
			$("#show_username").html(data.msg.name);
			var li_content = "<li><a href='/user_center'>用户主页</a></li>";
			$("#navbar-nav").prepend(li_content);
			isStudent = false;
		}
	},
	error: function() {
		alert("error");
	}
});

$.ajax({
	url: "/user/isTeacher",
	async: false,
	dataType: "json",
	data: {},
	type: 'get',
	success: function(data) {
		if (data.msg.statusCode == 1) {
			var content = "<li><a href='/teacher_center'>教师中心</a></li>"
			$("#navbar-nav").append(content);
			isTeacher = false;
		}
	},
	error: function() {
		alert(error);
	}
});

if (isStudent && isTeacher) {
	var content = new Array();
	content.push("<a href='/login' style='font-size: 14px;'><span id='user_login'>登陆</span>");
	content.push("/");
	content.push("<span>注册</span></a>");
	$(".user").html(content.join(""));
}