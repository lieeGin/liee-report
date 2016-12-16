 var dataGrid;
 var editForm;
$(function(){
		dataGrid = $('#dataGrid').datagrid({  
	        url: sysUtil.bp()+'/systemconfig/pageQuery',
	        fitColumns:true,
	      	border:true,
	        singleSelect:true,
	        fit:true,
	    	columnsfit:true,
	        pagination:true,
	        pageSize:16,
	        pageList:[16,50,100,200],
	        loadFilter:function(data){
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
	        columns:[[
	            {field:'id',title:'ID',width:100,align:'center'},  
	            {field:'keyword',title:'参数编号',width:100,align:'center'},  
	  			{field:'valuestr',title:'参数值',width:100,align:'center'},
	  			{field:'remark',title:'参数描述',width:100,align:'center'}
	        ]],
	        toolbar:[{
        		text:'新增',
        		iconCls:'icon-add',
        		handler:function(){
        			$('#errorMessage').html('');
        			editDialog.dialog({'title':"新增"});
        			editForm.form('clear');
        			editDialog.dialog("open");
        		}
        	},{
        		text:'编辑',
        		iconCls:'icon-edit',
        		handler:function(){
        			$('#errorMessage').html('');
        			var row = dataGrid.datagrid('getSelected');
        			if(row){
        				editForm.form('clear');
        				editForm.form('load',row);
	        			editDialog.dialog("open");
    				}else{
    					$.messager.show({
			            	title:'提示', 
			            	msg:'请先选择一条信息!',
			            	timeout:3000
			        	});
    				}
        		}
        	},{
        		text:'删除',
        		iconCls:'icon-remove',
        		handler:function(){
        			var row = dataGrid.datagrid('getSelected');
        			if(row){
        				$.messager.confirm('确认','确定删除该记录吗?',function(r){  
        				    if (r){  
        				    	var id=row["systemConfigId"];
    	        				base.authAjax({
        							type: "post",  
       								url: sysUtil.bp()+'/systemconfig/delete',
       								data:{"systemConfigId":id},
        							contentType:"application/x-www-form-urlencoded;charset=UTF-8",
        							dataType:"json", 
        							success:function(data){
        								if(data.success){
        									$.messager.show({
        						            	title:'', 
        						           	 	msg:"操作成功",
        						            	timeout:3000
        						        	});
        								}else{
        									$.messager.show({
        						            	title:'操作失败', 
        						           	 	msg:data.errMsg,
        						            	timeout:3000
        						        	});
        								}
        								dataGrid.data().datagrid.cache = null; 
		    	        				dataGrid.datagrid("reload"); 
        							}
    							});
    	        				
        				    }  
        				});  
        			}else{
        				$.messager.show({
			            	title:'提示', 
			            	msg:'请先选择一条信息!',
			            	timeout:3000
			        	});
        			}
        		}
        	}]
		});
		editForm = $('#editForm').form();
		editDialog=$('#editDialog').dialog({
			width:480,
			modal:true,
			resizable:true,
			 title:'编辑信息',
			 closed:true,
			 buttons:[{
        	 	text:"保存",
        	 	handler:function(){
        	 		
        	 		if($('#editForm').form("validate")){
        	 			base.authAjax({
							type: "post",  
							url: sysUtil.bp()+'/systemconfig/saveOrUpdate',
							data:$('#editForm').form('serialize'),
							contentType:"application/x-www-form-urlencoded;charset=UTF-8",
							dataType:"json", 
							success:function(data){
								if(data.success){
									$.messager.show({
						            	title:'', 
						           	 	msg:"操作成功",
						            	timeout:3000
						        	});
									editDialog.dialog("close");
								}else{
									$.messager.show({
						            	title:'操作失败', 
						           	 	msg:data.errMsg,
						            	timeout:3000
						        	});
								}
								dataGrid.data().datagrid.cache = null; 
		        				dataGrid.datagrid("reload"); 
							}
						}); 
        	 		}
        	 		
        	 	}
			},{
				text:"取消",
				handler:function(){
					editDialog.dialog("close");
			}
			}]
		});
		
		$('#select').live('click',function(){
        	var queryParams = $('#selectForm').form('serialize');
 			dataGrid.datagrid('options').queryParams= queryParams;   
 			dataGrid.data().datagrid.cache = null; 
			dataGrid.datagrid('load');
        });
}); 
		