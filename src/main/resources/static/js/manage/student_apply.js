var student_table = $("#student_table");

//刷新表格
$("#student_table_refresh").click(function() {
	$('#student_table').bootstrapTable('refresh', {});
});

student_table.bootstrapTable({
	url: "/student/verification",
	method:'get',
	sortOrder: 'desc',
    dataType:'json',
    width: "100%",
    queryParamsType: "",
    pagination:true,
    toolbar: '#student-custom-toolbar',
    sidePagination: "server",
    pageNumber:1,
    pageSize: 10,
   	pageList: [25, 35, 50],
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
	        			height: 180,
	        			width: 300,
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
	        							alert(result.msg);
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
		        }
	        },
	        formatter: operateFormatter
	    }
	]
});

function operateFormatter(value, row, index) {
    return [
        '<span class="student_verify" style="cursor:pointer; color:#6666CC;">审核</span>'
    ].join('');       //数组转成字符串
}

