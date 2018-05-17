var user_table = $("#user_table");

//刷新表格
$("#user_refresh").click(function() {
	$('#user_table').bootstrapTable('refresh', {});
});

user_table.bootstrapTable({
	url: "/user/page_json",
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
    toolbar: '#user-custom-toolbar',
    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
    pageNumber:1,                       //初始化加载第一页，默认第一页
    pageSize: 10,                       //每页的记录行数（*）
   	pageList: [25, 35, 50],        //可供选择的每页的行数（*）
	columns: [
		{
	        field: 'name',
	        title: '用户名称',
	        align: 'left'
	    }, {
	        field: 'email',
	        title: '邮箱',
	        align: 'left'
	    }, {
	        field: 'validation',
	        title: '验证状态',
	        align: 'left'
	    }, {
	        field: 'registerTime',
	        title: '创建时间',
	        align: 'left'
	    }
	]
});