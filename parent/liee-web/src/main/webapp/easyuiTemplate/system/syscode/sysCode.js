$(function() {

	 editForm = $('#editForm').form();
	 
	$('#codeTree').tree({
		url : sysUtil.bp() + '/sysCode/getAllCode',
		loadFilter: function(rows){
			return convert(rows);
		},
		onClick: function (node) {
			 // 查询当前结点
			 if(node.id!=0){
				 $.ajax({
						type : "post",
						url : sysUtil.bp()+ '/sysCode/getById',
						data : {"id": node.id},
						contentType : "application/x-www-form-urlencoded;charset=UTF-8",
						dataType : "json",
						success : function(data) {
							if(data.success){
								var menu = data.obj;
								editForm.form('load', menu);
							}else{
								$.messager.show({
									title : '查询失败',
									msg : data.errMsg,
									timeout : 3000
								});
							}
						}
					});
			 }else{
				 editForm.form('clear');
			 }
         }
	});
	
	$("#addSub").click(function(){
		var node = $('#codeTree').tree('getSelected');
		if(!node){
			$.messager.show({
				title : '信息',
				msg : '请先选择上级字典！',
				timeout : 3000
			});
			
			return;
		}
		
		editForm.form('clear');
		$("#parentCode").val(node.code);
		
		// 增加临时结点
		var newnode = {
			"id":-1,
			"parentCode":node.code,
			"text":"新节点"
		};
		$('#codeTree').tree('append', {
			parent:node.target,
			data:newnode
		});
		
	});
	
	// 保存提交
	$("#submit").click(function(){
		
	   $("#submit").linkbutton("disable");
		base.authAjax({
			type : "post",
			url : sysUtil.bp()+ '/sysCode/saveOrUpdate',
			data : $('#editForm').form('serialize'),
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					$.messager.show({
								title : '提示',
								msg : "操作成功",
								timeout : 3000
							});
				} else {
					$.messager.show({
								title : '操作失败',
								msg : data.errMsg,
								timeout : 3000
							});
				}
				
				$("#submit").linkbutton("enable");
				$("#codeTree").tree('reload');
			}
		});
	});
	
	// 刷新
	$("#reload").click(function(){
		editForm.form('clear');
		$("#codeTree").tree('reload');
	});
	
	// 删除
	$("#delete").click(function(){
		var node = $('#codeTree').tree('getSelected');
		if(!node){
			$.messager.show({
				title : '信息',
				msg : '请选择需要删除的菜单！',
				timeout : 3000
			});
			return;
		}
		base.authAjax({
			type : "post",
			url : sysUtil.bp()+ '/sysCode/delete',
			data : {"id": node.id},
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			dataType : "json",
			success : function(data) {
				if(data.success){
					$.messager.show({
						title : '成功',
						msg : "删除成功",
						timeout : 3000
					});
				}else{
					$.messager.show({
						title : '失败',
						msg : data.errMsg,
						timeout : 3000
					});
				}
				$("#codeTree").tree('reload');
			}
		});
		
	});
	
	
	function convert(rows){
		function exists(rows, parentCode){
			for(var i=0; i<rows.length; i++){
				if (rows[i].code == parentCode) return true;
			}
			return false;
		}
		
		var nodes = [];  //  
		// get the top level nodes
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			if (!exists(rows, row.parentCode)){
				nodes.push({
					id:row.id,
					code:row.code,
					text:row.text
				});
			}
		}
		
		var toDo = [];
		for(var i=0; i<nodes.length; i++){
			toDo.push(nodes[i]);
		}
		while(toDo.length){
			var node = toDo.shift();	// the parent node
			// get the children nodes
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				if (row.parentCode == node.code){
					var child = {id:row.id,code:row.code,text:row.text};
					if (node.children){
						node.children.push(child);
					} else {
						node.children = [child];
					}
					toDo.push(child);
				}
			}
		}
		return nodes;
	}
});
