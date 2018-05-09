function login() {
	var name = $.trim($("#username").val());
	var password = $.trim($("#password").val());
	if (name == "" || password == "") {
		$(".login-form .alert-danger").hide();
		$("#show_info").html("请输入用户名和密码");
		$(".login-form .alert-danger").show();
		return;
	}
	var param = {
		"name": name,
		"password": password
	};
	$.post("/admin/login", param, function(result) {
		if(result.success == "true" || result.success == true) {
			window.location.href = "/admin_profile";
		}else {
			$(".login-form .alert-danger").hide();
			$("#show_info").html(result.msg);
			$(".login-form .alert-danger").show();
			return;
		}
	}, "json");
}