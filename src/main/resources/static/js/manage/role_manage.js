var questionlabel_table = $("#role_table");

//添加角色
$("#role_add").click(function() {
	$("#add_role_name").val("");
	$("#add_role_code").val("");
	$("#add_role_description").val("");
	
	$("#add_role").dialog({
		height: 200,
		width: 450,
		resizable: false,
		buttons: {
			"添加": function() {
				var name = $("#add_role_name").val();
				var code = $("#add_role_code").val();
				var description = $("#add_role_description").val();
				var params = {
					"name": name,
					"code": code,
					"description": description
				};
				$.post("/role/add",params,function(result){
					if(result.success == "true" || result.success == true) {
						$("#add_role").dialog( "close" );
						$('#role_table').bootstrapTable('refresh', {});
					}else{
						$("#add_role").dialog( "close" );
						alert(result.msg);
					}
				},"json");
			},
			"取消": function() {
				$("#add_role").dialog( "close" );
		  	}
	     },
	});
});

//刷新表格
$("#role_refresh").click(function() {
	$('#role_table').bootstrapTable('refresh', {});
});

questionlabel_table.bootstrapTable({
	url: "/role/page_json",
	method:'get',
	sortOrder: 'desc',
    dataType:'json',
    width: "100%",
    contentType: "application/json",
    striped: true,   //是否显示行间隔色
    cache: false,
    search: false,        //查找输入框
    showColumns: false,      //True to show the columns drop down list
    showRefresh: false,    //True to show the refresh button
    showPaginationSwitch: false,
    queryParamsType: "",
    pagination:true,
    toolbar: '#role-custom-toolbar',
    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
    pageNumber:1,                       //初始化加载第一页，默认第一页
    pageSize: 10,                       //每页的记录行数（*）
   	pageList: [25, 35, 50],        //可供选择的每页的行数（*）
	columns: [
		{
	        field: 'name',
	        title: '角色名称',
	        width: "25%",
	        align: 'left'
	    }, {
	        field: 'code',
	        title: '代码',
	        width: "20%",
	        align: 'left'
	    },{
	        field: 'description',
	        title: '描述',
	        width: "40%",
	        align: 'left'
	    },{
	        field: 'operate',
	        title: '操作',
	        width: "15%",
	        align: 'left',
	        events: {
	        	'click .role_permission': function (e, value, row, index) {
	        		var treeObject = $.fn.zTree.getZTreeObj("permission_tree");
	        		//展开
	        		treeObject.expandAll(true);
	        		//取消打勾
	        		treeObject.checkAllNodes(false);
	        		
	        		//获取该角色的权限
	        		var permissions = $.get("/role/permissions", {"id": row.id}, function(result) {
	        			$.each(result, function(i,o) {
	        				var node = treeObject.getNodesByParam("id", o.id, null);
	        				//alert(o.id+"  "+JSON.stringify(node));
	        				treeObject.checkNode(node[0],true,false);
	        			});
	        		}, "json");
	        		
	        		$("#permission_role").dialog({
	        			height: 480,
	        			width: 260,
	        			resizable: false,
	        			buttons: {
	        				"保存": function() {
	        					//获取打勾的节点
	        					var data = treeObject.getCheckedNodes(true);
	        					
	        					var permissionIds = new Array();
	        					$.each(data, function(index, object) {
	        						permissionIds.push(object.id);
	        					});
	        					var param = {
	        						"id": row.id,
	        						"permissionIds": permissionIds.join(","),
	        					};
	        					$.post("/role/update/permission", param, function(result) {
	        						$("#permission_role").dialog("close");
	        						if (!result.success) {
	        							alert(result.msg);
									}
	        					}, "json");
	        				},
	        				"取消": function() {
	        					$("#permission_role").dialog( "close" );
	        			  	}
	        		     }
	        		});
		        },
		        'click .role_update': function(e, value, row, index) {
		        	$("#update_role_name").val(row.name);
		        	$("#update_role_code").val(row.code);
		        	$("#update_role_description").val(row.description);
		        	
		        	$("#update_role").dialog({
		        		height: 200,
		        		width: 450,
		        		resizable: false,
		        		buttons: {
		        			"修改": function() {
		        				var name = $("#update_role_name").val();
		        				var code = $("#update_role_code").val();
		        				var description = $("#update_role_description").val();
		        				var params = {
		        					"id": row.id,
		        					"name": name,
		        					"code": code,
		        					"description": description
		        				};
		        				$.post("/role/update",params,function(result){
		        					if(result.success == "true" || result.success == true) {
		        						$("#update_role").dialog( "close" );
		        						$('#role_table').bootstrapTable('refresh', {});
		        					}else{
		        						$("#update_role").dialog( "close" );
		        						alert(result.msg);
		        					}
		        				},"json");
		        			},
		        			"取消": function() {
		        				$("#update_role").dialog( "close" );
		        		  	}
		        	     }
		        	});
		        },
		        'click .role_delete': function(e, value, row, index) {
		        	if(!confirm("确认删除<"+row.name+">角色?")){
		        		return ;
		        	}
		        	$.post("/role/delete",{"id": row.id}, function(result){
		        		if (result.success == true || result == "true") {
		        			$('#role_table').bootstrapTable('refresh', {});
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
        '<span class="role_permission" style="cursor:pointer; color:#6666CC;">权限</span> ',
        '<span class="role_update" style="cursor:pointer; color:#6666CC;"">修改</span> ',
        '<span class="role_delete" style="cursor:pointer; color:#6666CC;"">删除</span> ',
    ].join('');       //数组转成字符串
}

//权限初始化
var permission_tree = {
	expand: true,
	init: function() {
		$.get("/permission/valid",{},function(result) {
			var setting = {
				edit: {
				   drag: {
					   autoExpandTrigger: false,
					   isCopy: false,
					   isMove: false
				   },
				   enable: false,
				   showRemoveBtn: false,
				   showRenameBtn: false
			   	},
				check : {enable : true,nocheckInherit : true},
				data: {simpleData: {enable: true}},
				view: {
					showIcon: false,
					addHoverDom: permission_tree.addHoverDom,
					removeHoverDom: permission_tree.removeHoverDom
				}
			};
			$.fn.zTree.init($("#permission_tree"), setting, result).expandAll(true);
		},"json");
	}
};

$(function() {
	permission_tree.init();
});