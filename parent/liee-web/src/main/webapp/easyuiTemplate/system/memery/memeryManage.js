var userMenuAuthGrid;

$(function() {

	userMenuAuthGrid = $('#userMenuAuthGrid').datagrid({
		url : sysUtil.bp() + '/memery/getUserMenuAuth',
		fitColumns : true,
		border : true,
		singleSelect : true,
		fit : true,
		columnsfit : true,
		pagination : false,
		loadFilter : function(data) {
			var dataMeta = [];
			dataMeta['total'] = data.length;
			dataMeta['rows'] = data;
			return dataMeta;
		},
		columns : [[{
			field : 'userId',
			title : '用户ID',
			width : 100,
			align : 'center'
		},{
			field : 'menuAuthStr',
			title : '菜单权限',
			width : 100,
			align : 'center'
		}, {
			field : 'operation',
			title : '操作',
			width : 100,
			align : 'center',
			formatter : function(val,row){
				return '<a href="javascript:;" style="color:blue" onclick="clearUserMenuAuth('+row.userId+')"> 清除该数据 </a>';
			}
		}]]
	});
	
	
});

/**
 * 清楚用户的菜单权限缓存
 */
function clearUserMenuAuth(userId){
	base.authAjax({
		type : "post",
		url : sysUtil.bp()+ '/memery/clearUserMenuAuth',
		data : {"userId":userId},
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
			userMenuAuthGrid.data().datagrid.cache = null;
			userMenuAuthGrid.datagrid("reload");
		}
	});
}




