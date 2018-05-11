var permission_tree = {
	expand: true,
	init: function() {
		$.get("/permission/all",{},function(result) {
			var setting = {
				edit: {
				   drag: {
					   autoExpandTrigger: false,
					   isCopy: false,
					   isMove: true
				   },
				   enable: true,
				   showRemoveBtn: true,
				   showRenameBtn: false
			   	},
				check : {enable : true,nocheckInherit : true},
				data: {simpleData: {enable: true}},
				view: {
					showIcon: true,
					addHoverDom: permission_tree.addHoverDom,
					removeHoverDom: permission_tree.removeHoverDom
				},
				callback: {
					beforeRemove: permission_tree.beforeRemove,
					onClick: permission_tree.onClick,
					onDrop: zTreeOnDrop
				}
			};
			$.fn.zTree.init($("#permission_tree"), setting, result).expandAll(true);
		},"json");
	},
	onClick: function(event, treeId, treeNode){},
    addHoverDom: function(treeId, treeNode) {
        if (treeNode.editNameFlag || $("#permission_addBtn_" + treeNode.id).length > 0)
           return;
		var sObj = $("#" + treeNode.tId + "_span");
		var addStr = "<span class='button add' id='permission_addBtn_"+ treeNode.id + "' title='增加' onfocus='this.blur();'>"+
					"<img src='/extensions/zTree_v3/css/zTreeStyle/add_1.png' style='margin-bottom:-4px;'/></span>" +
       				"<span class='button edit' id='permission_editBtn_"+ treeNode.id + "' title='修改' onfocus='this.blur();'></span>";
		sObj.append(addStr);
		var addbtn = $("#permission_addBtn_" + treeNode.id);
		if (addbtn) {
           addbtn.bind("click", function() {
               permission_add_dialog.open(treeNode);
           });
		}
       
       var editbtn = $("#permission_editBtn_" + treeNode.id);
       if (editbtn) {
           editbtn.bind("click", function() {
           		permission_up_dialog.open(treeNode);
           });
       }
    },
    removeHoverDom: function(treeId, treeNode) {
        $("#permission_addBtn_" + treeNode.id).unbind().remove();
		$("#permission_editBtn_" + treeNode.id).unbind().remove();
    },
    beforeRemove: function(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.selectNode(treeNode);
		if(treeNode.isParent) {
           alert("存在子权限！");
           return false;
		}
		if(confirm("确认删除权限<" + treeNode.name + ">？")) {
           var params = {"id": treeNode.id};
           $.post("/permission/del", params, function (result) {
               if (result.success == true) {
                   return true;
               }else{
            	   alert(result.msg);
                   return false;
               }
           });
		}else{
           return false;
		}
    },
    expAll: function(){
	    if(permission_tree.expand) {
	    	$.fn.zTree.getZTreeObj("permission_tree").expandAll(false);
	        permission_tree.expand = false;
	    } else {
	    	$.fn.zTree.getZTreeObj("permission_tree").expandAll(true);
	        permission_tree.expand = true;
	    }
    },
    checkAll: function(){
	    if(permission_tree.check) {
	    	$.fn.zTree.getZTreeObj("permission_tree").checkAllNodes(false);
	        permission_tree.check = false;
	    } else {
	    	$.fn.zTree.getZTreeObj("permission_tree").checkAllNodes(true);
	        permission_tree.check = true;
	    }
    },
};
var permission_add_root_dialog = {
	open: function() {
		$("#root_name").val("");
		$("#root_code").val("");
		var add_root_dialog = $( "#add_root_permission" ).dialog({
		      height: 200,
		      width: 450,
		      resizable: false,
		      buttons: {
		    	  "添加": function() {
					var name = $("#root_name").val();
					var isCatalog = $('#root_isCatalog option:selected').val() == "true" ? true : false;
					var params = {
						"name": name,
						"isCatalog": isCatalog
					};
					$.post("permission/add",params,function(result){
						if(result.success == "true" || result.success == true) {
							add_root_dialog.dialog( "close" );
							$.fn.zTree.getZTreeObj("permission_tree").addNodes(null, -1, result.msg, true);
						}else{
							add_root_dialog.dialog( "close" );
							alert(result.msg);
						}
						
					},"json");
		    	  },
		          "取消": function() {
		        	  add_root_dialog.dialog( "close" );
		          }
		     },
		});
	}
};
var permission_add_dialog = {
	open: function(treeNode) {
		$("#add_name").val("");
		$("#add_code").val("");
		var add_dialog = $( "#add_permission" ).dialog({
		      height: 250,
		      width: 450,
		      resizable: false,
		      buttons: {
		    	  "添加": function() {
					var name = $("#add_name").val();
					var code = $("#add_code").val();
					var isCatalog = $('#add_isCatalog option:selected').val() == "true" ? true : false;
					var params = {
						"name": name,
						"code": code,
						"isCatalog": isCatalog,
						"pid": treeNode.id
					};
					$.post("permission/add",params,function(result){
						if(result.success == "true" || result.success == true) {
							add_dialog.dialog( "close" );
							var index = treeNode.getIndex()+1;
							$.fn.zTree.getZTreeObj("permission_tree").addNodes(treeNode, index, result.msg);
						}else{
							add_dialog.dialog( "close" );
							alert(result.msg);
						}
						
					},"json");
		    	  },
		          "取消": function() {
		        	  add_dialog.dialog( "close" );
		          }
		        },
		});
	}
};
var permission_up_dialog = {
	open: function(treeNode) {
		$("#up_name").val(treeNode.name);
		$("#up_code").val(treeNode.code);
		$("#up_isCatalog").find("option[value='"+treeNode.isCatalog+"']").attr("selected",true);
		var up_dialog = $( "#update_permission" ).dialog({
		      height: 250,
		      width: 450,
		      resizable: false,
		      buttons: {
		    	  "更新": function() {
					var name = $("#up_name").val();
					var code = $("#up_code").val();
					var isCatalog = $('#up_isCatalog option:selected').val() == "true" ? true : false;
					var params = {
						"id": treeNode.id,
						"name": name,
						"code": code,
						"isCatalog": isCatalog,
						"pid": treeNode.id
					};
					$.post("permission/update",params,function(result){
						if(result.success == "true" || result.success == true) {
							up_dialog.dialog( "close" );
							treeNode.name = name;
							treeNode.code = code;
							$.fn.zTree.getZTreeObj("permission_tree").updateNode(treeNode, true);
						}else{
							up_dialog.dialog( "close" );
							alert(result.msg);
						}
						
					},"json");
		    	  },
		          "取消": function() {
		        	  up_dialog.dialog( "close" );
		          }
		        },
		});
	}
};
$(function() {
	permission_tree.init();
	
	//添加根节点
	$("#add_root").click(function() {
		permission_add_root_dialog.open();
	});
	
	//更新启用状态
	$("#update_status").click(function() {
		var data = $.fn.zTree.getZTreeObj("permission_tree").getChangeCheckedNodes();
		var upIds = new Array();
		var downIds = new Array();
		$.each(data, function(index, object) {
			if(object.checked) {
				upIds.push(object.id);
			}else{
				downIds.push(object.id);
			}
		});
		var param = {
			"upIds": upIds.join(","),
			"downIds": downIds.join(",")
		};
		$.post("/permission/status/update", param, function(result) {
			alert(result.msg);
		}, "json");
	});
	
	//展开、折叠
	$("#expandAll").click(function() {
		permission_tree.expAll();
	});
});

//拖拽回调函数
//isCopy  true：复制；false：移动
function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	if(moveType == true) {
		return ;
	}
	if(targetNode == null) {
		return ;
	}
	if(moveType == null) {
		return ;
	}
    //alert(treeNodes.length + "," + (targetNode ? (targetNode.tId + ", " + targetNode.id) : "isRoot" ));
	var tree = $.fn.zTree.getZTreeObj("permission_tree");
    var id = treeNodes[0].id;
    var targetId = targetNode.id;
    //alert(id+" "+targetId);
    var param  = {
    	"id": id,
    	"targetId": targetId
    };
    $.post("/permission/updatePid", param, function(result) {
    	if(result.success == false) {
    		alert(result.msg);
    	}
    }, "json");
};