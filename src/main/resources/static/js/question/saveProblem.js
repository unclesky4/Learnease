
//添加单选题
function saveQuestionSimple() {
	var r = confirm("确定保存单选题?");
	if(r != true) {
		return;
	}
	var	topic = $.trim($("#questionsimple_topic").val());
	var	questionName = $.trim($("#questionsimple_questionName").val());
	var	analyse = $.trim($("#questionsimple_analyse").val());
	//选项
	var options = new Array();
	$($("#questionsimple_options input")).each(function() {
		//alert($(this).val());
		if ($(this).val() != "") {
			options.push($(this).val());
		}
	});
	//标签
	var labels = $("#questionsimple_labels").val();
	
	//难度
	var difficulty = $("#questionsimple_difficulty").find('option:selected').val();
	//参考答案
	var answer = $("#questionsimple_answerContent").val();
	
	if(topic=="" || topic.length > 20){
		alert("主题不能为空且长度须小于20");
		return false;
	}
	if(questionName == "" || questionName.length > 256) {
		alert('题目不能为空且长度须小于256');
	    return false;
	}
	
	if(answer == "" || answer.length > 512) {
		alert("请填写参考答案(长度小于512)");
		return;
	}
	if($.inArray(answer, options) == -1) {
		alert("参考答案不在选项中");
		return;
	}
	
	if(analyse == "" || analyse.length > 512) {
		alert('答案分析不能为空且长度须小于512');
	    return false;
	}
	
    $.ajax({
        url:'/simple/save',
        type:'post',
        async: false,
        dataType:'json',
        data:{
        	"shortName": topic,
        	"content": questionName,
            "difficulty":difficulty,
            "answerContent":answer,
            "labelIds":labels.join(","),
            "options":options.join(","),
            "analyse": analyse,
            "difficulty": difficulty
        },
        success: function(result){
            alert(result.msg);
        },
        error: function(xhr,textStatus) {
            console.log('错误'+" "+xhr+" "+textStatus);
        }
    });
}

//添加多选题
function saveQuestionMultiple() {
	var r = confirm("确定保存多选题?");
	if(r != true) {
		return;
	}
    var	topic = $.trim($("#questionmultiple_topic").val());
    var	questionName = $.trim($("#questionmultiple_questionName").val());
	var	analyse = $.trim($("#questionmultiple_analyse").val());
    var difficulty = $("#questionmultiple_difficulty").find('option:selected').val();
    //选项
	var options = new Array();
	$($("#questionmultiple_options input")).each(function() {
		if ($(this).val() != "") {
			options.push($(this).val());
		}
	});
	var labels = $("#questionmultiple_labels").val();
	//参考答案
	var answerContent = new Array();
	$($("#questionmultiple_answerContent input")).each(function() {
		if ($(this).val() != "") {
			answerContent.push($(this).val());
		}
	});

	if(topic=="" || topic.length > 20){
		alert("主题不能为空且长度须小于20");
		return false;
	}
	if(questionName == "" || questionName.length > 256) {
		alert('题目不能为空且长度须小于256');
	    return false;
	}
	
	if(labels.length == 0) {
		alert("请选择标签");
		return false;
	}

	var stop = "false";
	if(answerContent.length == 0) {
		alert("请填写参考答案");
		return;
	}
	for(var i=0; i<answerContent.length; i++) {
		if($.inArray(answerContent[i],options) == -1) {
			stop = "true";
			break;
		}
	}
	if(stop == "true"){
		alert("答案没有匹配选项");
		return;
	}
	
    $.ajax({
        url:'/multiple/save',
        type:'post',
        dataType:'json',
        async: false,
        data:{
        	"shortName": topic,
        	"content":questionName,
            "difficulty":difficulty,
            "analyse":analyse,
            "answerContent":answerContent.join(","),
            "labelIds":labels.join(","),
            "options":options.join(",")
        },
        success:function (data){
            alert(data.msg);
        },
        error:function (xhr,textStatus) {
            console.log('错误'+" "+xhr+" "+textStatus);
        }

    });
}

//添加判断题
function saveQuestionJudgement() {
	var r = confirm("确定保存判断题?");
	if(r != true) {
		return;
	}
	var topic = $.trim($("#questionjudgement_topic").val());
    var	questionName = $.trim($("#questionjudgement_questionName").val());
	var labelIds = $("#questionjudgement_labels").val();
	var difficulty = $("#questionjudgement_difficulty").find('option:selected').val();
	var answerContent = $("#questionjudgement_answerContent").find('option:selected').val();
	var	analyse = $.trim($("#questionjudgement_analyse").val());
	
	if(topic=="" || topic.length > 20){
		alert("主题不能为空且长度须小于20");
		return false;
	}
	if(questionName == "" || questionName.length > 256) {
		alert('题目不能为空且长度须小于256');
	    return false;
	}
	if(answerContent == ""){
		alert("请选择出正确答案！");
		return;
	}
	
	if(analyse=="" || analyse.length > 512){
	    alert('答案分析不能为空且长度须小于512');
	    return false;
	}
	
	if(labelIds.length == 0) {
		alert('请选择标签');
	    return false;
	}
	
    $.ajax({
        url: '/judgement/save',
        type: 'post',
        dataType: 'json',
        async: false,
        data: {
        	"shortName": topic,
        	"content":questionName,
            "difficulty": difficulty,
            "analyse": analyse,
            "answerContent": answerContent,
            "labelIds": labelIds.join(",")
        },
        success: function (result) {
            alert(result.msg);
        },
        error: function (xhr, textStatus) {
            console.log('错误' + " " + xhr + " " + textStatus);
        }
    });

}

//添加填空题
function saveQuestionBlank() {
	var r = confirm("确定保存填空题?");
	if(r != true) {
		return;
	}
	var topic = $.trim($("#questionblank_topic").val());
    var	questionName = $.trim($("#questionblank_questionName").val());
	var labelIds = $("#questionblank_labels").val();
	var difficulty = $("#questionblank_difficulty").find('option:selected').val();
	var answerContent = $.trim($("#questionblank_answerContent").val());
	var	analyse = $.trim($("#questionblank_analyse").val());
	
	if(topic == "" || topic.length > 20){
		alert("主题不能为空且长度须小于20");
		return false;
	}
	if(questionName == "" || questionName.length > 256) {
		alert('题目不能为空且长度须小于256');
	    return false;
	}
	if(analyse == "" || analyse.length > 512) {
		alert('答案分析不能为空且长度须小于512');
	    return false;
	}
	if(labelIds.length == 0)  {
		alert('请填写标签！');
	    return false;
	}
	if (answerContent == "") {
		alert('请填写参考答案！');
	    return false;
	}
    $.ajax({
        url:'/blank/save',
        type: 'post',
        dataType: 'json',
        async: false,
        data: {
        	"shortName": topic,
        	"content":questionName,
            "answerContent":answerContent,
            "labelIds": labelIds.join(","),
            "difficulty": difficulty,
            "analyse": analyse
        },
        success: function (result) {
            alert(result.msg);
        },
        error: function (xhr, textStatus) {
            console.log('错误' + " " + xhr + " " + textStatus);
        }
    });
}

//添加编程题
function saveQuestionProgram() {
	var r = confirm("确定保存编程题?");
	if(r != true) {
		return;
	}
	var topic = $.trim($("#questionprogram_topic").val());
    var	questionName = $.trim($("#questionprogram_questionName").val());
    var	description = $.trim($("#questionprogram_description").val());
	var labelIds = $("#questionprogram_labels").val();
	var difficulty = $("#questionprogram_difficulty").find('option:selected').val();
	var answerContent = $.trim($("#questionprogram_answerContent").val());
	var	analyse = $.trim($("#questionprogram_analyse").val());
    var input = $.trim($("#questionprogram_input").val());
    var output = $.trim($("#questionprogram_output").val());
    var inputExample = $.trim($("#questionprogram_exampleInput").val());
    var outputExample = $.trim($("#questionprogram_exampleOutput").val());
    var hint = $.trim($("#questionprogram_hint").val());
    
	if(topic=="" || topic.length > 20){
		alert("主题不能为空且长度须小于20");
		return false;
	}
	if(questionName == "" || questionName.length > 256) {
		alert('题目不能为空且长度须小于256');
	    return false;
	}
	if(description =="" || description.length > 256){
	    alert('题目描述不能为空且长度须小于20');
	    return false;
	}
	if(analyse == "" || analyse.length > 512) {
		alert('答案分析不能为空且长度须小于512');
	    return false;
	}
	if(labelIds.length == 0)  {
		alert('请填写标签！');
	    return false;
	}
	
	if(input.length > 100 || output.length > 100 
			|| inputExample.length > 100 || outputExample.length > 100 || hint.length > 100) {
		alert("输入，输出，示例输入，示例输出，提示长度须小于100");
		return;
	}
    $.ajax({
        url:'/program/save',
        type: 'post',
        dataType: 'json',
        async: false,
        data: {
        	"shortName": topic,
        	"content":questionName,
            "description": description,
            "input" : input,
            "output" : output,
            "exampleInput":inputExample,
            "exampleOutput":outputExample,
            "labelIds": labelIds.join(","),
            "difficulty": difficulty,
            "hint": hint,
            "answerContent": answerContent,
            "analyse": analyse,
        },
        success: function (result) {
            alert(result.msg);
        },
        error: function (xhr, textStatus) {
            console.log('错误' + " " + xhr + " " + textStatus);
        }
    });
}

