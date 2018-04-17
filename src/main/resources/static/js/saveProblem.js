//添加单选题
function saveQuestionSimple() {
	var r = confirm("确定保存单选题?");
	if(r != true) {
		return;
	}
	var questionNumber = $.trim($("#questionsimple_questionNumber").val());
	var	topic = $.trim($("#questionsimple_topic").val());
	var	questionName = questionName_editor.$txt.html();
	var	description =  description_editor.$txt.html();// 插件
	var	analyse = analyse_editor.$txt.html();
	//选项
	var _options = $.trim($("#questionsimple_options_tagsinput").children(".tag").children("span").text());
	options = _options.split(/\s+/);
	//标签
	var _labels = $.trim($("#questionsimple_labels_tagsinput").children(".tag").children("span").text());
	var labels = _labels.split(/\s+/);
	//难度
	var difficulty = $("#questionsimple_difficulty").find('option:selected').val();
	//参考答案
	var answer = $("#questionsimple_answerContent").val();
	
	if(!(/^\d+$/.test(questionNumber)) || questionNumber.length > 6){
		alert("题目编号为正整数且长度须小于6");
		return false;
	}
	if(topic=="" || topic.length > 20){
		alert("主题不能为空且长度须小于20");
		return false;
	}
	if(questionName == "" || questionName.length > 256) {
		alert('题目不能为空且长度须小于256');
	    return false;
	}
	
	if(description=="" || description.length > 256){
	    alert('题目描述不能为空且长度须小于20');
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
	if(!(/^\d+$/.test(questionNumber))){
		alert("题目编号为正整数");
		return false;
	}
	
	if(analyse == "" || analyse.length > 512) {
		alert('答案分析不能为空且长度须小于512');
	    return false;
	}
	
    $.ajax({
        url:'/learnease/questionsimple/save.action',
        type:'post',
        async: false,
        dataType:'text',
        data:{
        	"questionNumber": questionNumber,
        	"topic": topic,
        	"questionName": questionName,
            "difficulty":difficulty,
            "description":description,
            "answerContent":answer,
            "labels":labels.join(","),
            "options":options.join(","),
            "analyse": analyse
        },
        success: function(result){
            alert(result);
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
    var questionNumber = $.trim($("#questionmultiple_questionNumber").val());
    var	topic = $.trim($("#questionmultiple_topic").val());
    var	questionName = questionmultiple_questionName_editor.$txt.html();
    var	description =  questionmultiple_description_editor.$txt.html();
    var analyse = questionmultiple_analyse_editor.$txt.html();
    var difficulty = $("#questionmultiple_difficulty").find('option:selected').val();
    var _otions = $.trim($("#questionmultiple_options_tagsinput").children(".tag").children("span").text());
    var  _labels = $.trim($("#questionmultiple_labels_tagsinput").children(".tag").children("span").text());
    var  _answerContent = $.trim($("#questionmultiple_answerContent_tagsinput").children(".tag").children("span").text());
    if(!(/^\d+$/.test(questionNumber)) || questionNumber.length > 6){
		alert("题目编号为正整数且长度须小于6");
		return false;
	}
	if(topic=="" || topic.length > 20){
		alert("主题不能为空且长度须小于20");
		return false;
	}
	if(questionName == "" || questionName.length > 256) {
		alert('题目不能为空且长度须小于256');
	    return false;
	}
	
	if(description===""){
	    alert('请填写题目描述');
	    return false;
	}
	var labels = _labels.split(/\s+/);
	if(labels.length == 0) {
		alert("请设置标签");
		return;
	}
	var options = _otions.split(/\s+/);
	var answerContent = _answerContent.split(/\s+/);
	var stop = "false";
	if(answerContent.length == 0) {
		alert("请填写参考答案");
		return;
	}
	if(options.length < answerContent.length) {
		alert("答案的个数与选项个数冲突");
		return;
	}
	for(var i=0; i<answerContent.length; i++) {
		if($.inArray(answerContent[1],options) == -1) {
			stop = "true";
			break;
		}
	}
	if(stop == "true"){
		alert("答案没有匹配选项");
		return;
	}
	
	if(analyse==""){
	    alert('请填写答案分析');
	    return false;
	}
	
    $.ajax({
        url:'/learnease/questionmultiple/save.action',
        type:'post',
        dataType:'text',
        async: false,
        data:{
        	"questionNumber":questionNumber,
        	"topic": topic,
        	"questionName":questionName,
            "difficulty":difficulty,
            "analyse":analyse,
            "description":description,
            "answerContent":answerContent.join(","),
            "labels":labels.join(","),
            "options":options.join(",")
        },
        success:function (){
            alert('添加成功！')
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
    var questionNumber = $.trim($("#questionjudgement_questionNumber").val());
	var topic = $.trim($("#questionjudgement_topic").val());
	var questionName = questionjudgement_questionName_editor.$txt.html();
	var description =  questionjudgement_description_editor.$txt.html();
	var analyse = questionjudgement_analyse_editor.$txt.html();
	var _labels = $.trim($("#questionjudgement_labels_tagsinput").children(".tag").children("span").text());
	var difficulty = $("#questionjudgement_difficulty").find('option:selected').val();
	var answerContent = $("#questionjudgement_answerContent").find('option:selected').val();
	var options = new Array("对","错");
	
    if(!(/^\d+$/.test(questionNumber)) || questionNumber.length > 6){
		alert("题目编号为正整数且长度须小于6");
		return false;
	}
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
	if(description=="" || description.length > 256){
	    alert('题目描述不能为空且长度须小于20');
	    return false;
	}
	
	if(analyse=="" || analyse.length > 512){
	    alert('答案分析不能为空且长度须小于512');
	    return false;
	}
	var labels = _labels.split(/\s+/);
	if(labels.length == 0) {
		alert('请填写标签');
	    return false;
	}
	
    $.ajax({
        url: '/learnease/questionjudgement/save.action',
        type: 'post',
        dataType: 'text',
        async: false,
        data: {
        	"questionNumber":questionNumber,
        	"topic": topic,
        	"questionName":questionName,
            "difficulty": difficulty,
            "analyse": analyse,
            "description": description,
            "answerContent": answerContent,
            "labels": labels.join(","),
            "options": options.join(",")
        },
        success: function (result) {
            alert(result);
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
	var questionNumber = $.trim($("#questionblank_questionNumber").val());
	var topic = $.trim($("#questionmlank_topic").val());
	var questionName = questionblank_questionName_editor.$txt.html();
	var description =  questionblank_description_editor.$txt.html();
	var _labels = $.trim($("#questionblank_labels_tagsinput").children(".tag").children("span").text());
	var difficulty = $("#questionmblank_difficulty").find('option:selected').val();
	var analyse = questionblank_analyse_editor.$txt.html();
	var _answerContent = $.trim($("#questionmblank_answerContent_tagsinput").children(".tag").children("span").text());
	if(!(/^\d+$/.test(questionNumber)) || questionNumber.length > 6){
		alert("题目编号为正整数且长度须小于6");
		return false;
	}
	if(topic=="" || topic.length > 20){
		alert("主题不能为空且长度须小于20");
		return false;
	}
	if(questionName == "" || questionName.length > 256) {
		alert('题目不能为空且长度须小于256');
	    return false;
	}
	if(questionName == "" || questionName.length > 256) {
		alert('题目不能为空且长度须小于256');
	    return false;
	}
	if(description=="" || description.length > 256){
	    alert('题目描述不能为空且长度须小于20');
	    return false;
	}
	if(analyse == "" || analyse.length > 512) {
		alert('答案分析不能为空且长度须小于512');
	    return false;
	}
	var labels = _labels.split(/\s+/);
	if(labels.length == 0)  {
		alert('请填写标签！');
	    return false;
	}
	if(labels[0] == "请按顺序输入答案") {
		labels = labels.splice(0,1);
	}
	var answerContent = _answerContent.split(/\s+/);
    $.ajax({
        url:'/learnease/questionblank/save.action',
        type: 'post',
        dataType: 'text',
        async: false,
        data: {
        	"questionNumber": questionNumber,
        	"topic": topic,
        	"questionName":questionName ,
            "description": description,
            "answerContent":answerContent.join(","),
            "labels": labels.join(","),
            "difficulty": difficulty,
            "analyse": analyse
        },
        success: function (result) {
            alert(result);
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
    var questionNumber = $.trim($("#questionprogram_questionNumber").val());
    var	topic = $.trim($("#questionprogram_topic").val());
    var	questionName = questionprogram_questionName_editor.$txt.html();
    var	description =  questionprogram_description_editor.$txt.html();
    var _labels = $.trim($("#questionprogram_labels_tagsinput").children(".tag").children("span").text());
    var difficulty = $("#questionprogram_difficulty").find('option:selected').val();
    var input = questionprogram_input_editor.$txt.html();
    var output = questionprogram_output_editor.$txt.html();
    var inputSample = questionprogram_exampleinput_editor.$txt.html();
    var outputSample = questionprogram_exampleoutput_editor.$txt.html();
    var hint = questionprogram_hint_editor.$txt.html();
    var answerContent = questionprogram_answerContent_editor.$txt.html();
    var analyse = questionprogram_analyse_editor.$txt.html();
	
    if(!(/^\d+$/.test(questionNumber)) || questionNumber.length > 6){
		alert("题目编号为正整数且长度须小于6");
		return false;
	}
	if(topic=="" || topic.length > 20){
		alert("主题不能为空且长度须小于20");
		return false;
	}
	if(questionName == "" || questionName.length > 256) {
		alert('题目不能为空且长度须小于256');
	    return false;
	}
	if(description=="" || description.length > 256){
	    alert('题目描述不能为空且长度须小于20');
	    return false;
	}
	if(analyse == "" || analyse.length > 512) {
		alert('答案分析不能为空且长度须小于512');
	    return false;
	}
	var labels = _labels.split(/\s+/);
	if(labels.length == 0)  {
		alert('请填写标签！');
	    return false;
	}
	if(labels[0] == "请按顺序输入答案") {
		labels = labels.splice(0,1);
	}
	
	if(input.length > 100 || output.length > 100 
			|| inputSample.length > 100 || outputSample.length > 100 || hint.length > 100) {
		alert("输入，输出，示例输入，示例输出，提示长度须小于100");
		return;
	}
/*	alert(hint = questionprogram_hint_editor.$txt.html());
	alert(hint = questionprogram_hint_editor.$txt.html());*/
    $.ajax({
        url:'/learnease/questionprogram/save.action',
        type: 'post',
        dataType: 'text',
        async: false,
        data: {
        	"questionNumber":questionNumber,
        	"topic": topic,
        	"questionName":questionName,
            "description": description,
            "input" : input,
            "output" : output,
            "exampleInput":inputSample,
            "exampleOutput":outputSample,
            "labels": labels.join(","),
            "difficulty": difficulty,
            "hint": hint,
            "answerContent": answerContent,
            "analyse": analyse,
        },
        success: function (result) {
            alert(result);
        },
        error: function (xhr, textStatus) {
            console.log('错误' + " " + xhr + " " + textStatus);
        }
    });
}