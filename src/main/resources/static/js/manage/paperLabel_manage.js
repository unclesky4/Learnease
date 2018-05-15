var questionlable_table = $("#paperLabel_table");

//添加问题标签
$("#paperLabel_add").click(function() {
	$("#add_paperLabel_name").val("");
	$("#add_paperLabel_code").val("");
	$("#add_paperLabel_description").val("");
	
	$("#add_paperLabel").dialog({
		height: 200,
		width: 450,
		resizable: false,
		buttons: {
			"添加": function() {
				var name = $("#add_paperLabel_name").val();
				var code = $("#add_paperLabel_code").val();
				var params = {
					"name": name,
					"code": code
				};
				$.post("/paperLabel/add",params,function(result){
					if(result.success == "true" || result.success == true) {
						$("#add_paperLabel").dialog( "close" );
						$('#paperLabel_table').bootstrapTable('refresh', {});
					}else{
						$("#add_paperLabel").dialog( "close" );
						alert(result.msg);
					}
				},"json");
			},
			"取消": function() {
				$("#add_paperLabel").dialog( "close" );
		  	}
	     },
	});
});

//刷新表格
$("#paperLabel_refresh").click(function() {
	$('#paperLabel_table').bootstrapTable('refresh', {});
});

questionlable_table.bootstrapTable({
	url: "/paperLabel/page_json",
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
    toolbar: '#paperLabel-custom-toolbar',
    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
    pageNumber:1,                       //初始化加载第一页，默认第一页
    pageSize: 10,                       //每页的记录行数（*）
   	pageList: [25, 35, 50],        //可供选择的每页的行数（*）
	columns: [
		{
	        field: 'name',
	        title: '试卷标签名称',
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
		        'click .paperLabel_update': function(e, value, row, index) {
		        	$("#update_paperLabel_name").val(row.name);
		        	$("#update_paperLabel_code").val(row.code);
		        	$("#update_paperLabel_description").val(row.description);
		        	
		        	$("#update_paperLabel").dialog({
		        		height: 200,
		        		width: 450,
		        		resizable: false,
		        		buttons: {
		        			"修改": function() {
		        				var name = $("#update_paperLabel_name").val();
		        				var code = $("#update_paperLabel_code").val();
		        				var description = $("#update_paperLabel_description").val();
		        				var params = {
		        					"id": row.id,
		        					"name": name
		        				};
		        				$.post("/paperLabel/update",params,function(result){
		        					if(result.success == "true" || result.success == true) {
		        						$("#update_paperLabel").dialog( "close" );
		        						$('#paperLabel_table').bootstrapTable('refresh', {});
		        					}else{
		        						$("#update_paperLabel").dialog( "close" );
		        						alert(result.msg);
		        					}
		        				},"json");
		        			},
		        			"取消": function() {
		        				$("#update_paperLabel").dialog( "close" );
		        		  	}
		        	     }
		        	});
		        },
		        'click .paperLabel_delete': function(e, value, row, index) {
		        	if(!confirm("确认删除<"+row.name+">标签?")){
		        		return ;
		        	}
		        	$.post("/paperLabel/delete",{"id": row.id}, function(result){
		        		if (result.success == true || result == "true") {
		        			$('#paperLabel_table').bootstrapTable('refresh', {});
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
        '<span class="paperLabel_update" style="cursor:pointer; color:#6666CC;"">修改</span> ',
        '<span class="paperLabel_delete" style="cursor:pointer; color:#6666CC;"">删除</span> ',
    ].join('');       //数组转成字符串
}