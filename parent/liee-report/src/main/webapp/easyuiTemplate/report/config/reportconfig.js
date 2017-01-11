 var dataGrid;
 var editForm;
 var editDialog;
 
 var paramGrid;
 var columnGrid;
 
 var dataTypes = [{"text":"文本","value":"text"},{"text":"数字","value":"number"}];                   //  查询参数类型
 var widgetTypes = [{"text":"文本框","value":"text"},{"text":"数字框","value":"number"},{"text":"下拉框","value":"select"}];    // 查询参数控件类型
 var compareWays = [{"text":"等于","value":"eq"},{"text":"大于","value":"gt"},{"text":"大于等于","value":"ge"},{"text":"小于","value":"lt"},{"text":"小于等于","value":"le"},
                    {"text":"模糊匹配","value":"like"},{"text":"包含","value":"in"}]
 
 var alignTypes = [{"text":"居左","value":"left"},{"text":"居右","value":"right"},{"text":"居中","value":"center"}];   // 结果表格显示对齐
 var YN = [{"text":"是","value":"1"},{"text":"否","value":"0"}];
 
 var dataSourceMap = {"0":"groovy脚本查询","1":"接口查询"};
 var reportShowWayMap = {"0":"表格","1":"柱状图","2":"曲线图","3":"雷达图","4":"饼图"};

$(function(){
	
		initParamGrid(0);  // 参数表格
		initColumnGrid(0);  // 结果列 表格
	
		dataGrid = $('#dataGrid').datagrid({  
	        url: sysUtil.bp()+'/report/pageQuery',
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
	            {field:'name',title:'报表名称',width:100,align:'center'},  
	  			{field:'code',title:'报表编号',width:100,align:'center'},
	  			{field:'dataSource',title:'数据来源',width:100,align:'center',formatter:function(val){
	  				return dataSourceMap[val];
	  			}},
	  			{field:'reportShowWay',title:'展示方式',width:100,align:'center',formatter:function(val){
	  				var html="";
	  				if(val){
	  					var strs = val.split(",");
	  					for(var i = 0 ; i<strs.length; i++){
	  						html+=reportShowWayMap[strs[i]]+",";
	  					}
	  				}
	  				return html;
	  			}}
	        ]],
	        toolbar:[{
        		text:'新增',
        		iconCls:'icon-add',
        		handler:function(){
        			$('#errorMessage').html('');
        			editDialog.dialog({'title':"新增"});
        			editForm.form('clear');
        			
        			changeShow(2);
        			paramGridQuery(0);  // 参数表格
        			columnGridQuery(0);  // 结果列 表格
        			
        			editDialog.dialog("open");
        		}
        	},{
        		text:'编辑',
        		iconCls:'icon-edit',
        		handler:function(){
        			$('#errorMessage').html('');
        			var row = dataGrid.datagrid('getSelected');
        			if(row){
        				changeShow(2);
        				setDataEdit(row);
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
        				    	var id=row["id"];
    	        				base.authAjax({
        							type: "post",  
       								url: sysUtil.bp()+'/report/delete',
       								data:{"id":id},
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
			 fit:true,
			 resizable:true,
			 title:'编辑信息',
			 closed:true,
			 buttons:[{
        	 	text:"保存",
        	 	handler:function(){
        	 		// 保存
        	 		saveReportConfig();
        	 	}
			},{
				text:"取消",
				handler:function(){
					editDialog.dialog("close");
			}
			}]
		});
		
		function setDataEdit(row){
			
			base.authAjax({
				type: "post",  
				url: sysUtil.bp()+'/report/getById',
				data:{"id":row.id},
				contentType:"application/x-www-form-urlencoded;charset=UTF-8",
				dataType:"json", 
				success:function(data){
					if(data.success){
						
						editForm.form('clear');
						editForm.form('load',data.obj.report);
						$("#groovyStr").val(data.obj.groovyStr);
						hljs.highlightBlock($("#codeArea code")[0]);
						
						console.log(data.obj.report);
						
						paramGridQuery(row.id);
						columnGridQuery(row.id);
						
						// 选择的类型
						columnSelect();
						
						editDialog.dialog("open");
						
					}else{
						$.messager.show({
			            	title:'操作失败', 
			           	 	msg:data.errMsg,
			            	timeout:3000
			        	});
					}
				}
			}); 
		}
		
		
		
		/**
		 * 提交
		 */
		function saveReportConfig(){
			
			// 获取参数表格
		   if (paramEditRow != undefined) {
				 paramGrid.datagrid("endEdit", paramEditRow);
				 paramEditRow = undefined;
           }
			   
			// 获取结果列表格
		   if (columnEditRow != undefined) {
				 columnGrid.datagrid("endEdit", columnEditRow);
				 columnEditRow = undefined;
           }
			
			if($('#editForm').form("validate") && checkGrids()){
				var submitData = $('#editForm').form('serialize');
				
				var paramRows = paramGrid.datagrid("getRows");
				var paramData = JSON.stringify(paramRows);
				var columnRows = columnGrid.datagrid("getRows");
				var columnData = JSON.stringify(columnRows);
				
				submitData.paramData = paramData;
				submitData.columnData = columnData;
				
			//	alert(JSON.stringify(submitData));
				
	 			base.authAjax({
					type: "post",  
					url: sysUtil.bp()+'/report/saveOrUpdate',
					data:submitData,
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
		
		
		/**
		 * 检查表格中的数据是否录入完整
		 * @returns
		 */
		function checkGrids(){
			
			var params = paramGrid.datagrid("getRows");
			if(params){
				for(var i=0; i<params.length;i++){
					var p = params[i];
					var result = checkParam(p);
					if(result){
						$.messager.show({
			            	title:'操作失败', 
			           	 	msg:result,
			            	timeout:3000
			        	});
						return false;
					}
				}
			}
			
			var columns = columnGrid.datagrid("getRows");
			if(columns){
				for(var i=0; i<columns.length;i++){
					var c = columns[i];
					var result = checkColumn(c);
					if(result){
						$.messager.show({
			            	title:'操作失败', 
			           	 	msg:result,
			            	timeout:3000
			        	});
						return false;
					}
				}
			}
			return true;
		}
		
		/**
		 * 检查参数是否录入完整
		 * @returns
		 */
		function checkParam(p){
			if(!p.name){
				return "请输入参数名称";
			}
			if(!p.code){
				return "请输入参数编码";
			}
			return "";
		}
		
		/**
		 * 检查数据列是否录入完整
		 * @returns
		 */
		function checkColumn(c){
			if(!c.title){
				return "请输入字段名称";
			}
			if(!c.field){
				return "请输入字段编码";
			}
			return "";
		}
		
		$('#select').on('click',function(){
        	var queryParams = $('#selectForm').form('serialize');
 			dataGrid.datagrid('options').queryParams= queryParams;   
 			dataGrid.data().datagrid.cache = null; 
			dataGrid.datagrid('load');
        });
		
		$("#codeArea").dblclick(function(){
			changeShow(2);
		});
		
		
		
		/**
		 * 初始化参数表格
		 */
		var paramEditRow = undefined; //定义全局变量：当前编辑的行
		function  initParamGrid(reportId){
			
			paramGrid = $('#paramGrid').datagrid({  
		        url: sysUtil.bp()+'/report/queryParamByReportId',
		        data:{"id":reportId},
		        fitColumns:true,
		      	border:true,
		        singleSelect:true,
		        fit:true,
		    	columnsfit:true,
		        pagination:false,
		        loadFilter:function(data){
		        	var dataMeta = [];
					if (data){
						var list = data;
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
					 if (paramEditRow != undefined) {
						 paramGrid.datagrid("endEdit", paramEditRow);
	                 }
					 paramGrid.datagrid("beginEdit", rowIndex);
					 paramEditRow = rowIndex;
				} ,
		        columns:[[
		            {field:'id',title:'ID',width:100,align:'center'},  
		            {field:'name',title:'参数名',width:100,align:'center',editor:{type : 'text', required:true}},  
		  			{field:'code',title:'参数编码',width:100,align:'center',editor:{type : 'text', required:true}},
		  			{field:'sourceCode',title:'对应数据源编码',width:100,align:'center',editor:{type : 'text'}},
		  			{field:'type',title:'数据类型',width:100,align:'center', editor:{  
		                type:'combobox',  
		                options:{  
		                    valueField:'value',  
		                    textField:'text',  
		                    data:dataTypes,  
		                    required:true
		                }  
		            },
		            formatter: function(value) { 
					    for (var i = 0; i < dataTypes.length; i++) { 
					        if (dataTypes[i].value == value) { 
					            return dataTypes[i].text; 
					        } 
					    } 
					    return value;
					}},
					
					{field:'compareWay',title:'比较方式',width:100,align:'center', editor:{  
		                type:'combobox',  
		                options:{  
		                    valueField:'value',  
		                    textField:'text',  
		                    data:compareWays,  
		                    required:true
		                }  
		            },
		            formatter: function(value) { 
					    for (var i = 0; i < compareWays.length; i++) { 
					        if (compareWays[i].value == value) { 
					            return compareWays[i].text; 
					        } 
					    } 
					    return value;
					}},
					
		  			{field:'widgetType',title:'控件类型',width:100,align:'center', editor:{  
		                type:'combobox',  
		                options:{  
		                    valueField:'value',  
		                    textField:'text',  
		                    data:widgetTypes,  
		                    required:true
		                }  
		            },
		            formatter: function(value) { 
					    for (var i = 0; i < widgetTypes.length; i++) { 
					        if (widgetTypes[i].value == value) { 
					            return widgetTypes[i].text; 
					        } 
					    } 
					    return value;
					}},
		  			{field:'selectKey',title:'字典编码',width:100,align:'center',editor:{type : 'text'}},
		        ]],
		        toolbar:[{
	        		text:'新增',
	        		iconCls:'icon-add',
	        		handler:function(){
	        			// 添加行
	        			var newRow = {};
	        			newRow.type='text';
	        			newRow.widgetType='text';
	        			paramGrid.datagrid("appendRow", newRow);
	        		}
	        	},{
	        		text:'删除',
	        		iconCls:'icon-remove',
	        		handler:function(){
	        			var row = paramGrid.datagrid('getSelected');
	        			if(row){
	        				var index = paramGrid.datagrid('getRowIndex',row);
	        				paramGrid.datagrid("deleteRow",index);
	        			}
	        		}
	        	}]
			});
		}
		
		

		/**
		 * 初始化结果列表格
		 */
		var columnEditRow = undefined; //定义全局变量：当前编辑的行
		function  initColumnGrid(reportId){
			
			columnGrid = $('#columnGrid').datagrid({  
		        url: sysUtil.bp()+'/report/queryColumnByReportId',
		        data:{"id":reportId},
		        fitColumns:true,
		      	border:true,
		        singleSelect:true,
		        fit:true,
		    	columnsfit:true,
		        pagination:false,
		        loadFilter:function(data){
		        	var dataMeta = {};
					if (data){
						var list = data;
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
					 if (columnGrid != undefined) {
						 columnGrid.datagrid("endEdit", columnEditRow);
	                 }
					 columnGrid.datagrid("beginEdit", rowIndex);
					 columnEditRow = rowIndex;
				} ,
		        columns:[[
		            {field:'id',title:'ID',width:100,align:'center'},  
		            {field:'title',title:'字段名称',width:100,align:'center',editor:{type : 'text'}},  
		  			{field:'field',title:'字段编码',width:100,align:'center',editor:{type : 'text'}},
		  			{field:'sourceField',title:'对应数据源字段',width:100,align:'center',editor:{type : 'text'}},
		  			{field:'sortable',title:'是否排序',width:100,align:'center', editor:{  
		                type:'combobox',  
		                options:{  
		                    valueField:'value',  
		                    textField:'text',  
		                    data:YN,  
		                    required:true  
		                }  
		            },
		            formatter: function(value) { 
					    for (var i = 0; i < YN.length; i++) { 
					        if (YN[i].value == value) { 
					            return YN[i].text; 
					        } 
					    } 
					    return value;
					}},
		  			{field:'width',title:'列宽',width:100,align:'center',editor:{type : 'numberbox'}},
		  			{field:'align',title:'对齐方式',width:100,align:'center',editor:{  
		                type:'combobox',  
		                options:{  
		                    valueField:'value',  
		                    textField:'text',  
		                    data:alignTypes,  
		                    required:true  
		                }  
		            },
		            formatter: function(value) { 
					    for (var i = 0; i < alignTypes.length; i++) { 
					        if (alignTypes[i].value == value) { 
					            return alignTypes[i].text; 
					        } 
					    } 
					    return value;
					}},
		        ]],
		        toolbar:[{
	        		text:'新增',
	        		iconCls:'icon-add',
	        		handler:function(){
	        			// 添加行
	        			var newRow = {};
	        			newRow.sortable=1;
	        			newRow.align='center';
	        			columnGrid.datagrid("appendRow", newRow);
	        		}
	        	},{
	        		text:'删除',
	        		iconCls:'icon-remove',
	        		handler:function(){
	        			var row = columnGrid.datagrid('getSelected');
	        			if(row){
	        				var index = columnGrid.datagrid('getRowIndex',row);
	        				columnGrid.datagrid("deleteRow",index);
	        			}
	        		}
	        	}]
			});
		}
		
		/**
		 * 参数表查询
		 */
		function paramGridQuery(id){
 			paramGrid.datagrid('options').queryParams= {"id":id};   
 			paramGrid.data().datagrid.cache = null; 
 			paramGrid.datagrid('load');
		}
		
		/**
		 * 结果列表格查询
		 */
		function columnGridQuery(id){
			columnGrid.datagrid('options').queryParams= {"id":id};   
			columnGrid.data().datagrid.cache = null; 
			columnGrid.datagrid('load');
		}
}); 


/**
 * type 1  隐藏textarea  2  隐藏代码展示
 * @param type
 */
function changeShow(type){
	if(type==1){
		$("#groovyStr").hide();
		var content = $("#groovyStr").val();
		$("#codeArea code").html(content);
		$("#codeArea").show();
		 hljs.highlightBlock($("#codeArea code")[0]);
	}else{
		$("#groovyStr").show();
		$("#codeArea").hide();
	}
}


/***
 * 勾选柱状图
 */
function columnSelect(){
	if($('#columnCheckbox').prop("checked")){  // 选中
		$(".column").show();
	}else{
		 //未选中
		$("#cxvalueFrom").combobox("setValue","");
		$("#cxvalue").val("");
		$("#cyvalueFrom").combobox("setValue","");
		$("#cyvalue").val("");
		$("#cytitle").val("");
		$("#cyunit").val("");
		$(".column").hide();
	}
	
}

/***
 * 勾选曲线图
 */
function lineSelect(){
	
	if($('#lineCheckbox').prop("checked")){  // 选中
		$(".line").show();
	}else{
		 //未选中
		$("#lxvalueFrom").combobox("setValue","");
		$("#lxvalue").val("");
		$("#lyvalueFrom").combobox("setValue","");
		$("#lyvalue").val("");
		$("#lytitle").val("");
		$("#lyunit").val("");
		$(".line").hide();
	}
}

		