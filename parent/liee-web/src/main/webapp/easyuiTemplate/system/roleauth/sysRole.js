var dataGrid;
var editForm;
$(function() {

	dataGrid = $('#dataGrid').datagrid({
		url : sysUtil.bp() + '/role/pageQuery',
		fitColumns : true,
		border : true,
		singleSelect : true,
		fit : true,
		columnsfit : true,
		pagination : true,
		pageSize : 16,
		pageList : [16, 50, 100, 200],
		loadFilter : function(data) {
			var dataMeta = [];
			if (data.success){
				var list = data.obj.rows;
				dataMeta['total'] = data.obj.total;
				dataMeta['rows'] = list;
				return dataMeta;
			}else{
				dataMeta['total'] = 0;
				dataMeta['rows'] = [];
				$.messager.show({
					title : '错误',
					msg : data.errMsg,
					timeout : 3000
				});
			}
		},
		columns : [[{
			field : 'id',
			title : '角色ID',
			width : 100,
			align : 'center'
		},{
			field : 'roleName',
			title : '角色名称',
			width : 100,
			align : 'center'
		}, {
			field : 'remark',
			title : '角色备注',
			width : 100,
			align : 'center'
		}]],
		toolbar : [{
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				$('#errorMessage').html('');
				editDialog.dialog({
					'title' : "新增"
				});
				editForm.form('clear');
				editDialog.dialog("open");
			}
		},
		{
			text : '编辑',
			iconCls : 'icon-edit',
			handler : function() {
				$('#errorMessage').html('');
				var row = dataGrid.datagrid('getSelected');
				if (row) {
					editForm.form('clear');
					editForm.form('load', row);
					editDialog.dialog("open");
				} else {
					$.messager.show({
						title : '提示',
						msg : '请先选择一条信息!',
						timeout : 3000
					});
				}
			}
		},
		{
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				var row = dataGrid.datagrid('getSelected');
				if (row) {
					$.messager.confirm('确认','确定删除该记录吗?',
						function(r) {
							if (r) {
								var id = row["id"];
								base.authAjax({
									type : "post",
									url : sysUtil.bp()+ '/role/delete',
									data : {"id" : id},
									contentType : "application/x-www-form-urlencoded;charset=UTF-8",
									dataType : "json",
									success : function(data) {
										if (data.success) {
											$.messager.show({
														title : '',
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
										dataGrid.data().datagrid.cache = null;
										dataGrid.datagrid("reload");
									}
								});
							}
						});
				} else {
					$.messager.show({
						title : '提示',
						msg : '请先选择一条信息!',
						timeout : 3000
					});
				}
			}
		},
		{
			text : '分配菜单权限',
			iconCls : 'icon-redo',
			handler : function() {
				$('#errorMessage').html('');
				var row = dataGrid.datagrid('getSelected');
				if (row) {
					$("#roleId").val(row.id);
					
					//初始化树
					initMenuTree();
					
					// 初始化被选择菜单，以及权限  
					initAuthGrid(row.id);
					
					// 弹出分配框
					authDialog.dialog("open");
					
				} else {
					$.messager.show({
						title : '提示',
						msg : '请先选择一条信息!',
						timeout : 3000
					});
				}
			}
		}]
	});
	
	function initMenuTree(){
		$('#menuTree').tree({
			url : sysUtil.bp() + '/menu/getAllMenu',
			loadFilter: function(rows){
				return convert(rows);
			},
	         onDblClick : function(node){
	        	 // 添加到右边
	        	var parentNode =  $('#menuTree').tree('getParent',node.target);
	        	 addNodeToGrid(node,parentNode);
	         }
		});
	}
	
	
	var authEditRow = undefined; //定义全局变量：当前编辑的行
	
	function initAuthGrid( roleId){
		
		var authLevels = [{"text":"只读","value":"1"},{"text":"读写","value":"2"}];  //  暂时固定
		
		$("#authGrid").datagrid({
			height : 100,
			url : sysUtil.bp() + '/role/getRoleMenu?roleId='+roleId,
			fitColumns : true,
			border : true,
			singleSelect : true,
			fit : true,
			columnsfit : true,
			pagination : false,  // 不分页
			loadFilter : function(data) {
				var dataMeta = [];
				if (data){
					var list = data
					dataMeta['total'] = data.length;
					dataMeta['rows'] = list;
					return dataMeta;
				}else{
					dataMeta['total'] = 0;
					dataMeta['rows'] = [];
					$.messager.show({
						title : '错误',
						msg : data.errMsg,
						timeout : 3000
					});
				}
			},
			onClickRow: function (rowIndex, rowData) {
				//alert(rowData.menuName);
				 if (authEditRow != undefined) {
					 $("#authGrid").datagrid("endEdit", authEditRow);
                 }
				 $("#authGrid").datagrid("beginEdit", rowIndex);
				  authEditRow = rowIndex;
			} ,
			onDblClickRow: function (rowIndex, rowData) {
				$("#authGrid").datagrid("deleteRow",rowIndex);
			} ,
			columns : [[
			 /*           {
				field : 'id',
				title : '记录ID',
				width : 100,
				hidden : true,
				align : 'center'
			},*/
			{
				field : 'menuId',
				title : '菜单ID',
				width : 100,
				hidden : true,
				align : 'center'
			},{
				field : 'menuName',
				title : '菜单名称',
				width : 100,
				align : 'center'
			}, {
				field : 'authLevel',
				title : '权限级别',
				width : 100,
				align : 'center',
				formatter: function(value) { 
				    for (var i = 0; i < authLevels.length; i++) { 
				        if (authLevels[i].value == value) { 
				            return authLevels[i].text; 
				        } 
				    } 
				    return value;
				} ,
				editor: {
			        type : 'combobox',//根据条件改变
			        options : {
			        		valueField : "value",
			        		textField : "text",
			        		data:authLevels, 
		        	}
			    }
			}]]
		});
	}
	
	
	 // 分配角色的dialog
    authDialog = $('#authDialog').dialog({
		width : 700,
		modal : true,
		resizable : true,
		title : '分配菜单权限',
		closed : true,
		buttons : [{
			text : "保存",
			id:"saveBtn",
			handler : function() {
			   var roleId = $("#roleId").val();
				
				// 获取表格中所有的菜单
			   if (authEditRow != undefined) {
					 $("#authGrid").datagrid("endEdit", authEditRow);
					 authEditRow = undefined;
               }
			   var rows = $("#authGrid").datagrid("getRows");
			   
			   var allRecord = JSON.stringify(rows);
			   
			   $("#saveBtn").linkbutton("disable");
			   // 保存
			   base.authAjax({
					type : "post",
					url : sysUtil.bp()+ '/role/saveRoleMenu',
					data :{"roleId":roleId,"allRecord":allRecord},
					contentType : "application/x-www-form-urlencoded;charset=UTF-8",
					dataType : "json",
					success : function(data) {
						if (data.success) {
							$.messager.show({
										title : '提示',
										msg : "操作成功",
										timeout : 3000
									});
							authDialog.dialog("close");
						} else {
							$.messager.show({
										title : '操作失败',
										msg : data.errMsg,
										timeout : 3000
									});
						}
						 $("#saveBtn").linkbutton("enable");
					}
				});
			   
			}
		}, {
			text : "取消",
			handler : function() {
				authDialog.dialog("close");
			}
		}]
    });
    
    // 添加菜单信息到右边表格
    function addNodeToGrid(node,parentNode){
    	if(!checkSelected(node)){
    		var parentName = (parentNode?parentNode.text:"");
    		
    		var newRow = {};
    		newRow.menuId = node.id;
    		newRow.menuName = (parentName?(parentName +" > "):"")+node.text;
    		newRow.authLevel = 2;  // 默认是拥有全部权限
    		$("#authGrid").datagrid("appendRow", newRow);
    	}
    }
    
    function checkSelected(node){
    	var rows = $("#authGrid").datagrid("getRows");
    	for(var i = 0 ; i<rows.length;i++){
    		var r = rows[i];
    		if(r.menuId==node.id){
    			return true;
    		}
    	}
    	return false;
    }
    
    // 添加所有菜单
    $("#addAll").click(function(){
    	var allNodes  =$('#menuTree').tree('getChildren');
    	for(var i = 0;i<allNodes.length;i++){
    		var node = allNodes[i];
    		var parentNode =  $('#menuTree').tree('getParent',node.target);
    		addNodeToGrid(node,parentNode);
    	}
    });
    
    // 添加选中菜单
    $("#addOne").click(function(){
    	var node =$("#menuTree").tree('getSelected');
    	var parentNode =  $('#menuTree').tree('getParent',node.target);
		addNodeToGrid(node,parentNode);
    });
    
    
    // 移除所有菜单
    $("#removeAll").click(function(){
    	//$('#authGrid').datagrid('loadData', { total: 0, rows: [] });//清空
    	var rows = $("#authGrid").datagrid("getRows");
    	for(var i = rows.length ; i>0;i--){
    		var select = rows[i];
    		var index = $('#authGrid').datagrid('getRowIndex',select);
        	$("#authGrid").datagrid("deleteRow",index);
    	}
    });
    
    // 移除选中的菜单
    $("#removeOne").click(function(){
    	var select = $('#authGrid').datagrid('getSelected');
    	var index = $('#authGrid').datagrid('getRowIndex',select);
    	$("#authGrid").datagrid("deleteRow",index);
    });
    

    editForm = $('#editForm').form();
    editDialog = $('#editDialog').dialog({
		width : 480,
		modal : true,
		resizable : true,
		title : '编辑信息',
		closed : true,
		buttons : [{
			text : "保存",
			id : "saveBtn",
			handler : function() {
				if ($('#editForm').form("validate")) {
					 $("#saveBtn").linkbutton("disable");
					base.authAjax({
						type : "post",
						url : sysUtil.bp()+ '/role/saveOrUpdate',
						data : $('#editForm').form('serialize'),
						contentType : "application/x-www-form-urlencoded;charset=UTF-8",
						dataType : "json",
						success : function(data) {
							if (data.success) {
								$.messager.show({
											title : '',
											msg : "操作成功",
											timeout : 3000
										});
								editDialog.dialog("close");
							} else {
								$.messager.show({
											title : '操作失败',
											msg : data.errMsg,
											timeout : 3000
										});
							}
							$("#saveBtn").linkbutton("enable");
							dataGrid.data().datagrid.cache = null;
							dataGrid.datagrid("reload");
						}
					});
				}

			}
		}, {
			text : "取消",
			handler : function() {
				editDialog.dialog("close");
			}
		}]
    });

	$('#select').live('click', function() {
		var queryParams = $('#selectForm').form('serialize');
		dataGrid.datagrid('options').queryParams = queryParams;
		dataGrid.data().datagrid.cache = null;
		dataGrid.datagrid('load');
	});
	
	function convert(rows){
		function exists(rows, parentId){
			for(var i=0; i<rows.length; i++){
				if (rows[i].id == parentId) return true;
			}
			return false;
		}
		
		var nodes = [];  // {id:0,text:"所有菜单"}
		// get the top level nodes
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			if (!exists(rows, row.parentId)){
				nodes.push({
					id:row.id,
					text:row.menuName
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
				if (row.parentId == node.id){
					var child = {id:row.id,text:row.menuName};
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
