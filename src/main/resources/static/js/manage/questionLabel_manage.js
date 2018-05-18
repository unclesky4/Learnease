var questionlable_table = $("#questionLabel_table");

//添加问题标签
$("#questionLabel_add").click(function() {
	$("#add_questionLabel_name").val("");
	$("#add_questionLabel_code").val("");
	$("#add_questionLabel_description").val("");
	
	$("#add_questionLabel").dialog({
		height: 200,
		width: 450,
		resizable: false,
		buttons: {
			"添加": function() {
				var name = $("#add_questionLabel_name").val();
				var code = $("#add_questionLabel_code").val();
				var params = {
					"name": name,
					"code": code
				};
				$.post("/questionLabel/add",params,function(result){
					$("#add_questionLabel").dialog( "close" );
					if(result.success == "true" || result.success == true) {
						$('#questionLabel_table').bootstrapTable('refresh', {});
					}else{
						if (result.status == 404) {
							alert("没有权限");
						}else{
							alert(result.msg);
						}
					}
				},"json");
			},
			"取消": function() {
				$("#add_questionLabel").dialog( "close" );
		  	}
	     },
	});
});

//刷新表格
$("#questionLabel_refresh").click(function() {
	$('#questionLabel_table').bootstrapTable('refresh', {});
});

questionlable_table.bootstrapTable({
	url: "/questionLabel/page_json",
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
    toolbar: '#questionLabel-custom-toolbar',
    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
    pageNumber:1,                       //初始化加载第一页，默认第一页
    pageSize: 10,                       //每页的记录行数（*）
   	pageList: [25, 35, 50],        //可供选择的每页的行数（*）
	columns: [
		{
	        field: 'name',
	        title: '题目标签名称',
	        align: 'left'
	    }, {
	        field: 'createTime',
	        title: '创建时间',
	        align: 'left'
	    },{
	        field: 'operate',
	        title: '操作',
	        //width: "15%",
	        align: 'left',
	        events: {
		        'click .questionLabel_update': function(e, value, row, index) {
		        	$("#update_questionLabel_name").val(row.name);
		        	$("#update_questionLabel_code").val(row.code);
		        	$("#update_questionLabel_description").val(row.description);
		        	
		        	$("#update_questionLabel").dialog({
		        		height: 200,
		        		width: 450,
		        		resizable: false,
		        		buttons: {
		        			"修改": function() {
		        				var name = $("#update_questionLabel_name").val();
		        				var code = $("#update_questionLabel_code").val();
		        				var description = $("#update_questionLabel_description").val();
		        				var params = {
		        					"id": row.id,
		        					"name": name
		        				};
		        				$.post("/questionLabel/update",params,function(result){
		        					$("#update_questionLabel").dialog( "close" );
		        					if(result.success == "true" || result.success == true) {
		        						$('#questionLabel_table').bootstrapTable('refresh', {});
		        					}else{
		        						if (result.status == 404) {
		        							alert("没有权限");
		        						}else{
		        							alert(result.msg);
		        						}
		        					}
		        				},"json");
		        			},
		        			"取消": function() {
		        				$("#update_questionLabel").dialog( "close" );
		        		  	}
		        	     }
		        	});
		        },
		        'click .questionLabel_delete': function(e, value, row, index) {
		        	if(!confirm("确认删除<"+row.name+">标签?")){
		        		return ;
		        	}
		        	$.post("/questionLabel/delete",{"id": row.id}, function(result){
		        		if (result.success == true || result == "true") {
		        			$('#questionLabel_table').bootstrapTable('refresh', {});
						}else{
							if (result.status == 404) {
								alert("没有权限");
							}else{
								alert(result.msg);
							}
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
        '<span class="questionLabel_update" style="cursor:pointer; color:#6666CC;"">修改</span> ',
        '<span class="questionLabel_delete" style="cursor:pointer; color:#6666CC;"">删除</span> ',
    ].join('');       //数组转成字符串
}