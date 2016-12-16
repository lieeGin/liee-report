var dataGrid;
var editForm;
var editDialog;

var roleDialog; 

$(function() {

	dataGrid = $('#dataGrid').datagrid({
		url : sysUtil.bp() + '/user/pageQuery',
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
			title : '用户ID',
			width : 100,
			align : 'center'
		},{
			field : 'userName',
			title : '用户名称',
			width : 100,
			align : 'center'
		}, {
			field : 'sex',
			title : '性别',
			width : 100,
			align : 'center',
			formatter : function(val){
				return val==0?"保密":(val==1?"男":(val==2?"女":"未知"));
			}
		},{
			field : 'mobilePhone',
			title : '手机号',
			width : 100,
			align : 'center'
		},{
			field : 'email',
			title : '邮箱',
			width : 100,
			align : 'center'
		},{
			field : 'registerTime',
			title : '注册时间',
			width : 100,
			align : 'center',
			formatter : function(val){
				return formatDateTime(new Date(val));
			}
		}]],
		toolbar : [{
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				
			 /* 权限控制*/
			  if(authLevel!=2){
					$.messager.show({
						title : '提示',
						msg : '对不起，你没有该操作的权限',
						timeout : 3000
					});
					return;
				}
				
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
				
			   /* 权限控制*/
			   if(authLevel!=2){
					$.messager.show({
						title : '提示',
						msg : '对不起，你没有该操作的权限',
						timeout : 3000
					});
					return;
				}
				
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
				if(authLevel!=2){
					$.messager.show({
						title : '提示',
						msg : '对不起，你没有该操作的权限',
						timeout : 3000
					});
					return;
				}
				var row = dataGrid.datagrid('getSelected');
				if (row) {
					$.messager.confirm('确认','确定删除该记录吗?',
						function(r) {
							if (r) {
								var id = row["id"];
								base.authAjax({
									type : "post",
									url : sysUtil.bp()+ '/user/delete',
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
			text : '分配角色',
			iconCls : 'icon-redo',
			handler : function() {
				$('#errorMessage').html('');
				var row = dataGrid.datagrid('getSelected');
				if (row) {
					$("#userId").val(row.id);
					// 先移除
					$('#allRole option').remove();
					$('#selectedRole option').remove();
					// 查询所有角色
					base.select("allRole","/role/getAllRole","id","roleName",null);
					// 查询已经分配的角色
					base.select("selectedRole","/role/getUserRole?userId="+row.id,"id","roleName",null);
					// 弹出分配框
					roleDialog.dialog("open");
					
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

    editForm = $('#editForm').form();
    editDialog = $('#editDialog').dialog({
		width : 300,
		modal : true,
		resizable : true,
		title : '编辑信息',
		closed : true,
		buttons : [{
			text : "保存",
			id : "saveBtn",
			handler : function() {
				if ($('#editForm').form("validate")) {
					var formData = $('#editForm').form('serialize');
					 $("#saveBtn").linkbutton("disable");
					base.authAjax({
						type : "post",
						url : sysUtil.bp()+ '/user/saveOrUpdate',
						data : formData,
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
    
    // 分配角色的dialog
    roleDialog = $('#roleDialog').dialog({
		width : 300,
		modal : true,
		resizable : true,
		title : '分配角色',
		closed : true,
		buttons : [{
			text : "保存",
			id : "saveBtn",
			handler : function() {
			    var userId = $("#userId").val();
				
				// 获取被选中的所有角色
			   var selectedRole =  $("#selectedRole option");
			   var roleIds = "";
			   if(selectedRole && selectedRole.length >0){
				   for(var i = 0;i<selectedRole.length;i++){
					   roleIds+=$(selectedRole[i]).attr("value")+",";
				   }
			   }
			   $("#saveBtn").linkbutton("disable");
			   // 保存
			   base.authAjax({
					type : "post",
					url : sysUtil.bp()+ '/user/saveUserRoles',
					data :{"userId":userId,"roleIds":roleIds},
					contentType : "application/x-www-form-urlencoded;charset=UTF-8",
					dataType : "json",
					success : function(data) {
						if (data.success) {
							$.messager.show({
										title : '',
										msg : "操作成功",
										timeout : 3000
									});
							roleDialog.dialog("close");
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
				roleDialog.dialog("close");
			}
		}]
    });
    
    // 添加所有角色
    $("#addAll").click(function(){
    	var allRoles = $('#allRole option');
    	
    	for(var i = 0;i<allRoles.length;i++){
    		var $option = $(allRoles[i]);
		    var html = $option.html();
    	    var value = $option.val();
    	    var exsit = $('#selectedRole option[value='+value+']');
    	    if(!exsit || exsit.length==0){
    	    	var addStr  = '<option value="'+value+'">'+html+'</option>';
    	    	$('#selectedRole').append(addStr);
    	    }
    	}
    });
    
    // 添加选中的角色
    $("#addOne").click(function(){
    	$option = $('#allRole option:selected');
    	addToSelected($option);
    });
    
    
    function addToSelected($option){
    	 var html = $option.html();
 	    var value = $option.val();
 	    var exsit = $('#selectedRole option[value='+value+']');
 	    if(!exsit || exsit.length==0){
 	    	var addStr  = '<option value="'+value+'">'+html+'</option>';
 	    	$('#selectedRole').append(addStr);
 	    }
    }
    
    // 移除所有角色
    $("#removeAll").click(function(){
    	$('#selectedRole').html("");
    });
    
    // 移除选中的角色
    $("#removeOne").click(function(){
    	$('#selectedRole option:selected').remove();
    });
    
    // 双击选择角色
    $('#allRole').dblclick(function(){  
    	$option =   $('#allRole option:selected');  
    	addToSelected($option);
    })
    
    // 双击移除角色
    $('#selectedRole').dblclick(function(){  
    	$('#selectedRole option:selected').remove();
    })

	$('#select').live('click', function() {
		var queryParams = $('#selectForm').form('serialize');
		dataGrid.datagrid('options').queryParams = queryParams;
		dataGrid.data().datagrid.cache = null;
		dataGrid.datagrid('load');
	});
});
