
$("#student_search").click(function() {
	$("#student_table").bootstrapTable('refresh');
});

$("#student_table").bootstrapTable({
	url: "/student/page_json",
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
    queryParams: function(params) {
    	params.search = $.trim($("#searchText").val());
    	return params;
    },
    pagination:true,
    toolbar: '#student-custom-toolbar',
    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
    pageNumber:1,                       //初始化加载第一页，默认第一页
    pageSize: 10,                       //每页的记录行数（*）
   	pageList: [25, 35, 50],        //可供选择的每页的行数（*）
	columns: [
		{
	        field: 'stuName',
	        title: '学生名',
	        align: 'left'
	    }, {
	        field: 'idCard',
	        title: '学号',
	        align: 'left'
	    }, {
	        field: 'stuSex',
	        title: '性别',
	        align: 'left'
	    }, {
	        field: 'stuAcademy',
	        title: '学院',
	        align: 'left'
	    }, {
	        field: 'stuClass',
	        title: '班级',
	        align: 'left'
	    }, {
	        field: 'stuEntranceTime',
	        title: '入学年份',
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
	        	'click .student_verify': function (e, value, row, index) {
	        	
	        		$("#student_verify_dialog").dialog({
	        			height: 150,
	        			width: 260,
	        			resizable: false,
	        			buttons: {
	        				"确定": function() {
	        					var status = $('#update_status option:selected').val();
	        					var param = {
	        						"stuId": row.stuId,
	        						"status": status
	        					};
	        					$.post("/student/status/update", param, function(result) {
	        						$("#student_verify_dialog").dialog("close");
	        						if (!result.success) {
	        							if (result.status == 404) {
	        								alert("没有权限");
	        							}else{
	        								alert(result.msg);
	        							}
									}else{
										$('#student_table').bootstrapTable('refresh', {});
									}
	        					}, "json");
	        				},
	        				"取消": function() {
	        					$("#student_verify_dialog").dialog( "close" );
	        			  	}
	        		     }
	        		});
		        },
		        'click .student_delete': function(e, value, row, index) {
		        	if(!confirm("确定删除学生<"+row.stuName+">")){
		        		return ;
		        	}
		        	$.post("/student/delete",{"id": row.stuId}, function(result) {
		        		if (result.success) {
		        			$("#student_table").bootstrapTable('refresh');
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
        '<span class="student_verify" style="cursor:pointer; color:#6666CC;">审核</span> ',
        '<span class="student_delete" style="cursor:pointer; color:#6666CC;">删除</span> '
    ].join('');       //数组转成字符串
}
