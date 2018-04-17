//学生角色 
$.ajax({
	url: "/user/isStudent",
	async: false,
	dataType: "json",
	data: {},
	type: 'get',
	success: function(data) {
		if (data.success==true || data.success=="true") {
			$(".apply_student").attr('disabled',true);
			$("#stu_status").val(data.msg.status);
			$("#stu_name").val(data.msg.stuName);
			$("#stu_academy").val(data.msg.stuAcademy);
			$("#stu_id").text(data.msg.stuId);
			if(data.msg.stuSex == "男") {
				$("#stu_sex").val("男");
			}else{
				$("#stu_sex").val("女");
			}
			$("#stu_idcard").val(data.msg.idCard);
			$("#stu_class").val(data.msg.stuClass);
			$("#register_year").val(data.msg.stuEntranceTime);
		}else{
			$(".update_student").attr('disabled',true);
		}
	},
	error: function() {
		alert(error);
	}
});

//教师角色
$.ajax({
	url: "/user/isTeacher",
	async: false,
	dataType: "json",
	data: {},
	type: 'get',
	success: function(data) {
		//alert(JSON.stringify(data));
		if (data.success == true || data.success=="true") {
			$(".apply_teacher").attr('disabled',true);
			$("#teacher_status").val(data.msg.status);
			$("#teacher_phone").val(data.msg.phone);
			$("#teacher_idCard").val(data.msg.idCard);
			$("#teacher_subject").val(data.msg.subject);
			if(data.msg.sex == "男") {
				$("#teacher_sex").val("男");
			}else{
				$("#teacher_sex").val("女");
			}
			$("#teacher_id").text(data.msg.tid);
			$("#teacher_name").val(data.msg.name);
		}else{
			$(".update_teacher").attr('disabled',true);
		}
	},
	error: function() {
		alert(error);
	}
});

//显示用户，角色信息
$.ajax({
	url: "/user/checkLogin",
	dataType: "json",
	async: false,
	type: "get",
	data: {},
	success: function(data) {
		//alert(JSON.stringify(data));
		
		if (data.success == true || data.success=="true") {
			//alert(data.msg.email);
			$("#user_id").val(data.msg.uid);
			$("#user_email").val(data.msg.email);
			$("#user_name").val(data.msg.name);
			$("#register_time").val(data.msg.registerTime);
			if(data.msg.validation == 1) {
				$("#user_status").val("已验证");
			}else {
				$("#user_status").val("未验证");
			}
		}
	},
	error: function() {
		alet("error");
	}
	
});

//更新User信息
function update_user() {
	if (!confirm("确定修改用户基本信息?")) {
		return null;
	}
	var new_username = $("#user_name").val();
	if(new_username.length > 10) {
		alert("昵称长度不能大于10");
		return ;
	}
	$.ajax({
		url: "/user/updateUserName",
		dataType: 'json',
		async: false,
		type: 'post',
		data: {
			"userName": new_username
		},
		success: function(result) {
			alert(result.msg);
		},
		error: function() {
			alert("error");
		}
	});
}

//修改密码
function changePwd() {
	var old_pwd = $.trim($("#old_pwd").val());
	var new_pwd = $.trim($("#new_pwd").val());
	var new_pwd_repeat = $.trim($("#new_pwd_repeat").val());
	if(new_pwd == "" || new_pwd_repeat == "" || old_pwd == "") {
		alert("密码输入框不能为空");
		return;
	}
	if(new_pwd != new_pwd_repeat) {
		alert("输入的新密码不相同");
		return;
	}
	$.ajax({
		url: "/user/updatePwd",
		dataType: 'json',
		async: false,
		type: 'post',
		data: {
			"old_pwd": old_pwd,
			"new_pwd1": new_pwd,
			"new_pwd2": new_pwd_repeat
		},
		success: function(result) {
			alert(result.msg);
		},
		error: function() {
			alert("error");
		}
	});
}

//显示模态框
function showModal(param) {
	if(param == "student") {
		$('#studentModal').modal({
			"keyboard": false
		});
	}
	if(param == "teacher") {
		$('#teacherModal').modal({
			"keyboard": false
		});
	}
}

//添加学生角色
function addStudent() {
	var stuName = $.trim($("#_stuName").val());
	var stuSex = $("#_teacherSex").find('option:selected').val();
	var idCard = $.trim($("#_idCard").val());
	var stuAcademy = $.trim($("#_stuAcademy").val());
	var stuClass = $.trim($("#_stuClass").val());
	var stuEntranceTime = $.trim($("#_stuEntranceTime").val());
	if((stuName.length > 12) || stuName == "") {
		$("#addStudent_result").hide();
		$("#addStudent_result").html("学生姓名格式错误");
		$("#addStudent_result").fadeIn("fast");
		return;
	}
	if(idCard == "" || idCard.length > 12) {
		$("#addStudent_result").hide();
		$("#addStudent_result").html("学号不能为空且长度须小于12");
		$("#addStudent_result").fadeIn("fast");
		return;
	}
	if(stuAcademy == "" || stuAcademy.length > 20) {
		$("#addStudent_result").hide();
		$("#addStudent_result").html("学院不能为空且长度须小于12");
		$("#addStudent_result").fadeIn("fast");
		return;
	}
	if(stuClass == ""){
		$("#addStudent_result").hide();
		$("#addStudent_result").html("班级不能为空");
		$("#addStudent_result").fadeIn("fast");
		return;
	}
	if(stuEntranceTime == "") {
		$("#addStudent_result").hide();
		$("#addStudent_result").html("入学年份不能为空");
		$("#addStudent_result").fadeIn("fast");
		return;
	}
	var z = /^\d{1,}$/;
	if(!z.test(stuClass)) {
		$("#addStudent_result").hide();
		$("#addStudent_result").html("班级必须填整数");
		$("#addStudent_result").fadeIn("fast");
		return;
	}
	if(!z.test(stuEntranceTime)) {
		$("#addStudent_result").hide();
		$("#addStudent_result").html("入学年份必须填整数");
		$("#addStudent_result").fadeIn("fast");
		return;
	}
	$.ajax({
		url: "/student/save",
		dataType: 'json',
		async: false,
		type: 'post',
		data: {
			"stuName": stuName,
			"stuSex": stuSex,
			"idCard": idCard,
			"stuAcademy": stuAcademy,
			"stuClass": stuClass,
			"stuEntranceTime": stuEntranceTime,
		},
		success: function(result) {
			$("#addStudent_result").hide();
			$("#addStudent_result").html(result.msg);
			$("#addStudent_result").fadeIn("fast");
		},
		error: function() {
			alert("error");
		}
	});
}
//添加教师角色
function addTeacher() {
	var name = $.trim($("#_teacherName").val());
	var sex = $("#_stuSex").find('option:selected').val();
	var idCard = $.trim($("#_teacherIdCard").val());
	var subject = $.trim($("#_teacherSubject").val());
	var phone = $.trim($("#_teacherPhone").val());
	if(name == "" || name.length > 12) {
		$("#addTeacher_result").hide();
		$("#addTeacher_result").html("姓名不能为空且长度须小于12");
		$("#addTeacher_result").fadeIn("fast");
		return;
	}
	var z = /^\d{1,}$/;
	if(!z.test(idCard)) {
		$("#addStudent_result").hide();
		$("#addStudent_result").html("教师职工号必须填整数");
		$("#addStudent_result").fadeIn("fast");
		return;
	}
	if(subject == "" || subject.length > 20) {
		$("#addTeacher_result").hide();
		$("#addTeacher_result").html("学科不能为空且长度须小于20");
		$("#addTeacher_result").fadeIn("fast");
		return;
	}
	var a = /^1[3|4|5|7|8][0-9]{9}$/;
	if(!a.test(phone)) {
		$("#addTeacher_result").hide();
		$("#addTeacher_result").html("请输入正确的手机号");
		$("#addTeacher_result").fadeIn("fast");
		return;
	}
	$.ajax({
		url: "/teacher/save",
		dataType: 'json',
		async: false,
		type: 'post',
		data: {
			"name": name,
			"sex": sex,
			"idCard": idCard,
			"subject": subject,
			"phone": phone
		},
		success: function(result) {
			$("#addTeacher_result").hide();
			$("#addTeacher_result").html(result.msg);
			$("#addTeacher_result").fadeIn("fast");
		},
		error: function() {
			alert("error");
		}
	});
}

//更新学生信息
function updateStudent() {
	var r = confirm("确定更新学生信息");
	if(r == true) {
		var stuId = $.trim($("#stu_id").text());
		var stuName = $.trim($("#stu_name").val());
		var idCard = $.trim($("#stu_idcard").val());
		var stuSex = $.trim($("#stu_sex").val());
		var stuAcademy = $.trim($("#stu_academy").val());
		var stuClass = $.trim($("#stu_class").val());
		var stuEntranceTime = $.trim($("#register_year").val());
//		alert(stuId);
//		alert(stuName);
		
		if(stuName == "" || stuName.length > 12) {
			alert("姓名不能为空且长度须小于12");
			return ;
		}
		
		if(stuAcademy == ""|| stuAcademy.length > 20) {
			alert("学院不能为空且长度须小于20");
			return ;
		}
		
		var z = /^\d{1,}$/;
		if(!z.test(idCard)) {
			alert("学号须填整数");
			return ;
		}
		if(!z.test(stuClass)) {
			alert("班级须填整数");
			return ;
		}
		if(!z.test(stuEntranceTime)) {
			alert("入学年份须填整数");
			return ;
		}
		$.ajax({
			url: "/student/updateStudent",
			dataType: 'json',
			async: false,
			type: 'post',
			data: {
				"stuId": stuId,
				"stuName": stuName,
				"stuSex": stuSex,
				"idCard": idCard,
				"stuAcademy": stuAcademy,
				"stuClass": stuClass,
				"stuEntranceTime": stuEntranceTime
			},
			success: function(result) {
				alert(result.msg);
			},
			error: function() {
				alert("error");
			}
		});
	}
}

//更新教师信息
function updateTeacher() {
	var r = confirm("确定更新教师信息");
	if(r == true) {
		var tid = $.trim($("#teacher_id").text());
		var name = $.trim($("#teacher_name").val());
		var sex = $.trim($("teacher_sex").find('option:selected').val());
		var idCard = $.trim($("#teacher_idCard").val());
		var subject = $.trim($("#teacher_subject").val());
		var phone = $.trim($("#teacher_phone").val());
		
		if(name == "" || name.length > 12) {
			alert("姓名不能为空且长度须小于12");
			return ;
		}
		var z = /^\d{1,}$/;
		if(!z.test(idCard)) {
			alert("学号须填整数");
			return ;
		}
		if(subject == "" || subject.length > 20) {
			alert("学科不能为空且长度须小于20");
			return ;
		}
		var checkPhone = /^1[3|4|5|7|8][0-9]{9}$/;
		if(!checkPhone.test(phone)) {
			alert("手机格式不正确");
			return ;
		}
		$.ajax({
			url: "/teacher/update",
			dataType: 'json',
			async: false,
			type: 'post',
			data: {
				"tid": tid,
				"name": name,
				"sex": sex,
				"idCard": idCard,
				"subject": subject,
				"phone": phone
			},
			success: function(result) {
				alert(result.msg);
			},
			error: function() {
				alert("error");
			}
		});
	}
}