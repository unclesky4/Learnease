var admin_table = $("#admin_table");

//添加管理员
$("#admin_add").click(function() {
	$("#add_admin_name").val("");
	$("#add_admin_phone").val("");
	$("#add_admin_email").val("");
	$("#add_admin_pwd").val("");
	$("#add_admin_repeatPwd").val("");
	
	$("#add_admin").dialog({
		height: 430,
		width: 450,
		resizable: false,
		buttons: {
			"添加": function() {
				var name = $("#add_admin_name").val();
				var sex = $("#add_admin_sex").val();
				var phone = $("#add_admin_phone").val();
				var email = $("#add_admin_email").val();
				var pwd = $.trim($("#add_admin_pwd").val());
				var repeatPwd = $.trim($("#add_admin_repeatPwd").val());
				var params = {
					"name": name,
					"sex": sex,
					"phone": phone,
					"email": email,
					"pwd": pwd,
					"repeatPwd": repeatPwd
				};
				$.post("/admin/add",params,function(result){
					if(result.success == "true" || result.success == true) {
						$("#add_admin").dialog( "close" );
						$('#admin_table').bootstrapTable('refresh', {});
					}else{
						$("#add_admin").dialog( "close" );
						alert(result.msg);
					}
				},"json");
			},
			"取消": function() {
				$("#add_admin").dialog( "close" );
		  	}
	     },
	});
});


//刷新表格
$("#admin_refresh").click(function() {
	$('#admin_table').bootstrapTable('refresh', {});
});

//获取所有角色
$.get("/role/all",{},function(result) {{
	var array = new Array();
	$.each(result, function(index, object) {
		array.push("<label name='check' class='checkbox'><input type='checkbox' value='"+object.id+"'> "+object.name+" </label>");
	});
	$("#role_list").html(array.join(""));
}}, "json");

admin_table.bootstrapTable({
	url: "/admin/page_json",
	method:'get',
	sortOrder: 'desc',
    dataType:'json',
    width: "100%",
    contentType: "application/json",
    striped: false,   //是否显示行间隔色
    cache: false,
    search: false,        //查找输入框
    showColumns: false,      //True to show the columns drop down list
    showRefresh: false,    //True to show the refresh button
    showPaginationSwitch: false,
    queryParamsType: "",
    pagination:true,
    toolbar: '#admin-custom-toolbar',
    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
    pageNumber:1,                       //初始化加载第一页，默认第一页
    pageSize: 10,                       //每页的记录行数（*）
   	pageList: [25, 35, 50],        //可供选择的每页的行数（*）
	columns: [
		{
	        field: 'name',
	        title: '管理员姓名',
	        width: '15%',
	        align: 'left'
	    }, {
	        field: 'sex',
	        title: '性别',
	        width: '10%',
	        align: 'left'
	    },{
	        field: 'phone',
	        title: '电话',
	        width: '20%',
	        align: 'left'
	    },{
	        field: 'email',
	        title: '邮箱',
	        width: '25%',
	        align: 'left'
	    },{
	        field: 'operate',
	        title: '操作',
	        width: '30%',
	        align: 'left',
	        events: {
	        	'click .admin_role': function (e, value, row, index) {
	        		$(":checkbox").prop("checked",false);
	        		//获取管理员的角色信息
	        		$.get("/admin/roles",{"id": row.id},function(result) {
	        			$.each(result, function(index, object) {
	        				$(":checkbox[value='"+object.id+"']").prop("checked",true);
	        			});
	        		}, "json");
	        		
	        		$("#role_admin").dialog({
	        			height: 400,
	        			width: 260,
	        			resizable: false,
	        			buttons: {
	        				"保存": function() {
	        					
	        					var roleIds = new Array();
	        					$.each($('input:checkbox:checked'),function(){
	        		                roleIds.push($(this).val());
	        		            });
	        					alert(roleIds);
	        					var param = {
	        						"id": row.id,
	        						"roleIds": roleIds.join(","),
	        					};
	        					$.post("/admin/role/update", param, function(result) {
	        						$("#role_admin").dialog("close");
	        						if (!result.success) {
	        							alert(result.msg);
									}
	        					}, "json");
	        				},
	        				"取消": function() {
	        					$("#role_admin").dialog( "close" );
	        			  	}
	        		     }
	        		});
		        },
		        'click .admin_update': function(e, value, row, index) {
		        	$("#update_admin_name").val(row.name);
		        	$("#update_admin_sex").find("option[value='"+row.sex+"']").attr("selected",true);
		        	$("#update_admin_phone").val(row.code);
		        	$("#update_admin_email").val(row.description);
		        	
		        	$("#update_admin").dialog({
		        		height: 340,
		        		width: 450,
		        		resizable: false,
		        		buttons: {
		        			"修改": function() {
		        				var name = $("#update_admin_name").val();
		        				var sex = $("#update_admin_sex").val();
		        				var phone = $("#update_admin_phone").val();
		        				var email = $("#update_admin_mail").val();
		        				var params = {
		        					"id": row.id,
		        					"sex": sex,
		        					"name": name,
		        					"phone": phone,
		        					"email": email
		        				};
		        				$.post("/admin/update",params,function(result){
		        					if(result.success == "true" || result.success == true) {
		        						$("#update_admin").dialog( "close" );
		        						$('#admin_table').bootstrapTable('refresh', {});
		        					}else{
		        						$("#update_admin").dialog( "close" );
		        						alert(result.msg);
		        					}
		        				},"json");
		        			},
		        			"取消": function() {
		        				$("#update_admin").dialog( "close" );
		        		  	}
		        	     }
		        	});
		        },
		        'click .admin_pwd_update': function(e, value, row, index) {
		        	$("#update_pwd").val("");
    				$("#update_pwd_repeat").val("");
    				
		        	$("#pwd_update").dialog({
		        		height: 240,
		        		width: 450,
		        		resizable: false,
		        		buttons: {
		        			"修改": function() {
		        				var pwd = $.trim($("#update_pwd").val());
		        				var pwd_repeat = $.trim($("#update_pwd_repeat").val());
		        				
		        				var params = {
		        					"id": row.id,
		        					"newPwd": pwd,
		        					"repeatPwd": pwd_repeat
		        				};
		        				$.post("/admin/password/update",params,function(result){
		        					if(result.success == "true" || result.success == true) {
		        						$("#pwd_update").dialog( "close" );
		        						alert(result.msg);
		        					}else{
		        						$("#pwd_update").dialog( "close" );
		        						alert(result.msg);
		        					}
		        				},"json");
		        			},
		        			"取消": function() {
		        				$("#pwd_update").dialog( "close" );
		        		  	}
		        	     }
		        	});
				},
		        'click .admin_delete': function(e, value, row, index) {
		        	if(!confirm("确认删除管理员<"+row.name+">?")){
		        		return ;
		        	}
		        	$.post("/admin/delete",{"id": row.id}, function(result){
		        		if (result.success == true || result == "true") {
		        			$('#admin_table').bootstrapTable('refresh', {});
						}else{
							alert(result.msg);
						}
		        	}, "json");
		        }
	        },
	        formatter: operateFormatter
	    }
	]
});

function operateFormatter(value, row, index) {
    return [
        '<span class="admin_role" style="cursor:pointer; color:#6666CC;">指派角色</span> ',
        '<span class="admin_update" style="cursor:pointer; color:#6666CC;"">修改信息</span> ',
        '<span class="admin_pwd_update" style="cursor:pointer; color:#6666CC;"">重置密码</span> ',
        '<span class="admin_delete" style="cursor:pointer; color:#6666CC;"">删除</span> '
    ].join('');       //数组转成字符串
}