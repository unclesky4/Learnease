var search = location.search;
var questionId = search.split("?id=")[1];

var optionArray = new Array();
$.get("/multiple/findById", {"id": questionId}, function(result) {
	$("#multiple_content").html(result.content);
	switch(result.difficulty) {
		case 1:
			$("#multiple_difficulty").html("★");
			break;
		case 2:
			$("#multiple_difficulty").html("★★");
			break;
		case 3:
			$("#multiple_difficulty").html("★★★");
			break;
		case 4:
			$("#multiple_difficulty").html("★★★★");
			break;
		case 5:
			$("#multiple_difficulty").html("★★★★★");
			break;
		default:$("#question_difficulty").html("★");
	};
	$("#multiple_label").html(result.labels);
	
	//$("#multiple_option").html(result.options);
	optionArray = $.trim(result.options.replace("[","").replace("]","")).split(",");
	//alert(optionArray);
	
	var content = new Array();
	var option = new Array();
	var tmp = ["A","B","C","D","E","F","G"];
	for(var i = 0; i < optionArray.length; i++) {
		option.push(" &nbsp;&nbsp;"+tmp[i]+":"+optionArray[i]);
		
		content.push("<label class='checkbox-inline'>"+
			"<input type='checkbox' class='uniform answer' value='"+$.trim(optionArray[i])+"'>"+
			optionArray[i]+"</label>");
	}
	content.push("<button class='submit_answer btn bs-tooltip' onclick='submitAnswer();' data-placement='bottom' style='margin-left:10px;'>提交</button>");
	
	$("#multiple_option").html(option.join(" "));
	$("#show_option").html(content.join(" "));
}, "json");

//提交答案
function submitAnswer() {
	var id = questionId;
	var answercontent = new Array();
	
	//获取选中的值
	var array = $(".answer");
	for(var i=0; i<array.length; i++) {
		if(array[i].checked) {
			answercontent.push(array[i].value);
		}
	}
	if(answercontent.length == 0) {
		alert("请选择答案");
		return false;
	}
	//alert(answercontent);
	var params = {
		"qid": questionId,
		"answer": answercontent.join(",")
	};
	$.get("/multiple/judge", params, function(result) {
		$("#analyse").html(result.msg);
	}, "json")
}