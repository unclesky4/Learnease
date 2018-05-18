var search = location.search;
var questionId = search.split("?id=")[1];

var optionArray = new Array();
$.get("/simple/findById", {"id": questionId}, function(result) {
	$("#simple_content").html(result.content);
	switch(result.difficulty) {
		case 1:
			$("#simple_difficulty").html("★");
			break;
		case 2:
			$("#simple_difficulty").html("★★");
			break;
		case 3:
			$("#simple_difficulty").html("★★★");
			break;
		case 4:
			$("#simple_difficulty").html("★★★★");
			break;
		case 5:
			$("#simple_difficulty").html("★★★★★");
			break;
		default:$("#question_difficulty").html("★");
	};
	$("#simple_label").html(result.labels);
	
	//$("#simple_option").html(result.options);
	optionArray = $.trim(result.options.replace("[","").replace("]","")).split(",");
	//alert(optionArray);
	
	var content = new Array();
	var option = new Array();
	var tmp = ["A","B","C","D","E","F","G"];
	for(var i = 0; i < optionArray.length; i++) {
		option.push(" &nbsp;&nbsp;"+tmp[i]+":"+optionArray[i]);
		
		content.push("<label class='radio-inline'>"+
			"<input type='radio' name='optionsRadios2' class='uniform answer' value='"+$.trim(optionArray[i])+"'>"+
			optionArray[i]+"</label>");
	}
	content.push("<button class='submit_answer btn bs-tooltip' onclick='submitAnswer();' data-placement='bottom' style='margin-left:10px;'>提交</button>");
	
	$("#simple_option").html(option.join(" "));
	$("#show_option").html(content.join(" "));
}, "json");

//提交答案
function submitAnswer() {
	var id = questionId;
	var answercontent = "";
	
	//获取选中的值
	var array = $(".answer");
	for(var i=0; i<array.length; i++) {
		if(array[i].checked) {
			answercontent = array[i].value;
		}
	}
	if(answercontent == "") {
		alert("请选择答案");
		return false;
	}
	//alert(answercontent);
	var params = {
		"qid": questionId,
		"answer": answercontent
	};
	$.get("/simple/judge", params, function(result) {
		$("#analyse").html(result.msg);
	}, "json")
}