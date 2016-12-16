<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/easyuiTemplate/head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>角色管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  	<script>
	   // 权限控制使用到以下2个参数
  		var authLevel="${param.authLevel}";
  		var menuId="${param.menuId}";
  	</script>
  	<script type="text/javascript" src="${path}/easyuiTemplate/system/roleauth/sysRole.js"></script>
  </head>
  <body>
 <body class="easyui-layout" style="overflow:hidden" loadingMessage="请等待数据加载....." fit="true">
	  	<div region="center" border="true">
	  		 <form id="selectForm">
	  		 		<table style="font-size:12px;margin-left:10px;">
	  		 		<tr>
	  		 			<td>角色名称：</td>
	  					<td><input name="roleName" type="text"/></td>
			  	 	 	<td><a href="javascript:void(0)" plain="true" id="select" class="easyui-linkbutton" iconCls="icon-search">查询</a> </td>
			  	 	 </tr>
	  		 	</table>
			  </form>
	  		<div style="width:98%;height:90%;margin:10 10 10 10">
		  		<table id="dataGrid" class="esayui"></table>
	  		</div>
		</div>
		<div id="editDialog" class="easyui-dialog" style="width: 400px;height: 300px;" title="详细信息" style="dispaly:none">
	  			<form id="editForm" >
	  					<table class="tableStyle">
	  						<tr>
	  							<td align="right" class="tdStyle">角色名称: <input type="hidden" name="id"></td>
	  							<td align="left" class="tdStyle">
	  								<input type="text" name="roleName" class="easyui-validatebox"  style="font-size:12px;width:300px;" required="true"/>
	  							</td>
	  						</tr>
	  						<tr>
	  							<td align="right" class="tdStyle">角色描述:</td>
	  							<td align="left" class="tdStyle">
	  								<input type="text" name="remark" class="easyui-validatebox"  style="font-size:12px;width:300px;" />
	  							</td>
	  						</tr>
	  						 <tr>
  							<td align="left" class="tdStyle" colspan="4">
  								<span id="errorMessage" style="font-size:16px;color:red;padding-left:10px;"></span>
  							</td>
  						</tr>
	  					</table>
	  			</form>
	  		</div>
	  		
	  		
	 	 <div id="authDialog" class="easyui-dialog" style="width:500px;height:400px;" title="分配权限" style="dispaly:none">
	 			<form id="authForm" style="height:100%" >
	  					<table class="tableStyle">
	  						<tr>
	  							<td align="left" class="tdStyle" style="width:40%">所有菜单:<input type="hidden" name="roleId" id="roleId"/></td>
	  							<td align="center" class="tdStyle" style="width:20%"></td>
	  							<td align="left" class="tdStyle" style="width:40%">已授权菜单:</td>
	  						</tr>
	  						
	  						<tr style="height: 300px;">
	  							<td align="left" class="tdStyle" style="width:240px;vertical-align:top;">
	  								<ul id="menuTree" class="easyui-tree"></ul>
	  							</td>
	  							<td align="center" class="tdStyle">
	  								<div>
	  									<a href="#" id="addAll" class="easyui-linkbutton" >&gt;&gt;</a>
	  								</div>
	  								<br>
	  								<div>
	  									<a href="#" id="addOne" class="easyui-linkbutton" >&gt;</a>
	  								</div>
	  								<br>
	  								<div>
	  									<a href="#" id="removeAll" class="easyui-linkbutton" >&lt;&lt;</a>
	  								</div>
	  								<br>
	  								<div>
	  									<a href="#" id="removeOne" class="easyui-linkbutton" >&lt;</a>
	  								</div>
	  							</td>
	  							<td align="left" class="tdStyle" style="width:440px">
	  								<table id="authGrid" class="esayui"></table>
								</td>
	  						</tr>
	  					</table>
	  			</form>
	 			
  		</div>
  </body>
</html>