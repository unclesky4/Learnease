//刷新表格
$("#teacher_refresh").click(function() {
	$("#teacher_table").bootstrapTable('refresh', {});
});

$("#teacher_table").bootstrapTable({
	url: "/teacher/verification",
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
    toolbar: '#teacher-custom-toolbar',
    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
    pageNumber:1,                       //初始化加载第一页，默认第一页
    pageSize: 10,                       //每页的记录行数（*）
   	pageList: [25, 35, 50],        //可供选择的每页的行数（*）
	columns: [
		{
	        field: 'name',
	        title: '教师姓名',
	        align: 'left'
	    }, {
	        field: 'sex',
	        title: '性别',
	        align: 'left'
	    }, {
	        field: 'idCard',
	        title: '职工号',
	        align: 'left'
	    }, {
	        field: 'phone',
	        title: '电话',
	        align: 'left'
	    }, {
	        field: 'subject',
	        title: '学科',
	        align: 'left'
	    }, {
	        field: 'status',
	        title: '审核状态',
	        align: 'left'
	    }, {
	        field: 'operate',
	        title: '操作',
	        width: "15%",
	        align: 'left',
	        events: {
	        	'click .teacher_verify': function (e, value, row, index) {
	        	
	        		$("#teacher_verify_dialog").dialog({
	        			height: 180,
	        			width: 300,
	        			resizable: false,
	        			buttons: {
	        				"确定": function() {
	        					var status = $('#update_status option:selected').val();
	        					var param = {
	        						"id": row.tid,
	        						"status": status
	        					};
	        					$.post("/teacher/status/update", param, function(result) {
	        						$("#teacher_verify_dialog").dialog("close");
	        						if (!result.success) {
	        							if (result.status == 404) {
	        								alert("没有权限");
	        							}else{
	        								alert(result.msg);
	        							}
									}else{
										$('#teacher_table').bootstrapTable('refresh', {});
									}
	        					}, "json");
	        				},
	        				"取消": function() {
	        					$("#teacher_verify_dialog").dialog( "close" );
	        			  	}
	        		     }
	        		});
		        }
	        },
	        formatter: operateFormatter
	    }
	]
});

function operateFormatter(value, row, index) {
    return [
        '<span class="teacher_verify" style="cursor:pointer; color:#6666CC;">审核</span> '
    ].join('');       //数组转成字符串
}
